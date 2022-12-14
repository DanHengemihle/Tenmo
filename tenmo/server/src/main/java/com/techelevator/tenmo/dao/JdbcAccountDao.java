package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
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

        String sql = "SELECT balance FROM account WHERE account_id = ?;";
        Account account = new Account();
        BigDecimal balance = new BigDecimal(0);
       try {
           balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, id);
       } catch (DataAccessException e) {
           e.printStackTrace();
       }

        return balance;

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



    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }




}
