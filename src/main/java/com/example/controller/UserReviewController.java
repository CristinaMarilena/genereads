package com.example.controller;

import com.example.model.Account;
import com.example.model.Book;
import com.example.model.Review;
import com.example.service.AccountService;
import com.example.service.BookService;
import com.example.service.ReviewService;
import com.example.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class UserReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "reviews/addrating/{rating}/bookapiurl/{apiurl}", method = RequestMethod.POST)
    public Review addRating(@PathVariable int rating, String apiurl) {
        Account user = accountService.findByEmail(securityService.findLoggedInUsername());
        Review review = new Review();
        review.setUserId(user.getUserId());
        review.setRating(rating);
        review.setBookId(bookService.getBookByUrl(apiurl).getBookId());
        reviewService.addReview(review);
        return review;
    }

    @RequestMapping(value = "reviews/addreview/{review}/bookapiurl/{apiurl}", method = RequestMethod.POST)
    public Review addReview(@PathVariable String reviewText, String apiurl) {
        Account user = accountService.findByEmail(securityService.findLoggedInUsername());
        Review review = new Review();
        review.setUserId(user.getUserId());
        review.setReview(reviewText);
        review.setBookId(bookService.getBookByUrl(apiurl).getBookId());
        reviewService.addReview(review);
        return review;
    }

    @RequestMapping(value = "reviews/updatereview/{reviewId}/review{reviewText}", method = RequestMethod.POST)
    public Review updateReview(@PathVariable int reviewId, String reviewText) {
        Review existingReview = reviewService.getReview(reviewId);

        if(reviewText != null){
            existingReview.setReview(reviewText);
        }
        reviewService.updateReview(existingReview);
        return existingReview;
    }


    @RequestMapping(value = "reviews/updaterating/{reviewId}/rating{rating}", method = RequestMethod.POST)
    public Review updateRating(@PathVariable int reviewId, int rating) {
        Review existingReview = reviewService.getReview(reviewId);

        if(rating > -1 && rating < 11){
            existingReview.setRating(rating);
        }
        reviewService.updateReview(existingReview);
        return existingReview;
    }


    @RequestMapping(value = "reviews/deleterating{reviewId}", method = RequestMethod.POST)
    public Review deleteRating(@PathVariable int reviewId) {
        Review existingReview = reviewService.getReview(reviewId);
        reviewService.deleteReview(reviewId);
        return existingReview;
    }

    @RequestMapping(value = "reviews/deleterating{reviewId}", method = RequestMethod.POST)
    public Review deleteReview(@PathVariable int reviewId) {
        Review existingReview = reviewService.getReview(reviewId);
        reviewService.deleteReview(reviewId);
        return existingReview;
    }

    @RequestMapping(value = "reviews/allreviews/{apiurl}", method = RequestMethod.GET)
    public List<Review> getAllReviewsOfABook(@PathVariable String apiurl) {
        Book book = bookService.getBookByUrl(apiurl);
        List<Review> reviews= reviewService.getReviewsByBook(book.getBookId());
        return reviews;
    }

    @RequestMapping(value = "reviews/getrating/{apiurl}", method = RequestMethod.GET)
    public double getRatingOfABook(@PathVariable String apiurl) {
        Book book = bookService.getBookByUrl(apiurl);
        List<Review> reviews= reviewService.getReviewsByBook(book.getBookId());
        int sum = 0;
        for(Review rev: reviews){
            sum = rev.getRating();
        }
        return sum/reviews.size();
    }

}