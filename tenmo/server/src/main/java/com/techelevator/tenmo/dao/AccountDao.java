package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountDao {
/*






transfer denial still needs work
and one for just pending transfers (list of transfers)
test listalltransfers method in transfer controller
cli


  */



   BigDecimal getBalanceById(int id);

   List<Account> listAccountsForTransfer();




}
