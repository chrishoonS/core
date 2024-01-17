package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService{

    // MemberRepository, DiscountPolicy => 철저히 inteface에 의존
    private final MemberRepository memberRepository; // 생성자에 final이 있으면 생성자를 통해서 할당

    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    /*
     * 할인 정책 변경을 위해 interface 교체
     * OCP, DIP 같은 객체 지향 설계 원칙을 충실히 준수 했나?
     * 
     * DIP : 클래스 의존관계를 보면
     *  - 추상(interface) 뿐만 아니라 구체(impl) 클래스에도 의존하고 있다.
     *  - 추상(interface) 의존: `DiscountPolicy`
     *  - 구체(impl) 클래스: `FixDiscountPolicy` , `RateDiscountPolicy`
     *
     * 지금 코드는 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 준다! 따라서 OCP를 위반
     *
     * 이 문제를 해결하려면?
     * 클라이언트 코드인 `OrderServiceImpl` 은 `DiscountPolicy` 의 인터페이스 뿐만 아니라 구체 클래스도 함 께 의존한다.
     * 그래서 구체 클래스를 변경할 때 클라이언트 코드도 함께 변경해야 한다.
     * **DIP 위반** 추상에만 의존하도록 변경(인터페이스에만 의존)
     * DIP를 위반하지 않도록 인터페이스에만 의존하도록 의존관계를 변경하면 된다.
     */

//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 고정 할인
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 정액 할인

    /**
     * 주문 생성 요청
     * @return 최종 생성된 주문 반환
     */
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
