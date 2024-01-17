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
 */
public class AppConfig {
    public MemberService memberService() {
        // 생성자 주입
        return new MemberServiceImpl(new MemoryMemberRepository()); // DI(Dependency Injection, 의존관계 주입)
    }
    public OrderService orderService() {
        return new OrderServiceImpl(
                new MemoryMemberRepository(),
                new FixDiscountPolicy());
    }
}

