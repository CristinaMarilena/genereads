package com.example.service;

import com.example.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.example.dao.AccountDAO;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDAO AccountDAO;

    public void addAccount(Account Account) {
        AccountDAO.addAccount(Account);
    }

    public void updateAccount(Account Account) {
        AccountDAO.updateAccount(Account);
    }

    public Account getAccount(int id) {
        return AccountDAO.getAccount(id);
    }

    public void deleteAccount(int id) {
        AccountDAO.deleteAccount(id);
    }

    public List<Account> getAccounts() {
        return AccountDAO.getAccounts();
    }

}