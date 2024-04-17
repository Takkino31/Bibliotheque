import java.util.Objects;

/**
 * Book
 */
public class Book {
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;

    public Book(String title, String author, int publicationYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
    }

    // Getters et setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Titre: " + title + ", Auteur: " + author + ", Année de publication: " + publicationYear + ", ISBN: " + isbn;
    }

    @Override
    public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }
    Book other = (Book) obj;
    return Objects.equals(title, other.title) &&
           Objects.equals(author, other.author) &&
           Objects.equals(publicationYear, other.publicationYear) &&
           Objects.equals(isbn, other.isbn);
}

@Override
public int hashCode() {
    return Objects.hash(title, author, publicationYear, isbn);
}

}


