package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {
    // Item 공통 속성
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    // Book 추가 속성
    private String author;
    private String isbn;

    public Book createBook() {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        return book;
    }
}