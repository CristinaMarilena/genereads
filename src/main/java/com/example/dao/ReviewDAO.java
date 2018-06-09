package com.example.dao;

import com.example.model.Review;

import java.util.List;

public interface ReviewDAO {

    public void addReview(Review review);

    public void updateReview(Review review);

    public Review getReview(int id);

    public void deleteReview(int id);

    public List<Review> getReviews();

    public List<Review> getReviewsByBook(int bookId);

    public Review getReviewByBookAndUser(int userId, int bookId);

}
