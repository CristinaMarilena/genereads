package com.example.controller.customized;

import com.example.utils.CSVUtils;
import com.example.ml.RecomandationSystem;
import com.example.model.Book;
import com.example.model.Review;
import com.example.service.AccountService;
import com.example.service.BookService;
import com.example.service.ReviewService;
import com.example.service.SecurityService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class RecommendationController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookService bookService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "rec", method = RequestMethod.GET)
    public List<Book> getBooksRec() throws IOException, TasteException {
        List<Book> bookList = new LinkedList<>();
        writeRatingsToCSV();
        List<Long> recommendedBooks = RecomandationSystem.getRecommendationsForUser(
                accountService.findByEmail(securityService.findLoggedInUsername()).getUserId());

        for(Long id : recommendedBooks){
            Book book = bookService.getBook(Integer.valueOf(Long.toString(id)));
            bookList.add(book);
        }
        return bookList;
    }

    private void writeRatingsToCSV() {
        List<Review> reviews = reviewService.getReviews();
        String csvFile = "user_book.csv";
        try {
            FileWriter writer = new FileWriter(csvFile);
            for (Review review : reviews) {
                CSVUtils.writeLine(writer,
                        Arrays.asList(String.valueOf(review.getUserId()),
                                String.valueOf(review.getBookId()),
                                String.valueOf(review.getRating())));}
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}