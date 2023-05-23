package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

// repository 관련 검색 조건은 여기에!
@Getter @Setter
public class OrderSearch {
    private String memberName; // 회원 이름
    private OrderStatus orderStatus; // 주문 상태 : ORDER, CANCEL
}
