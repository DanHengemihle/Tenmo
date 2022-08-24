package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private AccountDao accountDao;
    //possible userDao?

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }



}
