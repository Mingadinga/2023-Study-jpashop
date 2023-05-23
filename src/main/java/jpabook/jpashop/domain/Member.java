package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // 양방향 참조 시, 연관관계의 주인은 order_id
    private List<Order> orders = new ArrayList<>();

    public Member(){}

    public Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
