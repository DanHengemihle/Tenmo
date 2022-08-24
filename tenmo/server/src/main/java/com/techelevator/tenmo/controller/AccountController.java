package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
//@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;
    //possible userDao?

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @RequestMapping(path = "/account/", method = RequestMethod.GET)
    public BigDecimal getBalanceById(@Valid @RequestParam int id){

        return accountDao.getBalanceById(id);
    }

@RequestMapping(path = "/account/transfer", method = RequestMethod.PUT )
    public boolean transfer(@Valid @RequestParam int fromId, @RequestParam int toId, @RequestParam BigDecimal transferAmount){
        if(!accountDao.transfer(fromId, toId, transferAmount)){
            return false;
        }
        return true;
}
//HASH ID'S
// CREATETRANSACTION METHOD IN JDBC?










}
