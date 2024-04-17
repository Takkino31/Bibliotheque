public class Main {
    public static void main(String[] args)
            throws LibraryException.BookNotFoundException, LibraryException.UserNotFoundException {
        // Création de la bibliothèque avec une limite d'emprunt de 3 livres
        Library library = new Library(3);

        // Création de quelques livres et utilisateurs
        Book book1 = new Book("Livre 1", "Auteur 1", 2020, "ISBN1");
        Book book2 = new Book("Livre 2", "Auteur 2", 2021, "ISBN2");
        User user1 = new User("John Doe", 1001, true); // Éligible à l'emprunt
        User user2 = new User("Jane Smith", 1002, true); // Éligible à l'emprunt

        // Ajout des livres à la bibliothèque
        library.addBook(book1.getTitle(), book1.getAuthor(), book1.getPublicationYear(), book1.getIsbn());
        library.addBook(book2.getTitle(), book2.getAuthor(), book2.getPublicationYear(), book2.getIsbn());

        // Ajout des utilisateurs à la bibliothèque
        library.addUser(user1.getName(), user1.getId(), user1.isEligible());
        library.addUser(user2.getName(), user2.getId(), user2.isEligible());

        // Création de l'interface utilisateur avec le terminal et démarrage
        LibraryTerminalUI terminalUI = new LibraryTerminalUI(library);
        terminalUI.start();
    }
}
