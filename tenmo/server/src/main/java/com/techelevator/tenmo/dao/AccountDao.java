package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountDao {
/*


fix method throwing error if it still is
check transfer methods in postman
fix and move transfer method from jdbcaccount and controller


add transfer list method to see all transfers for account, and one for just pending transfers
cli


  */



   BigDecimal getBalanceById(int id);

   List<Account> listAccountsForTransfer();

   boolean transfer(int fromAccountId, int toAccountId, BigDecimal transferAmount);


}
