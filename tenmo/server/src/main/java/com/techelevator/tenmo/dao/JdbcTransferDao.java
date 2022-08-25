package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements  TransferDao{

    private JdbcTemplate jdbcTemplate;
    public JdbcTransferDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}


    @Override
    public BigDecimal getBalanceByAccountId(int id) {

        String sql = "SELECT balance FROM account WHERE account_id = ?;";

        return jdbcTemplate.queryForObject(sql, BigDecimal.class, id);
    }



    @Override
    public List<Transfer> listAllTransfersByAccountId(int accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE from_account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        while(results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = new Transfer();
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if(results.next()) {
             transfer = mapRowToTransfer(results);

        }
        return transfer;
    }

    @Override
    public Transfer createTransfer(int fromAccountId, int toAccountId, BigDecimal transferAmount) {

        Transfer transfer = new Transfer();
        transfer.setFromAccountId(fromAccountId);
        transfer.setToAccountId(toAccountId);
        transfer.setAmount(transferAmount);
        String sql = "INSERT INTO transfer (status, amount, to_account_id, from_account_id) VALUES (?, ?, ?, ?) RETURNING transfer_id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getStatus(), transferAmount, toAccountId, fromAccountId);

        transfer.setId(newId);
       return transfer;
    }


    @Override
    public boolean transferApproval(Transfer transfer) {

        if (transfer.getToAccountId() == transfer.getFromAccountId()) {
            return false;
        } if (transfer.getAmount().compareTo(BigDecimal.ZERO) < 0 || transfer.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            return false;
        } if(transfer.getAmount().compareTo(getBalanceByAccountId(transfer.getFromAccountId())) == 1) {
            return false;
        }
        String sql = "BEGIN TRANSACTION; UPDATE account SET balance = balance + ?  WHERE account_id = ?; UPDATE account SET balance = balance - ? WHERE account_id = ?; UPDATE transfer SET status = 'Approved' WHERE transfer_id = ?; COMMIT;";

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transfer.getAmount(), transfer.getToAccountId(), transfer.getAmount(), transfer.getFromAccountId(), transfer.getId());

        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }


    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setId(rowSet.getInt("transfer_id"));
        transfer.setStatus(rowSet.getString("status"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        transfer.setToAccountId(rowSet.getInt("to_account_id"));
        transfer.setFromAccountId(rowSet.getInt("from_account_id"));

        return transfer;
    }
}
