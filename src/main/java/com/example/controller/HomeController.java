package com.example.controller;

import com.example.examples.AddAccountExample;
import com.example.model.Account;
import com.example.service.AccountServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

@Controller
public class HomeController {

    @Autowired
    AddAccountExample addAccountExample;

    @Autowired
    SessionFactory sessionFactory;

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    @RequestMapping("/")
    public String home() {
        return "index.htm";
    }

    @RequestMapping("/addaccount")
    public String addAccountPlease(){
        addAccountExample.addAccount();
        return "succes";
    }

  /*  @RequestMapping(value = "/addaccount", method = RequestMethod.POST)
    public ModelAndView addAccountEx(@ModelAttribute Account account) {
        ModelAndView modelAndView = new ModelAndView("home");
        accountService.addAccount(account);

        modelAndView.addObject("mesage", "account added");

        return modelAndView;
    }*/
}
