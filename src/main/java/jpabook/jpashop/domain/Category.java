package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // 실무에선 사용 안함
    // 조인 테이블을 두면 이 필드 조합으로만 고정됨
    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name = "category_id"), // fk
        inverseJoinColumns = @JoinColumn(name = "item_id")) // 반대쪽 fk
    private List<Item> items = new ArrayList<>();

    // 계층 구조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child;

}
