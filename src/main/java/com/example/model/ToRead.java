package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "toread")
public class ToRead {

    @Id
    @Column(name = "toReadId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int toReadId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "bookId")
    private int bookId;

    public ToRead() {
    }

    public ToRead(int userId, int bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public int getToReadId() {
        return toReadId;
    }

    public void setToReadId(int toReadId) {
        this.toReadId = toReadId;
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

