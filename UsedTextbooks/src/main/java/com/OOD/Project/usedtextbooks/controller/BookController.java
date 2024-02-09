package com.OOD.Project.usedtextbooks.controller;

import com.OOD.Project.usedtextbooks.entity.Book;
import com.OOD.Project.usedtextbooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Endpoint to retrieve all books
    @GetMapping("/getAll")
    public List<Book> getAllBooks() {
            return bookService.getAllBooks();
    }

    // Endpoint to find books by title
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        List<Book> books = bookService.findBooksByTitle(title);
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Endpoint to get a specific book by ID
    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/buy/{title}")
    public ResponseEntity<?> buyBookByTitle(@PathVariable String title) {
        try {
            Book purchasedBook = bookService.buyBookByTitle(title);
            return ResponseEntity.ok(purchasedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




    // Endpoint for a user to sell back a book by ID

        @PostMapping("/sellBackById/{id}")
        public ResponseEntity<?> sellBookById(@PathVariable Long id) {
            try {
                Book soldBook = bookService.sellBookById(id);
                return ResponseEntity.ok(soldBook);
            } catch (NoSuchElementException e) {
                // Handling the case where the book is not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }


    // Endpoint for a user to sell back a book by title
    @PostMapping("/sellBackByTitle/{title}")
    public ResponseEntity<Book> sellBackBookByTitle(@PathVariable String title) {
        try {
            Book soldBackBook = bookService.sellBookByTitle(title);
            return new ResponseEntity<>(soldBackBook, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to add a new book
    @PostMapping("/admin/add")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    // Endpoint to update a book's details
    @PutMapping("/admin/update/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookService.updateBook(id, updatedBook).orElse(null);
    }

    // Endpoint to delete a book
    @DeleteMapping("/admin/delete/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}

