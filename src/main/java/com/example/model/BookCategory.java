package com.example.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bookcategory")
public class BookCategory {

    @Id
    @Column(name = "categoryId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Column
    private String categoryName;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "categories")
    @JsonManagedReference
    private Set<Book> books;

    public BookCategory(String categoryName, Set<Book> books) {
        this.categoryName = categoryName;
        this.books = books;
    }

    public BookCategory() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
