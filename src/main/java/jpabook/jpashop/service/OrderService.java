package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepositoryOld;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepositoryOld memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장 - createOrder 안에서 세팅, Cascade.All에 의해 persist로 저장됨
        // Casecade All - 참조 관계가 하나뿐일때. 라이프사이클이 동일할 때. private한 참조 관계일 때.
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 취소
     */
    // 우와 간단하다!
    // 도메인 모델 패턴 : JPA를 사용하면 도메인을 풍성하게 만들 수 있다
    // 트랜잭션 스크립트 패턴 : 데이터를 다루는 SQL 스크립트에 비해 객체지향적이다
    // 문맥에 따라 트레이드 오프가 있으므로 적절히 선택. 두 패턴은 양립할 수 있다.
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }


    // 검색
    @Transactional
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
    }

}
