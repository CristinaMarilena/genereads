package com.example.controller.customized;

import com.example.model.Book;
import com.example.model.RecentlyViewed;
import com.example.model.Review;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/v1/")
public class UserReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RecentlyViewedService recentlyViewedService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "reviews/addrating/{rating}/bookurl/{bookurl}", method = RequestMethod.POST)
    public Review addRating(@PathVariable int rating, @PathVariable String bookurl) {
        int userId = accountService.findByEmail(securityService.findLoggedInUsername()).getUserId();
        int bookId = bookService.getBookByUrl(bookurl).getBookId();
        Review reviewToBeFound = reviewService.getReviewByBookAndUser(userId, bookId);
        if (reviewToBeFound == null) {
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
        } else {
            reviewToBeFound.setRating(rating * 2);
            reviewService.updateReview(reviewToBeFound);
            return reviewToBeFound;
        }
    }

    @RequestMapping(value = "reviews/getrating/bookurl/{bookurl}", method = RequestMethod.GET)
    public Review getRating(@PathVariable String bookurl) {
        int userId = accountService.findByEmail(securityService.findLoggedInUsername()).getUserId();
        int bookId = bookService.getBookByUrl(bookurl).getBookId();
        return reviewService.getReviewByBookAndUser(userId, bookId);
    }


    @RequestMapping(value = "reviews/addreview/bookurl/{bookurl}", method = RequestMethod.POST)
    public Review addReview(@RequestBody String reviewText, @PathVariable String bookurl) {
        int userId = accountService.findByEmail(securityService.findLoggedInUsername()).getUserId();
        int bookId = bookService.getBookByUrl(bookurl).getBookId();
        Review review = new Review();

        review.setUserId(userId);
        review.setReview(reviewText);

        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH).format(ldt);
        review.setTimestamp(DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH).format(ldt));

        review.setBookId(bookId);
        reviewService.addReview(review);

        RecentlyViewed recentlyViewed = new RecentlyViewed();
        recentlyViewed.setBookId(bookId);
        recentlyViewed.setUserId(userId);
        recentlyViewedService.addRecentlyViewed(recentlyViewed, userId);

        return review;
    }

    @RequestMapping(value = "reviews/allreviews/{bookurl}", method = RequestMethod.GET)
    public List<Review> getAllReviewsOfABook(@PathVariable String bookurl) {
        Book book = bookService.getBook(bookService.getBookByUrl(bookurl).getBookId());
        List<Review> reviews = reviewService.getReviewsByBook(book.getBookId());
        if (reviews != null) {
            List<Review> reviewList = new LinkedList<>();

            for (Review rev : reviews) {
                if (rev.getReview() != null)
                    reviewList.add(rev);
            }
            return reviewList;
        } else return null;
    }

    @RequestMapping(value = "reviews/getrating/{bookurl}", method = RequestMethod.GET)
    public Review getRatingOfABook(@PathVariable String bookurl) {
        Book book = bookService.getBook(bookService.getBookByUrl(bookurl).getBookId());
        List<Review> reviews = reviewService.getReviewsByBook(book.getBookId());
        if (reviews != null) {
            List<Review> reviewList = new LinkedList<>();
            int sum = 0;
            for (Review rev : reviews) {
                if (rev.getRating() > 0) {
                    sum = sum + rev.getRating();
                    reviewList.add(rev);
                }
            }

            Review review = new Review();
            review.setRating(sum / reviewList.size());
            return review;
        } else return null;
    }

//

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
}