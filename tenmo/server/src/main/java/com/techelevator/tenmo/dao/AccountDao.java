package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountDao {



   BigDecimal getBalanceById(int id);

   List<Account> listAccountsForTransfer();


//INTEGRATION TESTING

}
