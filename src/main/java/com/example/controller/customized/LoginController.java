package com.example.controller.customized;

import com.example.model.Account;
import com.example.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login/v1")
public class LoginController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST)
    public Account loginUser(@RequestBody Account account){
        securityService.autologin(account.getEmail(), account.getPassword());
        return account;
    }
}
