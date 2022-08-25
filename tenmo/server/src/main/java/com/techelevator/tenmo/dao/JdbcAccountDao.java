package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}



    @Override
    public BigDecimal getBalanceById(int id) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()) {
            account = mapRowToAccount(rowSet);
        }
            return account.getBalance();
        }

    @Override
    public List<Account> listAccountsForTransfer() {
        List<Account> accounts = new ArrayList<>();

        SqlRowSet results = jdbcTemplate.queryForRowSet("SELECT * FROM account;");
        while (results.next()){
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }


    @Override
    public boolean transfer(int fromAccountId, int toAccountId, BigDecimal transferAmount) {

        //MOVE TO TRANSFER
        //TRANSFER OBJECT IN PARAMETERS

        if (fromAccountId == toAccountId) {
                return false;
        } if (transferAmount.compareTo(BigDecimal.ZERO) < 0 || transferAmount.compareTo(BigDecimal.ZERO) == 0) {
                return false;
        } if(transferAmount.compareTo(getBalanceById(fromAccountId)) < 0 || transferAmount.compareTo(getBalanceById(fromAccountId)) == 0) {
                return false;
            }
          String sql = "BEGIN TRANSACTION; UPDATE account SET balance = balance + ?  WHERE account_id = ?; UPDATE account SET balance = balance - ? WHERE account_id = ?;";

                  try {
                      SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferAmount, toAccountId, transferAmount, fromAccountId);
                  } catch (DataAccessException e) {
                      return false;
                  }
        return true;
    }
    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }




}
