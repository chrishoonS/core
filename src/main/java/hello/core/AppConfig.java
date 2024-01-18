package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

/**
 * 애플리케이션의 전체 동작 방식을 구성(config)하기 위해, 구현 객체를 생성
 * 연결하는 책임을 가지는 별도의 설정 클래스
 *
 * 리팩토링 후
 * 중복도 제거하고, 메서드만 봐도 역할이 잘 드러남으로써, 애플리케이션의 전체 구성도 파악하기가 쉬어짐
 */
public class AppConfig {
    public MemberService memberService() {
        // 생성자 주입
        // new MemoryMemberRepository() -> cmd+option+m으로 리팩토링
        return new MemberServiceImpl(memberRepository()); // DI(Dependency Injection, 의존관계 주입)
    }

    private static MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    // new FixDiscountPolicy() -> cmd+option+m으로 리팩토링
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    private static FixDiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }
}

