package com.example.dao;

import com.example.model.Account;

import java.util.List;

public interface AccountDAO {

    public void addAccount(Account account);

    public void updateAccount(Account account);

    public Account getAccount(int id);

    public void deleteAccount(int id);

    public List<Account> getAccounts();

    public Account findByEmail(String email);

}