package com.example.controller.model.mappings;

import com.example.model.Review;
import com.example.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = "reviews", method = RequestMethod.GET)
    public List<Review> list() {
        return reviewService.getReviews();
    }

    @RequestMapping(value = "reviews", method = RequestMethod.POST)
    public Review create(@RequestBody Review review) {
        reviewService.addReview(review);
        return review;
    }

    @RequestMapping(value = "reviews/{id}", method = RequestMethod.GET)
    public Review get(@PathVariable int id) {
        return reviewService.getReview(id);
    }

    @RequestMapping(value = "reviews/{id}", method = RequestMethod.PUT)
    public Review update(@PathVariable int id, @RequestBody Review review) {
        Review existingReview = reviewService.getReview(id);

        if(review.getRating() <= 11 && review.getRating()>=0){
            existingReview.setRating(review.getRating());
        }
        if(review.getBookId() != 0){
            existingReview.setBookId(review.getBookId());
        }
        if(review.getUserId() != 0){
            existingReview.setUserId(review.getUserId());
        }
        if(review.getReview() != null){
            existingReview.setReview(review.getReview());
        }

        reviewService.updateReview(existingReview);
        return review;
    }

    @RequestMapping(value = "reviews/{id}", method = RequestMethod.DELETE)
    public Review delete(@PathVariable int id) {
        Review existingReview = reviewService.getReview(id);
        reviewService.deleteReview(id);
        return existingReview;
    }



}