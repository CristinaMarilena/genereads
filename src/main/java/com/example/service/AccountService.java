package com.example.service;

import java.util.List;

import com.example.model.Account;

public interface AccountService {

    public void addAccount(Account Account);

    public void updateAccount(Account Account);

    public Account getAccount(int id);

    public void deleteAccount(int id);

    public List<Account> getAccounts();

    public Account findByEmail(String email);

}