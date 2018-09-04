package com.example.controller.model.mappings;

import com.example.model.Book;
import com.example.model.CurrentlyReading;
import com.example.service.AccountService;
import com.example.service.BookService;
import com.example.service.CurrentlyReadingService;
import com.example.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class CurrentlyReadingController {

    @Autowired
    private CurrentlyReadingService currentlyReadingService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BookService bookService;

    @Autowired
    private SecurityService securityService;


    @RequestMapping(value = "reading/byurl/{bookurl}", method = RequestMethod.POST)
    public CurrentlyReading addToCurrentlyReading(@PathVariable String bookurl) {
        int userId = accountService.findByEmail(securityService.findLoggedInUsername()).getUserId();
        int bookId = bookService.getBookByUrl(bookurl).getBookId();
        CurrentlyReading currentlyReadingToBeFound = currentlyReadingService.getCurrentlyReadingByUserAndBook(userId, bookId);
        if( currentlyReadingToBeFound == null) {

            CurrentlyReading currentlyReading = new CurrentlyReading();
            currentlyReading.setBookId(bookId);
            currentlyReading.setUserId(userId);

            currentlyReadingService.addCurrentlyReading(currentlyReading);
            return currentlyReading;
        }
        else {
            currentlyReadingService.deleteCurrentlyReading(currentlyReadingToBeFound.getCurrentlyReadingId());
            return null;
        }
    }

    @RequestMapping(value = "reading/byurl/{bookurl}", method = RequestMethod.GET)
    public CurrentlyReading getCurrentlyReading(@PathVariable String bookurl) {
        int userId = accountService.findByEmail(securityService.findLoggedInUsername()).getUserId();
        int bookId = bookService.getBookByUrl(bookurl).getBookId();
        CurrentlyReading readingToBeFound = currentlyReadingService.getCurrentlyReadingByUserAndBook(userId, bookId);
        return (readingToBeFound != null) ? readingToBeFound : null;
    }

    @RequestMapping(value = "currentlyreading", method = RequestMethod.GET)
    public List<CurrentlyReading> list() {
        return currentlyReadingService.getCurrentlyReading();
    }

    @RequestMapping(value = "currentlyreading", method = RequestMethod.POST)
    public CurrentlyReading create(@RequestBody CurrentlyReading currentlyReading) {
        currentlyReadingService.addCurrentlyReading(currentlyReading);
        return currentlyReading;
    }

    @RequestMapping(value = "currentlyreading/{id}", method = RequestMethod.GET)
    public CurrentlyReading get(@PathVariable int id) {
        return currentlyReadingService.getCurrentlyReading(id);
    }

    @RequestMapping(value = "currentlyreading/{id}", method = RequestMethod.PUT)
    public CurrentlyReading update(@PathVariable int id, @RequestBody CurrentlyReading currentlyReading) {
        CurrentlyReading existingCurrentlyReading = currentlyReadingService.getCurrentlyReading(id);
        if(currentlyReading.getBookId() != 0){
            existingCurrentlyReading.setBookId(currentlyReading.getBookId());
        }
        if(currentlyReading.getUserId() != 0){
            existingCurrentlyReading.setUserId(currentlyReading.getUserId());
        }

        currentlyReadingService.updateCurrentlyReading(existingCurrentlyReading);
        return currentlyReading;
    }

    @RequestMapping(value = "currentlyreading/{id}", method = RequestMethod.DELETE)
    public CurrentlyReading delete(@PathVariable int id) {
        CurrentlyReading existingCurrentlyReading = currentlyReadingService.getCurrentlyReading(id);
        currentlyReadingService.deleteCurrentlyReading(id);
        return existingCurrentlyReading;
    }

    @RequestMapping(value = "currentlyreading/{userId}", method = RequestMethod.GET)
    public List<CurrentlyReading> getCurrentlyReadingByUser(@PathVariable int userId){
        List<CurrentlyReading> currentlyReadingList = currentlyReadingService.getCurrentlyReadingByUser(userId);
        return currentlyReadingList;
    }

    @RequestMapping(value = "currentlyreading/own", method = RequestMethod.GET)
    public List<Book> getCurrentlyReadingByCurrentUser(){
        List<CurrentlyReading> currentlyReadingList = currentlyReadingService.getCurrentlyReadingByUser(
                accountService.findByEmail(securityService.findLoggedInUsername()).getUserId());
        List<Book> bookList = new LinkedList<>();
        for(CurrentlyReading currentlyReading:currentlyReadingList){
            bookList.add(bookService.getBook(currentlyReading.getBookId()));
        }
        return bookList;
    }
}