import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryTerminalUI {
    private Library library;
    private UserManager userManager;
    private Scanner scanner;

    public LibraryTerminalUI(Library library) {
        this.library = library;
        this.userManager = library.getUserManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.println("Bienvenue dans la bibliothèque !");
        displayMainMenu();
    }

    private void displayMainMenu() throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gestion des livres");
        System.out.println("2. Gestion des utilisateurs");
        System.out.println("3. Emprunter un livre");
        System.out.println("4. Retourner un livre");
        System.out.println("5. Afficher les livres empruntés par un utilisateur");
        System.out.println("6. Rechercher un livre");
        System.out.println("7. Afficher la liste des utilisateurs");
        System.out.println("8. Supprimer un utilisateur");
        System.out.println("9. Supprimer un livre");
        System.out.println("10. Afficher les statistiques de la bibliothèque");
        System.out.println("11. Quitter");
        System.out.print("Choisissez une option : ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier
    
        switch (choice) {
            case 1:
                displayBookManagementMenu();
                break;
            case 2:
                displayUserManagementMenu();
                break;
            case 3:
                borrowBook();
                break;
            case 4:
                returnBook();
                break;
            case 5:
                displayBorrowedBooks();
                break;
            case 6:
                searchBook();
                break;
            case 7:
                displayUsersList();
                break;
            case 8:
                deleteUser();
                break;
            case 9:
                deleteBook();
                break;
            case 10:
                library.displayLibraryStatistics();
                displayMainMenu();
                break;
            case 11:
                System.out.println("Merci d'avoir utilisé notre système de gestion de bibliothèque !");
                System.exit(0);
                break;
            default:
                System.out.println("Option invalide. Veuillez choisir une option valide.");
                displayMainMenu();
                break;
        }
    }
    private void displayBookManagementMenu()
            throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.println("\n=== GESTION DES LIVRES ===");
        System.out.println("1. Ajouter un livre");
        System.out.println("2. Retourner au menu principal");
        System.out.print("Choisissez une option : ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier

        switch (choice) {
            case 1:
                addBook();
                displayBookManagementMenu();
                break;
            case 2:
                displayMainMenu();
                break;
            default:
                System.out.println("Option invalide. Veuillez choisir une option valide.");
                displayBookManagementMenu();
                break;
        }
    }

    private void displayUserManagementMenu()
            throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.println("\n=== GESTION DES UTILISATEURS ===");
        System.out.println("1. Retourner au menu principal");
        System.out.print("Choisissez une option : ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier

        switch (choice) {
            case 1:
                displayMainMenu();
                break;
            default:
                System.out.println("Option invalide. Veuillez choisir une option valide.");
                displayUserManagementMenu();
                break;
        }
    }

    private void addBook() {
        System.out.println("\n=== AJOUT D'UN LIVRE ===");
        System.out.print("Titre : ");
        String title = scanner.nextLine();
        System.out.print("Auteur : ");
        String author = scanner.nextLine();
        System.out.print("Année de publication : ");
        int publicationYear = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier
        System.out.print("ISBN : ");
        String isbn = scanner.nextLine();

        library.addBook(title, author, publicationYear, isbn);
        System.out.println("Le livre a été ajouté avec succès !");
    }

    private void borrowBook() throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.println("\n=== EMPRUNTER UN LIVRE ===");
        System.out.print("ID de l'utilisateur : ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier

        List<Book> availableBooks = library.getAvailableBooks();
        if (availableBooks.isEmpty()) {
            System.out.println("Désolé, il n'y a aucun livre disponible pour le moment.");
            displayMainMenu();
            return;
        }

        System.out.println("Voici la liste des livres disponibles :");
        for (int i = 0; i < availableBooks.size(); i++) {
            Book book = availableBooks.get(i);
            System.out.println((i + 1) + ". " + book.getTitle() + " (Auteur: " + book.getAuthor() + ", ISBN: "
                    + book.getIsbn() + ")");
        }

        System.out.print("Veuillez choisir le numéro du livre que vous voulez emprunter : ");
        int bookIndex = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier

        if (bookIndex < 1 || bookIndex > availableBooks.size()) {
            System.out.println("Numéro de livre invalide.");
            displayMainMenu();
            return;
        }

        Book selectedBook = availableBooks.get(bookIndex - 1);
        try {
            library.borrowBook(library.findUser(userId), selectedBook);
            System.out.println("L'emprunt a été effectué avec succès !");
        } catch (LibraryException e) {
            System.out.println("Une erreur s'est produite lors de l'emprunt : " + e.getMessage());
        }

        // Ne ferme pas la session après l'emprunt
        displayMainMenu();
    }

    private void returnBook() throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.println("\n=== RETOURNER UN LIVRE ===");
        System.out.print("ID de l'utilisateur : ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier
        System.out.print("ISBN du livre : ");
        String isbn = scanner.nextLine();

        try {
            library.returnBook(library.findUser(userId), library.findBook(isbn));
            System.out.println("Le livre a été retourné avec succès !");
        } catch (LibraryException e) {
            System.out.println("Une erreur s'est produite lors du retour du livre : " + e.getMessage());
        }

        // Ne ferme pas la session après le retour
        displayMainMenu();
    }

    private void displayBorrowedBooks()
            throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.println("\n=== AFFICHER LES LIVRES EMPRUNTÉS PAR UN UTILISATEUR ===");
        System.out.print("ID de l'utilisateur : ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier

        try {
            User user = library.findUser(userId);
            List<Book> borrowedBooks = user.getBorrowedBooks();
            if (borrowedBooks.isEmpty()) {
                System.out.println("Cet utilisateur n'a emprunté aucun livre.");
            } else {
                System.out.println("Livres empruntés par " + user.getName() + ":");
                for (Book book : borrowedBooks) {
                    System.out.println("- " + book.getTitle() + " (Auteur: " + book.getAuthor() + ", ISBN: "
                            + book.getIsbn() + ")");
                }
            }
        } catch (LibraryException e) {
            System.out
                    .println("Une erreur s'est produite lors de l'affichage des livres empruntés : " + e.getMessage());
        }

        // Ne ferme pas la session après l'affichage des livres empruntés
        displayMainMenu();
    }

    private void searchBook() throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.println("\n=== RECHERCHER UN LIVRE ===");
        System.out.println("Que voulez-vous rechercher ?");
        System.out.println("1. Titre");
        System.out.println("2. Auteur");
        System.out.println("3. ISBN");
        System.out.print("Choisissez une option : ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier

        switch (choice) {
            case 1:
                searchByTitle();
                break;
            case 2:
                searchByAuthor();
                break;
            case 3:
                searchByISBN();
                break;
            default:
                System.out.println("Option invalide. Veuillez choisir une option valide.");
                searchBook();
                break;
        }
    }

    private void searchByTitle() throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.print("Titre du livre : ");
        String title = scanner.nextLine();
        Book book = library.findBookByTitle(title);
        if (book == null) {
            System.out.println("Aucun livre trouvé avec ce titre.");
        } else {
            System.out.println("Livre trouvé :");
            System.out.println("- " + book.getTitle() + " (Auteur: " + book.getAuthor() + ", ISBN: " + book.getIsbn()
                    + ")");
        }
        displayMainMenu();
    }

    private void searchByAuthor()
            throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.print("Auteur du livre : ");
        String author = scanner.nextLine();
        List<Book> books = new ArrayList<>();
        int bookId = 1; // Commencer à 1 car les ID de livre commencent généralement à 1
        Book book;
        while ((book = library.findBook(String.valueOf(bookId))) != null) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                books.add(book);
            }
            bookId++;
        }

        if (books.isEmpty()) {
            System.out.println("Aucun livre trouvé avec cet auteur.");
        } else {
            System.out.println("Livres trouvés :");
            for (Book b : books) {
                System.out.println("- " + b.getTitle() + " (Auteur: " + b.getAuthor() + ", ISBN: " + b.getIsbn()
                        + ")");
            }
        }
        displayMainMenu();
    }

    private void searchByISBN() throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.print("ISBN du livre : ");
        String isbn = scanner.nextLine();
        Book book = library.findBookByISBN(isbn);
        if (book == null) {
            System.out.println("Aucun livre trouvé avec cet ISBN.");
        } else {
            System.out.println("Livre trouvé :");
            System.out.println("- " + book.getTitle() + " (Auteur: " + book.getAuthor() + ", ISBN: " + book.getIsbn()
                    + ")");
        }
        displayMainMenu();
    }


    private void deleteUser() throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.println("\n=== SUPPRIMER UN UTILISATEUR ===");
        List<User> users = library.getUserManager().getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur enregistré pour le moment.");
            displayMainMenu();
            return;
        }
    
        System.out.println("Voici la liste des utilisateurs :");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println((i + 1) + ". ID: " + user.getId() + ", Nom: " + user.getName());
        }
    
        System.out.print("Choisissez le numéro de l'utilisateur à supprimer : ");
        int userIndex = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier
    
        if (userIndex < 1 || userIndex > users.size()) {
            System.out.println("Numéro d'utilisateur invalide.");
            displayMainMenu();
            return;
        }
    
        User userToDelete = users.get(userIndex - 1);
        library.getUserManager().deleteUser(userToDelete.getId());
        System.out.println("L'utilisateur " + userToDelete.getName() + " a été supprimé avec succès.");
        displayMainMenu();
    }
    
    private void deleteBook() throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.println("\n=== SUPPRIMER UN LIVRE ===");
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("Aucun livre enregistré pour le moment.");
            displayMainMenu();
            return;
        }
    
        System.out.println("Voici la liste des livres :");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println((i + 1) + ". Titre: " + book.getTitle() + ", Auteur: " + book.getAuthor() + ", ISBN: " + book.getIsbn());
        }
    
        System.out.print("Choisissez le numéro du livre à supprimer : ");
        int bookIndex = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne restante après la saisie de l'entier
    
        if (bookIndex < 1 || bookIndex > books.size()) {
            System.out.println("Numéro de livre invalide.");
            displayMainMenu();
            return;
        }
    
        Book bookToDelete = books.get(bookIndex - 1);
        library.removeBook(bookToDelete.getIsbn());
        System.out.println("Le livre " + bookToDelete.getTitle() + " a été supprimé avec succès.");
        displayMainMenu();
    }

    private void displayUsersList()
            throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        System.out.println("\n=== LISTE DES UTILISATEURS ===");
        List<User> users = userManager.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur enregistré pour le moment.");
        } else {
            for (User user : users) {
                System.out.println("ID: " + user.getId() + ", Nom: " + user.getName());
            }
        }
        displayMainMenu();
    }
}