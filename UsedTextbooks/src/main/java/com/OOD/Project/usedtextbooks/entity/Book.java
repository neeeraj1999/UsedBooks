package com.OOD.Project.usedtextbooks.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String authors;
    private String title;
    private String edition;
    private BigDecimal price;
    private int soldBackCount = 0;
    private int quantity;

    public Book() {}

    public Book(String isbn, String authors, String title, String edition, BigDecimal price, int quantity) {
        this.isbn = isbn;
        this.authors = authors;
        this.title = title;
        this.edition = edition;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getSoldBackCount() {
        return soldBackCount;
    }

    public void setSoldBackCount(int soldBackCount) {
        this.soldBackCount = soldBackCount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", authors='" + authors + '\'' +
                ", title='" + title + '\'' +
                ", edition='" + edition + '\'' +
                ", price=" + price +
                ", soldBackCount=" + soldBackCount +
                ", quantity=" + quantity +
                '}';
    }
}

