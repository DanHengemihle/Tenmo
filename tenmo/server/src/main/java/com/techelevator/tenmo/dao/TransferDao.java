package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface TransferDao {


   List<Transfer> listAllTransfersByAccountId(int accountId);

   List<Transfer> listAllPendingTransfers(int accountId);

   Transfer getTransferById(int transferId);

   Transfer createTransfer(int fromAccountId, int toAccountId, BigDecimal transferAmount);

   BigDecimal getBalanceByAccountId(int id);

   boolean transferApproval(Transfer transfer);

   void transferDenial(Transfer transfer);

   int getAccountIdFromPrincipal(String username);


}
