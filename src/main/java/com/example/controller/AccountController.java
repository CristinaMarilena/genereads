package com.example.controller;

import com.example.model.Account;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "accounts", method = RequestMethod.GET)
    public List<Account> list() {
        return accountService.getAccounts();
    }

    @RequestMapping(value = "accounts", method = RequestMethod.POST)
    public Account create(@RequestBody Account account) {
        accountService.addAccount(account);
        return account;
    }

    @RequestMapping(value = "accounts/{id}", method = RequestMethod.GET)
    public Account get(@PathVariable int id) {
        return accountService.getAccount(id);
    }

    @RequestMapping(value = "account/{email:.+}", method = RequestMethod.GET)
    public Account get(@PathVariable String email) {
        return accountService.findByEmail(email);
    }

    @RequestMapping(value = "accounts/{id}", method = RequestMethod.PUT)
    public Account update(@PathVariable int id, @RequestBody Account account) {
        Account existingAccount = accountService.getAccount(id);

        if(account.getEmail() != null){
            existingAccount.setEmail(account.getEmail());
        }
        if(account.getStatus() != null){
            existingAccount.setStatus(account.getStatus());
        }
        if(account.getPassword() != null){
            existingAccount.setPassword(account.getPassword());
        }
        if(account.getPhoto() != null){
            existingAccount.setPhoto(account.getPhoto());
        }

        accountService.updateAccount(existingAccount);
        return account;
    }

    @RequestMapping(value = "accounts/{id}", method = RequestMethod.DELETE)
    public Account delete(@PathVariable int id) {
        Account existingAccount = accountService.getAccount(id);
        accountService.deleteAccount(id);
        return existingAccount;
    }

}