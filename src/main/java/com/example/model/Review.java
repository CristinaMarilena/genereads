package com.example.model;

import org.hibernate.annotations.Target;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @Column(name = "reviewId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "bookId")
    private int bookId;

    @Column(name = "rating")
    @Range(min = 0, max = 10)
    private int rating;

    @Column(name = "review")
    private String review;

    @Column(name = "timestamp")
    private String timestamp;

    public Review() {
    }

    public Review(int userId, int bookId, int rating, String review, String timestamp) {
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
        this.review = review;
        this.timestamp = timestamp;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
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
        this.bookId = Integer.valueOf(bookId);
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }
}
