package com.example.controller;

import com.example.model.CurrentlyReading;
import com.example.service.AccountService;
import com.example.service.SecurityService;
import com.example.service.CurrentlyReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class CurrentlyReadingController {

    @Autowired
    private CurrentlyReadingService currentlyReadingService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountService accountService;

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
    public List<CurrentlyReading> getCurrentlyReadingByCurrentUser(){
        List<CurrentlyReading> currentlyReadingList = currentlyReadingService.getCurrentlyReadingByUser(accountService.findByEmail(securityService.findLoggedInUsername()).getUserId());
        return currentlyReadingList;
    }
}