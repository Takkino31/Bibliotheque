public class LibraryException extends Exception {
    public LibraryException() {
        super();
    }

    public LibraryException(String message) {
        super(message);
    }

    // Exception pour un livre non trouvé
    public static class BookNotFoundException extends LibraryException {
        public BookNotFoundException(String message) {
            super(message);
        }
    }

    // Exception pour un utilisateur non trouvé
    public static class UserNotFoundException extends LibraryException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    // Exception pour un nombre maximal d'emprunts dépassé
    public static class MaxBorrowedBooksExceededException extends LibraryException {
        public MaxBorrowedBooksExceededException(String message) {
            super(message);
        }
    }

    // Exception pour un utilisateur non éligible
    public static class UserNotEligibleException extends LibraryException {
        public UserNotEligibleException(String message) {
            super(message);
        }
    }
}
