package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "userid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public Account(){

    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}