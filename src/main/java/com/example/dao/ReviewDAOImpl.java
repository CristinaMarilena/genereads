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
        reviewToUpdate.setTimestamp(review.getTimestamp());
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

    @Override
    public List<Review> getReviewsByBook(int bookId) {
        List<Review> reviews = getCurrentSession().createQuery("from Review where bookid=:Bookid").setParameter("Bookid", bookId).list();
        if (!reviews.isEmpty())
            return reviews;
        else return null;
    }

    @Override
    public Review getReviewByBookAndUser(int userId, int bookId) {
        List<Review> reviewList = getCurrentSession().createQuery("from Review where userId=:UserId and bookId=:BookId and rating > 0")
                .setParameter("UserId", userId)
                .setParameter("BookId", bookId)
                .list();
        if (!reviewList.isEmpty())
            return reviewList.get(0);
        else return null;
    }


}