package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {


   List<Transfer> listAllTransfersByAccountId(int accountId);

   Transfer getTransferById(int transferId);

   Transfer createTransfer(int fromAccountId, int toAccountId, BigDecimal transferAmount);

   BigDecimal getBalanceByAccountId(int id);

  // boolean transferApproval(int transferId)




}
