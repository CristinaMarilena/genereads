package com.example.controller;

import com.example.model.ToRead;
import com.example.service.AccountService;
import com.example.service.SecurityService;
import com.example.service.ToReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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