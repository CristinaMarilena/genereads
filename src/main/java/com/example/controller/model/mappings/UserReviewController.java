package com.example.controller.model.mappings;

import com.example.model.Account;
import com.example.model.Book;
import com.example.model.RecentlyViewed;
import com.example.model.Review;
import com.example.service.*;
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

    @Autowired
    private RecentlyViewedService recentlyViewedService;

    @RequestMapping(value = "reviews/addrating/{rating}/bookurl/{bookurl}", method = RequestMethod.POST)
    public Review addRating(@PathVariable int rating, @PathVariable String bookurl) {
        int userId = accountService.findByEmail("cris@email").getUserId();
        int bookId = bookService.getBookByUrl(bookurl).getBookId();
        Review reviewToBeFound = reviewService.getReviewByBookAndUser(userId, bookId);
         if(reviewToBeFound == null){
             Review review = new Review();

             review.setUserId(userId);
             review.setRating(rating * 2);

             review.setBookId(bookId);
             reviewService.addReview(review);

             RecentlyViewed recentlyViewed = new RecentlyViewed();
             recentlyViewed.setBookId(bookId);
             recentlyViewed.setUserId(userId);
             recentlyViewedService.addRecentlyViewed(recentlyViewed, userId);

             return review;
         }
         else {
             reviewToBeFound.setRating(rating*2);
             reviewService.updateReview(reviewToBeFound);
             return reviewToBeFound;
         }
    }

    @RequestMapping(value = "reviews/getrating/bookurl/{bookurl}", method = RequestMethod.GET)
    public Review getRating(@PathVariable String bookurl) {
        int userId = accountService.findByEmail("cris@email").getUserId();
        int bookId = bookService.getBookByUrl(bookurl).getBookId();
        return reviewService.getReviewByBookAndUser(userId, bookId);
    }


    @RequestMapping(value = "reviews/addreview/{review}/bookId{bookId}", method = RequestMethod.POST)
    public Review addReview(@PathVariable String reviewText, @PathVariable int bookId) {
        Account user = accountService.findByEmail("crist@tradeshift.com");
        Review review = new Review();
        review.setUserId(user.getUserId());
        review.setReview(reviewText);

        review.setBookId(bookId);
        reviewService.addReview(review);

        RecentlyViewed recentlyViewed = new RecentlyViewed();
        recentlyViewed.setBookId(bookId);
        recentlyViewed.setUserId(user.getUserId());
        recentlyViewedService.addRecentlyViewed(recentlyViewed, user.getUserId());

        return review;
    }

    @RequestMapping(value = "reviews/updatereview/{reviewId}/review{reviewText}", method = RequestMethod.POST)
    public Review updateReview(@PathVariable int reviewId, String reviewText) {
        Review existingReview = reviewService.getReview(reviewId);

        if (reviewText != null) {
            existingReview.setReview(reviewText);
        }
        reviewService.updateReview(existingReview);
        RecentlyViewed recentlyViewed = recentlyViewedService.getRecentlyViewedByUserAndBook(existingReview.getUserId(), existingReview.getBookId());
        recentlyViewedService.updateRecentlyViewed(recentlyViewed, recentlyViewed.getUserId());

        return existingReview;
    }


    @RequestMapping(value = "reviews/updaterating/{reviewId}/rating{rating}", method = RequestMethod.POST)
    public Review updateRating(@PathVariable int reviewId, int rating) {
        Review existingReview = reviewService.getReview(reviewId);

        if (rating > -1 && rating < 11) {
            existingReview.setRating(rating);
        }
        reviewService.updateReview(existingReview);

        RecentlyViewed recentlyViewed = recentlyViewedService.getRecentlyViewedByUserAndBook(existingReview.getUserId(), existingReview.getBookId());
        recentlyViewedService.updateRecentlyViewed(recentlyViewed, recentlyViewed.getUserId());

        return existingReview;
    }


    @RequestMapping(value = "reviews/deleterating{reviewId}", method = RequestMethod.POST)
    public Review deleteRating(@PathVariable int reviewId) {
        Review existingReview = reviewService.getReview(reviewId);
        reviewService.deleteReview(reviewId);
        return existingReview;
    }

    @RequestMapping(value = "reviews/deletereview{reviewId}", method = RequestMethod.POST)
    public Review deleteReview(@PathVariable int reviewId) {
        Review existingReview = reviewService.getReview(reviewId);
        reviewService.deleteReview(reviewId);
        return existingReview;
    }

    @RequestMapping(value = "reviews/allreviews/{bookId}", method = RequestMethod.GET)
    public List<Review> getAllReviewsOfABook(@PathVariable int bookId) {
        Book book = bookService.getBook(bookId);
        List<Review> reviews = reviewService.getReviewsByBook(book.getBookId());
        return reviews;
    }

    @RequestMapping(value = "reviews/getrating/{bookurl}", method = RequestMethod.GET)
    public Review getRatingOfABook(@PathVariable String bookurl) {
        Book book = bookService.getBook(bookService.getBookByUrl(bookurl).getBookId());
        List<Review> reviews = reviewService.getReviewsByBook(book.getBookId());
        int sum = 0;
        for (Review rev : reviews) {
            sum = sum + rev.getRating();
        }

        Review review = new Review();
        review.setRating(sum / reviews.size());
        return review ;
    }

}