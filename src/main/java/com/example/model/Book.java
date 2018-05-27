package com.example.model;


import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

@Entity
@Table(name = "book")
@Proxy(lazy = false)
public class Book {

    @Id
    @Column(name = "bookId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "apiUrl")
    private String apiUrl;

    @Column(name = "bookImage")
    private Blob bookImage;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "books_authors",
            joinColumns = { @JoinColumn(name = "bookId") },
            inverseJoinColumns = { @JoinColumn(name = "authorId") })
    private Set<Author> authors;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "books_genres",
            joinColumns = { @JoinColumn(name = "bookId") },
            inverseJoinColumns = { @JoinColumn(name = "genreId") })
    private Set<BookGenre> genres;

    public Book() {
    }

    public Book(String title, String apiUrl, Blob bookImage, Set<Author> authors, Set<BookGenre> genres) {
        this.title = title;
        this.apiUrl = apiUrl;
        this.bookImage = bookImage;
        this.authors = authors;
        this.genres = genres;
    }

    public Set<BookGenre> getGenres() {
        return genres;
    }

    public void setGenres(Set<BookGenre> genres) {
        this.genres = genres;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Author> getAuthors() {
        return authors;
    }


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Blob getBookImage() {
        return bookImage;
    }

    public void setBookImage(Blob bookImage) {
        this.bookImage = bookImage;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", bookImage=" + bookImage +
                ", authors=" + authors +
                '}';
    }
}
