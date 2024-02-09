package com.OOD.Project.usedtextbooks.service;

import com.OOD.Project.usedtextbooks.entity.Book;
import com.OOD.Project.usedtextbooks.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService implements BookServiceInter {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }
    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id).map(existingBook -> {

            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthors(updatedBook.getAuthors());
            existingBook.setEdition(updatedBook.getEdition());
            existingBook.setIsbn(updatedBook.getIsbn());
            existingBook.setQuantity(updatedBook.getQuantity());
            existingBook.setPrice(updatedBook.getPrice());
            return bookRepository.save(existingBook);
        });
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> buyBook(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            if (book.getQuantity() > 0) {
                book.setQuantity(book.getQuantity() - 1);
                bookRepository.save(book);
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    @Override
    public Book buyBookByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContaining(title);
        if (books.isEmpty()) {
            throw new RuntimeException("Book not found");
        }

        Book book = books.get(0); // Get the first book with the given title
        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1); // Decrease the quantity by one
            return bookRepository.save(book); // Save the updated book entity
        } else {
            throw new RuntimeException("Book is out of stock");
        }
    }

    public Book sellBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book newBook = new Book();

        if (bookOptional.isPresent()) {
            // We have found a book with the given ID
            Book book = bookOptional.get();

            // Copy properties from the found book to the new book
            newBook.setIsbn(book.getIsbn());
            newBook.setAuthors(book.getAuthors());
            newBook.setEdition(book.getEdition());
            // Depreciate the book's price by 10% for the new book
            newBook.setPrice(book.getPrice().multiply(BigDecimal.valueOf(0.9)));
            // Set the quantity for the new book
            newBook.setQuantity(1);
            // Update the title for the new book with a note about being sold back
            newBook.setTitle(book.getTitle() + "(" + (book.getSoldBackCount() +1)+ ")");
            newBook.setSoldBackCount(book.getSoldBackCount() + 1);
            // Save the new book to the repository
            bookRepository.save(newBook);
        } else {
            throw new NoSuchElementException("Book with ID " + id + " not found.");
        }

        return newBook;
    }




    @Override
    public Book sellBookByTitle(String title) {
        List<Book> booksWithMatchingTitle = bookRepository.findByTitleContaining(title);
        Book newBook = new Book();

        if (!booksWithMatchingTitle.isEmpty()) {
            // Assuming we are selling back the first book that matches the title
            Book book = booksWithMatchingTitle.get(0);

            // Copy properties from the found book to the new book
            newBook.setIsbn(book.getIsbn());
            newBook.setAuthors(book.getAuthors());
            newBook.setEdition(book.getEdition());
            // Depreciate the book's price by 10% for the new book
            newBook.setPrice(book.getPrice().multiply(BigDecimal.valueOf(0.9)));
            // Set the quantity for the new book
            newBook.setQuantity(1);
            // Update the title for the new book with a note about being sold back
            newBook.setTitle(book.getTitle() + "(" + (book.getSoldBackCount() +1)+ ")");
            newBook.setSoldBackCount(book.getSoldBackCount()+1);
            // Save the new book to the repository
            bookRepository.save(newBook);
        } else {
            newBook.setTitle(title);
            // Set a default price
            newBook.setPrice(new BigDecimal("10.00")); // Example default price
            newBook.setQuantity(1);
            bookRepository.save(newBook);
        }

        return newBook;
    }



}

