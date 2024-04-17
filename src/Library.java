import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Library
 */
public class Library extends LibraryException {
    private List<Book> books;
    private Map<User, List<Book>> borrowedBooks;
    private UserManager userManager;
    private int maxBorrowedBooks;

    public Library(int maxBorrowedBooks) {
        this.books = new ArrayList<>();
        this.borrowedBooks = new HashMap<>();
        this.userManager = new UserManager();
        this.maxBorrowedBooks = maxBorrowedBooks;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

    // Gestion des Livres
    public void addBook(String title, String author, int publicationYear, String isbn) {
        Book newBook = new Book(title, author, publicationYear, isbn);
        books.add(newBook);
        System.out.println("Le livre \"" + title + "\" a été ajouté à la bibliothèque.");
    }

    public void updateBook(String isbn, String newTitle, String newAuthor, int newPublicationYear) {
        Book book = findBook(isbn);
        if (book != null) {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            book.setPublicationYear(newPublicationYear);
            System.out.println("Le livre \"" + newTitle + "\" a été mis à jour avec succès.");
        } else {
            System.out.println("Aucun livre trouvé avec l'ISBN " + isbn + ".");
        }
    }

    public void removeBook(String isbn) {
        Book book = findBook(isbn);
        if (book != null) {
            books.remove(book);
            System.out.println("Le livre \"" + book.getTitle() + "\" a été supprimé de la bibliothèque.");
        } else {
            System.out.println("Aucun livre trouvé avec l'ISBN " + isbn + ".");
        }
    }

    public Book findBook(String title, String author, String isbn) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) &&
                    book.getAuthor().equalsIgnoreCase(author) &&
                    book.getIsbn().equalsIgnoreCase(isbn)) {
                return book;
            }
        }
        return null;
    }

    public Book findBookByAuthor(String author) throws BookNotFoundException {
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                return book;
            }
        }
        throw new BookNotFoundException("Aucun livre trouvé pour l'auteur : " + author);
    }

    public Book findBookByTitle(String title) throws BookNotFoundException {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        throw new BookNotFoundException("Aucun livre trouvé pour le titre : " + title);
    }

    public Book findBookByISBN(String isbn) throws BookNotFoundException {
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                return book;
            }
        }
        throw new BookNotFoundException("Aucun livre trouvé pour l'ISBN : " + isbn);
    }

    Book findBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                return book;
            }
        }
        return null;
    }

    // Gestion des Emprunts
    public void addUser(String name, int id, boolean isEligible) {
        userManager.addUser(name, id, isEligible);
    }

    public List<User> getAllUsers() {
        return userManager.getAllUsers();
    }

    public User findUser(int id) throws LibraryException.UserNotFoundException {
        return userManager.findUser(id);
    }

    public void setUserEligibility(int id, boolean eligible) throws LibraryException.UserNotFoundException {
        userManager.setUserEligibility(id, eligible);
    }

    // Gestion des Emprunts
    public void borrowBook(User user, Book book) throws LibraryException {
        if (user.isEligible() && user.getBorrowedBooks().size() < maxBorrowedBooks) {
            if (!borrowedBooks.containsKey(user)) {
                borrowedBooks.put(user, new ArrayList<>());
            }
            if (borrowedBooks.get(user).contains(book)) {
                throw new LibraryException.BookNotFoundException(
                        "Le livre " + book.getTitle() + " a déjà été emprunté par " + user.getName());
            } else {
                borrowedBooks.get(user).add(book);
                user.borrowBook(book);
                System.out.println(user.getName() + " a emprunté le livre : " + book.getTitle());
            }
        } else {
            if (!user.isEligible()) {
                throw new LibraryException.UserNotEligibleException(
                        user.getName() + " n'est pas éligible pour emprunter des livres.");
            } else if (user.getBorrowedBooks().size() >= maxBorrowedBooks) {
                throw new LibraryException.MaxBorrowedBooksExceededException(
                        user.getName() + " a atteint la limite maximale d'emprunts.");
            }
        }
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            boolean isBookBorrowed = false;
            for (List<Book> borrowedBooksList : borrowedBooks.values()) {
                if (borrowedBooksList.contains(book)) {
                    isBookBorrowed = true;
                    break;
                }
            }
            if (!isBookBorrowed) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public void returnBook(User user, Book book) {
        if (borrowedBooks.containsKey(user) && borrowedBooks.get(user).contains(book)) {
            borrowedBooks.get(user).remove(book);
            user.returnBook(book);
            System.out.println(user.getName() + " a retourné le livre : " + book.getTitle());
        } else {
            System.out.println("Le livre " + book.getTitle() + " n'a pas été emprunté par " + user.getName());
        }
    }

    public void displayBorrowedBooks(User user) {
        System.out.println("Livres empruntés par " + user.getName() + ":");
        for (Book book : user.getBorrowedBooks()) {
            System.out.println("- " + book);
        }
    }

    public void displayLibraryStatistics() {
        int totalBooks = books.size();
        int totalBorrowedBooks = 0;

        for (List<Book> userBooks : borrowedBooks.values()) {
            totalBorrowedBooks += userBooks.size();
        }

        int availableBooks = totalBooks - totalBorrowedBooks;
        if (availableBooks < 0) {
            availableBooks = 0;
        }

        System.out.println("Statistiques de la bibliothèque:");
        System.out.println("Nombre total de livres : " + totalBooks);
        System.out.println("Nombre de livres empruntés : " + totalBorrowedBooks);
        System.out.println("Nombre de livres disponibles : " + availableBooks);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }
}
