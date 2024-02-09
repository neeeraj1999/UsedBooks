package com.OOD.Project.usedtextbooks.service;

import com.OOD.Project.usedtextbooks.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookServiceInter {
    List<Book> getAllBooks();

    List<Book> findBooksByTitle(String title);

    Optional<Book> getBookById(Long id);

    Book addBook(Book book);

    Optional<Book> updateBook(Long id, Book updatedBook);

    void deleteBook(Long id);

    Optional<Book> buyBook(Long id);

    Book buyBookByTitle(String title);

    Book sellBookById(Long id);

    //Optional<Book> sellBookByIsbn(String isbn);

    Book sellBookByTitle(String title);
}
