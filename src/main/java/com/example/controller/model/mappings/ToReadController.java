package com.example.controller.model.mappings;

import com.example.model.ToRead;
import com.example.service.AccountService;
import com.example.service.BookService;
import com.example.service.SecurityService;
import com.example.service.ToReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class ToReadController {

    @Autowired
    private ToReadService toReadService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BookService bookService;


    @RequestMapping(value = "toread/byurl/{bookurl}", method = RequestMethod.POST)
    public ToRead addToRead(@PathVariable String bookurl) {
        int userId = accountService.findByEmail(securityService.findLoggedInUsername()).getUserId();
        int bookId = bookService.getBookByUrl(bookurl).getBookId();
        ToRead toReadToBeFound = toReadService.getToReadByUserAndBook(userId, bookId);
        if( toReadToBeFound == null) {

            ToRead toRead = new ToRead();
            toRead.setBookId(bookId);
            toRead.setUserId(userId);

            toReadService.addToRead(toRead);
            return toRead;
        }
        else {
            toReadService.deleteToRead(toReadToBeFound.getToReadId());
            return null;
        }
    }

    @RequestMapping(value = "toread/byurl/{bookurl}", method = RequestMethod.GET)
    public ToRead getToRead(@PathVariable String bookurl) {
        int userId = accountService.findByEmail(securityService.findLoggedInUsername()).getUserId();
        int bookId = bookService.getBookByUrl(bookurl).getBookId();
        ToRead toReadToBeFound = toReadService.getToReadByUserAndBook(userId, bookId);
        return (toReadToBeFound != null) ? toReadToBeFound : null;
    }

    @RequestMapping(value = "toread", method = RequestMethod.GET)
    public List<ToRead> list() {
        return toReadService.getToRead();
    }

    @RequestMapping(value = "toread", method = RequestMethod.POST)
    public ToRead create(@RequestBody ToRead toRead) {
        toReadService.addToRead(toRead);
        return toRead;
    }

    @RequestMapping(value = "toread/{id}", method = RequestMethod.GET)
    public ToRead get(@PathVariable int id) {
        return toReadService.getToRead(id);
    }

    @RequestMapping(value = "toread/{id}", method = RequestMethod.PUT)
    public ToRead update(@PathVariable int id, @RequestBody ToRead toRead) {
        ToRead existingToRead = toReadService.getToRead(id);
        if(toRead.getBookId() != 0){
            existingToRead.setBookId(toRead.getBookId());
        }
        if(toRead.getUserId() != 0){
            existingToRead.setUserId(toRead.getUserId());
        }

        toReadService.updateToRead(existingToRead);
        return toRead;
    }

    @RequestMapping(value = "toread/{id}", method = RequestMethod.DELETE)
    public ToRead delete(@PathVariable int id) {
        ToRead existingToRead = toReadService.getToRead(id);
        toReadService.deleteToRead(id);
        return existingToRead;
    }

    @RequestMapping(value = "toread/{userId}", method = RequestMethod.GET)
    public List<ToRead> getToReadByUser(@PathVariable int userId){
        List<ToRead> toReadList = toReadService.getToReadByUser(userId);
        return toReadList;
    }

    @RequestMapping(value = "toread/own", method = RequestMethod.GET)
    public List<ToRead> getToReadByCurrentUser(){
        List<ToRead> toReadList = toReadService.getToReadByUser(accountService.findByEmail(securityService.findLoggedInUsername()).getUserId());
        return toReadList;
    }
}