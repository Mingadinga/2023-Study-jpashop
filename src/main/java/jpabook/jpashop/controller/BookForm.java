package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.UpdateItemDto;
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

    public static BookForm of(Book book) {
        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setName(book.getName());
        form.setPrice(book.getPrice());
        form.setStockQuantity(book.getStockQuantity());
        form.setAuthor(book.getAuthor());
        form.setIsbn(book.getIsbn());
        return form;
    }

    public UpdateItemDto toDto() {
        return new UpdateItemDto(price, name, stockQuantity);
    }
}