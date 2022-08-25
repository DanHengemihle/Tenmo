package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
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
    public BigDecimal getBalanceById(@Valid @RequestParam int id){

        return accountDao.getBalanceById(id);
    }

@RequestMapping(path = "/account/transfer/approval", method = RequestMethod.PUT )
    public String transfer(@Valid @RequestParam int fromId, @RequestParam int toId, @RequestParam BigDecimal transferAmount, @RequestParam String status){

    //(DO WE NEED SEPARATE STRING STATUS PARAMETER)?

    //IF STATEMENTS DEPENDING ON STATUS

    //if(status.equalsIgnoreCase("Approved")){
    //transfer.setStatus("Approved")
    //transferDao.transfer()


        if(!accountDao.transfer(fromId, toId, transferAmount)){
            return "Transfer Denied";
        }
        return "Transfer Approved";
}




//HASH ID'S
// CREATETRANSACTION METHOD IN JDBC?











}
