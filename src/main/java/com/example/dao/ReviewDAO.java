package com.example.dao;

import com.example.model.Review;

import java.util.List;

public interface ReviewDAO {

    public void addReview(Review review);

    public void updateReview(Review review);

    public Review getReview(int id);

    public void deleteReview(int id);

    public List<Review> getReviews();

}
