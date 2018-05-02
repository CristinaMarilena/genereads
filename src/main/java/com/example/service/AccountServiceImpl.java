package com.example.service;

import com.example.model.Account;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.example.dao.AccountDAO;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountDAO.addAccount(account);
    }

    public void updateAccount(Account Account) {
        accountDAO.updateAccount(Account);
    }

    public Account getAccount(int id) {
        Account account = accountDAO.getAccount(id);
        return account;
    }

    public void deleteAccount(int id) {
        accountDAO.deleteAccount(id);
    }

    public List<Account> getAccounts() {
        return accountDAO.getAccounts();
    }

    public Account findByEmail(String email) {
        return accountDAO.findByEmail(email);
    }
}