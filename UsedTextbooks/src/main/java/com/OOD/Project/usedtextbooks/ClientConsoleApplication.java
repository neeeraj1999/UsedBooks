package com.OOD.Project.usedtextbooks;


import com.OOD.Project.usedtextbooks.entity.Book;
import com.OOD.Project.usedtextbooks.entity.User;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientConsoleApplication {

    private static final String BASE_URL = "http://localhost:8080/api";
    private RestTemplate restTemplate = new RestTemplate();
    private User currentUser = null;

    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        try {
            ResponseEntity<User> response = restTemplate.getForEntity(BASE_URL + "/users/" + username, User.class);
            User user = response.getBody();
            if (user != null && password.equals(user.getPassword())) {
                currentUser = user;
                System.out.println("Logged in successfully as " + user.getRole());
            } else {
                System.out.println("Invalid credentials.");
            }
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("User not found!");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }



    }

    private void displayAllBooks() {
        ResponseEntity<Book[]> response = restTemplate.getForEntity(BASE_URL + "/books/getAll", Book[].class);
        Book[] books = response.getBody();
        for (Book book : books) {
            System.out.println(book);
        }
    }

public void searchBookByTitle() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the book title to search for: ");
    String title = scanner.nextLine();

    String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
    String searchUrl = BASE_URL + "/books/search?title=" + encodedTitle;

    try {
        ResponseEntity<Book[]> response = restTemplate.getForEntity(searchUrl, Book[].class);
        Book[] books = response.getBody();
        // Process the books array as needed, for example, print the books
        if (books != null) {
            for (Book book : books) {
                System.out.println(book);
            }
        } else {
            System.out.println("No books found with the title: " + title);
        }
    } catch (Exception e) {
        System.out.println("An error occurred while searching for books: " + e.getMessage());
    }
}




    private void displayAllUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(BASE_URL + "/users/getAll", User[].class);
        User[] users = response.getBody();
        for (User user : users) {
            System.out.println(user);
        }
    }

    private void searchUserByUsername() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        ResponseEntity<User> response = restTemplate.getForEntity(BASE_URL + "/users/" + username, User.class);

        User user = response.getBody();
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("User not found.");
        }
    }

private Book inputBookDetails() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter book ISBN: ");
    String isbn = scanner.nextLine();

    System.out.print("Enter book title: ");
    String title = scanner.nextLine();

    System.out.print("Enter book author: ");
    String author = scanner.nextLine();

    System.out.print("Enter book edition: ");
    String edition = scanner.nextLine();

    System.out.print("Enter book price: ");
    double priceInput = scanner.nextDouble();
    BigDecimal price = BigDecimal.valueOf(priceInput);
    System.out.print("Enter book quantity: ");
    int quantity = scanner.nextInt();
    return new Book(isbn, author, title, edition, price, quantity);
}


    private User inputUserDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();
        return new User(username, password, role);
    }

    private void addBook() {
        Book book = inputBookDetails();
        restTemplate.postForEntity(BASE_URL + "/books/admin/add", book, Book.class);
        System.out.println("Book added successfully.");
    }

    private void updateBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book ID to update: ");
        long id = scanner.nextLong();
        scanner.nextLine();  // Consume newline
        Book updatedBook = inputBookDetails();
        restTemplate.put(BASE_URL + "/books/admin/update/" + id, updatedBook);
        System.out.println("Book updated successfully.");
    }

    private void deleteBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book ID to delete: ");
        long id = scanner.nextLong();
        restTemplate.delete(BASE_URL + "/books/admin/delete/" + id);
        System.out.println("Book deleted successfully.");
    }

    private void addUser() {
        User user = inputUserDetails();
        restTemplate.postForEntity(BASE_URL + "/users/admin/add", user, User.class);
        System.out.println("User added successfully.");
    }

    private void updateUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username of user to update: ");
        String username = scanner.nextLine();
        User updatedUser = inputUserDetails();
        restTemplate.put(BASE_URL + "/users/admin/update/" + username, updatedUser);
        System.out.println("User updated successfully.");
    }

    private void deleteUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username of user to delete: ");
        String username = scanner.nextLine();
        restTemplate.delete(BASE_URL + "/users/admin/delete/" + username);
        System.out.println("User deleted successfully.");
    }

    public void buyBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the title of the book you want to buy: ");
        String title = scanner.nextLine();

        try {
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
            String buyUrl = BASE_URL + "/books/buy/" + encodedTitle;
            HttpEntity<Book> request = new HttpEntity<>(new Book()); // If additional book data is needed, construct the Book object accordingly
            ResponseEntity<Book> response = restTemplate.postForEntity(buyUrl, request, Book.class);
            Book book = response.getBody();
            System.out.println("You have successfully bought the book: " + book.getTitle());
        } catch (HttpClientErrorException e) {
            System.out.println("An error occurred while trying to buy the book: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    public void sellBackBookByTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the title of the book you want to sell back: ");
        String title = scanner.nextLine(); // Get user input for the title
        String url = BASE_URL + "/books/sellBackByTitle/" + title;
        try {
            ResponseEntity<Book> response = restTemplate.postForEntity(url, null, Book.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                System.out.println("Book sold back successfully: " + response.getBody());
            } else {
                System.out.println("Failed to sell back book. Status code: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            System.out.println("An error occurred: " + e.getResponseBodyAsString());
        }
    }

    private void sellBackBookById() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the book to sell: ");
        Long bookId = scanner.nextLong(); // Assuming the ID is of type Long
        scanner.nextLine(); // Consume the newline

        try {
            // Make a call to the API endpoint to sell the book by its ID
            ResponseEntity<Book> response = restTemplate.postForEntity(
                    BASE_URL + "/books/sellBackById/" + bookId,
                    null, // No request body is needed for this call
                    Book.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                // Assuming the response body contains the book that was sold
                Book soldBook = response.getBody();
                System.out.println("Book sold successfully: " + soldBook);
            } else {
                System.out.println("Failed to sell the book. Status: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            // Handle specific client errors (e.g., 404 Not Found)
            System.err.println("Error selling book: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }




    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (currentUser == null) {
            login();
        }

        if ("admin".equals(currentUser.getRole())) {
            int adminChoice;
            do {
                System.out.println("Admin Menu:");
                System.out.println("1. Display all books");
                System.out.println("2. Search book by title");
                System.out.println("3. Display all users");
                System.out.println("4. Search user by username");
                System.out.println("5. Add book");
                System.out.println("6. Update book");
                System.out.println("7. Delete book");
                System.out.println("8. Add user");
                System.out.println("9. Update user");
                System.out.println("10. Delete user");
                System.out.println("11. Logout");
                System.out.print("Enter your choice: ");
                adminChoice = scanner.nextInt();
                scanner.nextLine();  // consume the newline

                switch (adminChoice) {
                    case 1:
                        displayAllBooks();
                        break;
                    case 2:
                        searchBookByTitle();
                        break;
                    case 3:
                        displayAllUsers();
                        break;
                    case 4:
                        searchUserByUsername();
                        break;
                    case 5:
                        addBook();
                        break;
                    case 6:
                        updateBook();
                        break;
                    case 7:
                        deleteBook();
                        break;
                    case 8:
                        addUser();
                        break;
                    case 9:
                        updateUser();
                        break;
                    case 10:
                        deleteUser();
                        break;
                    case 11:
                        currentUser = null;
                        break;
                }
            } while (adminChoice != 11);
        } else {  // user functionalities
            int userChoice;
            do {
                System.out.println("User Menu:");
                System.out.println("1. Display all books");
                System.out.println("2. Search book by title");
                System.out.println("3. Buy a book");
                System.out.println("4. Sell back a book by Title");
                System.out.println("5. Sell back a book by id");
                System.out.println("6. Logout");
                System.out.print("Enter your choice: ");
                userChoice = scanner.nextInt();
                scanner.nextLine();  // consume the newline

                switch (userChoice) {
                    case 1:
                        displayAllBooks();
                        break;
                    case 2:
                        searchBookByTitle();
                        break;

                    case 3:
                        buyBook();
                        break;
                    case 4:
                        sellBackBookByTitle();
                        break;
                    case 5:
                        sellBackBookById();
                        break;
                    case 6:
                        currentUser = null;
                        break;
                }
            } while (userChoice != 6);
        }
    }

    public static void main(String[] args) {
        ClientConsoleApplication app = new ClientConsoleApplication();
        app.run();
    }
}
