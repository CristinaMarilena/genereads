package com.example.model;


import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "book_genre")
@Proxy(lazy = false)
public class BookGenre {

    @Id
    @Column(name = "genreId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;

    @Column
    private String genreName;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "genres")
    private Set<Book> books;

    public BookGenre(String genreName, Set<Book> books) {
        this.genreName = genreName;
        this.books = books;
    }

    public BookGenre() {
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
