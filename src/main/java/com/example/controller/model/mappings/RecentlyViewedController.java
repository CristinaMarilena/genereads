package com.example.controller.model.mappings;

import com.example.model.RecentlyViewed;
import com.example.service.AccountService;
import com.example.service.SecurityService;
import com.example.service.RecentlyViewedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class RecentlyViewedController {

    @Autowired
    private RecentlyViewedService recentlyViewedService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "recentlyviewed", method = RequestMethod.GET)
    public List<RecentlyViewed> list() {
        return recentlyViewedService.getRecentlyViewed();
    }

    @RequestMapping(value = "recentlyviewed", method = RequestMethod.POST)
    public RecentlyViewed create(@RequestBody RecentlyViewed recentlyViewed) {
        recentlyViewedService.addRecentlyViewed(recentlyViewed, accountService.findByEmail(securityService.findLoggedInUsername()).getUserId());
        return recentlyViewed;
    }

    @RequestMapping(value = "recentlyviewed/{id}", method = RequestMethod.GET)
    public RecentlyViewed get(@PathVariable int id) {
        return recentlyViewedService.getRecentlyViewed(id);
    }

    @RequestMapping(value = "recentlyviewed/{id}", method = RequestMethod.PUT)
    public RecentlyViewed update(@PathVariable int id, @RequestBody RecentlyViewed recentlyViewed) {
        RecentlyViewed existingRecentlyViewed = recentlyViewedService.getRecentlyViewed(id);
        if(recentlyViewed.getBookId() != 0){
            existingRecentlyViewed.setBookId(recentlyViewed.getBookId());
        }
        if(recentlyViewed.getUserId() != 0){
            existingRecentlyViewed.setUserId(recentlyViewed.getUserId());
        }

        recentlyViewedService.updateRecentlyViewed(existingRecentlyViewed, accountService.findByEmail(securityService.findLoggedInUsername()).getUserId());
        return recentlyViewed;
    }

    @RequestMapping(value = "recentlyviewed/{id}", method = RequestMethod.DELETE)
    public RecentlyViewed delete(@PathVariable int id) {
        RecentlyViewed existingRecentlyViewed = recentlyViewedService.getRecentlyViewed(id);
        recentlyViewedService.deleteRecentlyViewed(id);
        return existingRecentlyViewed;
    }

    @RequestMapping(value = "recentlyviewed/byuser/{userId}", method = RequestMethod.GET)
    public List<RecentlyViewed> getRecentlyViewedByUser(@PathVariable int userId){
        List<RecentlyViewed> recentlyViewedList = recentlyViewedService.getRecentlyViewedByUser(userId);
        return recentlyViewedList;
    }

    @RequestMapping(value = "recentlyviewed/own", method = RequestMethod.GET)
    public List<RecentlyViewed> getRecentlyViewedByCurrentUser(){
        List<RecentlyViewed> recentlyViewedList = recentlyViewedService.getRecentlyViewedByUser(accountService.findByEmail(securityService.findLoggedInUsername()).getUserId());
        return recentlyViewedList;
    }
}