package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "recentlyviewed")
public class RecentlyViewed {

    @Id
    @Column(name = "recentlyViewedId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recentlyViewedId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "bookId")
    private int bookId;

    public RecentlyViewed() {
    }

    public RecentlyViewed(int userId, int bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public int getRecentlyViewedId() {
        return recentlyViewedId;
    }

    public void setRecentlyViewedId(int recentlyViewedId) {
        this.recentlyViewedId = recentlyViewedId;
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

