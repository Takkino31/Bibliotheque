import java.util.ArrayList;
import java.util.List;

/**
 * User
 */
public class User {
    private String name;
    private int id;
    private boolean eligible;
    private List<Book> borrowedBooks;

    public User(String name, int id, boolean eligible) {
        this.name = name;
        this.id = id;
        this.eligible = eligible;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}