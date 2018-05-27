package com.example.dao;

import com.example.model.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDAOImpl implements ReviewDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void addReview(Review review) {
        getCurrentSession().save(review);
    }

    public void updateReview(Review review) {
        Review reviewToUpdate = getReview(review.getReviewId());
        reviewToUpdate.setBookId(review.getBookId());
        reviewToUpdate.setRating(review.getRating());
        reviewToUpdate.setReview(review.getReview());
        reviewToUpdate.setUserId(review.getUserId());
        getCurrentSession().update(reviewToUpdate);
    }

    public Review getReview(int id) {
        Review review = (Review) getCurrentSession().get(Review.class, id);
        return review;
    }

    public void deleteReview(int id) {
        Review Review = getReview(id);
        if (Review != null)
            getCurrentSession().delete(Review);
    }

    public List<Review> getReviews() {
        return getCurrentSession().createQuery("from Review").list();
    }

}