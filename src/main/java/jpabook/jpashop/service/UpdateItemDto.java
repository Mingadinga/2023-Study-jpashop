package jpabook.jpashop.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class UpdateItemDto {
    Integer price;
    String name;
    Integer stockQuantity;
}
