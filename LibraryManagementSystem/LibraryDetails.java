// package LibraryManagementSystem;

import java.util.ArrayList;
import java.util.Scanner;

class Books {

    String author;
    String title;
    String id;
    boolean isAvailable;

    public Books(String bid, String bauthor, String btitle) {
        this.id = bid;
        this.author = bauthor;
        this.title = btitle;
        this.isAvailable = true;
    }

    void display() {
        System.out.println("Book ID: " + id + ", Title: " + title + ", Author: " + author + ", Available: " + isAvailable);
    }
}

class Users {

    String username;
    int userID;
    ArrayList<Books> issuedBooks = new ArrayList<Books>();

    public Users(int id, String uname) {
        this.userID = id;
        this.username = uname;
    }

    void viewIssuedBooks() {
        System.out.println("Books issued to " + username + ":");
        if (issuedBooks.isEmpty()) {
            System.out.println("  No books issued.");
        } else {
            for (Books b : issuedBooks) {
                System.out.println("  - " + b.title);
            }
        }
    }

}

class Library {

    ArrayList<Books> books = new ArrayList<Books>();

    void addBook(Books book) {
        books.add(book);
        System.out.println("Book added successfully!");
    }

    void searchBookById(String searchId) {
        for (Books b : books) {
            if (b.id.equals(searchId)) {
                System.out.println(" Book found:");
                b.display();
                return;
            }
        }
        System.out.println(" Book not found.");
    }

    void borrowBook(String title, Users user) {
        for (Books b : books) {
            if (b.title.equalsIgnoreCase(title)) {
                if (b.isAvailable) {
                    b.isAvailable = false;
                    user.issuedBooks.add(b);
                    System.out.println(" Book issued: " + b.title);
                } else {
                    System.out.println(" Book already issued.");
                }
                return;
            }
        }
        System.out.println(" Book not found.");
    }

    void returnBook(String title, Users user) {
        for (Books b : user.issuedBooks) {
            if (b.title.equalsIgnoreCase(title)) {
                b.isAvailable = true;
                user.issuedBooks.remove(b);
                System.out.println(" Book returned: " + b.title);
                return;
            }
        }
        System.out.println(" This book was not issued to you.");
    }

}

public class LibraryDetails {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Library lib = new Library();

        System.out.print("Enter User ID:");
        int userId = sc.nextInt();

        System.out.println("Enter Username:");
        String username = sc.next();

        Users user = new Users(userId, username);
        int choice;
        while (true) {
            System.out.println("******** Library Menu ********");
            System.out.println("1. Add Book \t 2.Search Book By ID \t 3. BorrowBook \t 4.Return Book \t 5.Exit");
            System.out.print("Enter yourr Choice:");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    String bid = sc.next();
                    System.out.print("Enter Title: ");
                    String title = sc.next();
                    System.out.print("Enter Author: ");
                    String author = sc.next();
                    lib.addBook(new Books(bid, author, title));
                    break;
                case 2:
                    System.out.print("Enter Book ID: ");
                    String sid = sc.next();
                    lib.searchBookById(sid);
                    break;
                case 3:
                    System.out.print("Enter Title: ");
                    String btitle = sc.next();
                    lib.borrowBook(btitle, user);
                    break;
                case 4:
                    System.out.print("Enter Title: ");
                    String rtitle = sc.next();
                    lib.returnBook(rtitle, user);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid!!!!");
            }
            // sc.close();
        }
    }
}
