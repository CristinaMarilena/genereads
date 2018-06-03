package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "currentlyreading")
public class CurrentlyReading {

    @Id
    @Column(name = "currentlyReadingId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int currentlyReadingId;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "userId")
    private int userId;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "bookId")
    private int bookId;

    public CurrentlyReading() {
    }

    public CurrentlyReading(int userId, int bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public int getCurrentlyReadingId() {
        return currentlyReadingId;
    }

    public void setCurrentlyReadingId(int currentlyReadingId) {
        this.currentlyReadingId = currentlyReadingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}

