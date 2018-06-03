package com.example.service;

import com.example.dao.ReviewDAO;
import com.example.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;

    public void addReview(Review review) {
        reviewDAO.addReview(review);
    }

    public void updateReview(Review Review) {
        reviewDAO.updateReview(Review);
    }

    public Review getReview(int id) {
        Review review = reviewDAO.getReview(id);
        return review;
    }

    public void deleteReview(int id) {
        reviewDAO.deleteReview(id);
    }

    public List<Review> getReviews() {
        return reviewDAO.getReviews();
    }

    public List<Review> getReviewsByBook(int bookId) {
        return reviewDAO.getReviewsByBook(bookId);
    }
}