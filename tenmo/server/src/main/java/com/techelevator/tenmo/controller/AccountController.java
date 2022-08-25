package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
//@RequestMapping(path = "/account")
public class AccountController {

    private AccountDao accountDao;


    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @RequestMapping(path = "/accounts/", method = RequestMethod.GET)
    public List<Account> listAccountsForTransfer(){

        return accountDao.listAccountsForTransfer();
    }

    @RequestMapping(path = "/account/", method = RequestMethod.GET)
    public BigDecimal getBalanceById(@Valid @RequestBody AccountDTO accountDTO){

        return accountDao.getBalanceById(accountDTO.getAccountId());
    }






//HASH ID'S
// CREATETRANSACTION METHOD IN JDBC?











}
