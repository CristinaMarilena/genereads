package com.example.controller;

import com.example.model.Account;
import com.example.service.AccountService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public List<Account> list() {
        return accountService.getAccounts();
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Account create(@RequestBody Account account) {
        System.out.println("please");
        accountService.addAccount(account);
        return account;
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public Account get(@PathVariable int id) {
        return accountService.getAccount(id);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    public Account update(@PathVariable int id, @RequestBody Account account) {
        Account existingAccount = accountService.getAccount(id);
        BeanUtils.copyProperties(account, existingAccount);
        accountService.updateAccount(existingAccount);
        return account;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public Account delete(@PathVariable int id) {
        Account existingAccount = accountService.getAccount(id);
        accountService.deleteAccount(id);
        return existingAccount;
    }

}