package com.OOD.Project.usedtextbooks.repository;



import com.OOD.Project.usedtextbooks.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn); // To retrieve a book by its ISBN.
    List<Book> findByTitleContaining(String title); // To retrieve books containing the specified title.
}

