package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.AccountDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao){this.transferDao = transferDao;}

    @RequestMapping(path = "/account/transfers", method = RequestMethod.GET)
    public List<Transfer>  listAllTransfersByAccountId(@Valid @RequestBody AccountDTO accountId){
        return transferDao.listAllTransfersByAccountId(accountId.getAccountId());
    }

    @RequestMapping(path = "/account/transfers/pending", method = RequestMethod.GET)
    public List<Transfer>  listAllPendingTransfers(@Valid @RequestBody AccountDTO accountId){
        return transferDao.listAllPendingTransfers(accountId.getAccountId());
    }

    @RequestMapping(path = "/account/transfer", method = RequestMethod.GET)
    public Transfer getTransferById(@Valid @RequestBody TransferDTO transferId){

        String idHash = String.valueOf(String.valueOf(transferId).hashCode());

        return transferDao.getTransferById(transferId.getTransferId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/account/transfer", method = RequestMethod.POST)
    public String createTransfer(@Valid @RequestBody Transfer transfer) {


        if (transfer.getToAccountId() == transfer.getFromAccountId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create transfer.");
        } if (transfer.getAmount().compareTo(BigDecimal.ZERO) < 0 || transfer.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            return "Unable to create transfer.";
        } if(transfer.getAmount().compareTo(transferDao.getBalanceByAccountId(transfer.getFromAccountId())) == 1) {
            return "Unable to create transfer.";
        }
        try {
            transferDao.createTransfer(transfer.getFromAccountId(), transfer.getToAccountId(), transfer.getAmount());

        } catch (Exception e) {
            return "Unable to create transfer.";
        }
        return "Transfer record created - pending approval";
    }


    @RequestMapping(path = "/account/transfer", method = RequestMethod.PUT )
    public String transferApproval(@Valid @RequestBody TransferDTO transferId, @RequestParam String status, Principal principal) {
        int id = transferId.getTransferId();
        Transfer transfer =  transferDao.getTransferById(id);


        if (status.equalsIgnoreCase("Approve") && transfer.getToAccountId() == transferDao.getAccountIdFromPrincipal(principal.getName())) {

            transferDao.transferApproval(transfer);
            return "Transfer Approved";
        }

        else if(status.equalsIgnoreCase("Deny")){
          transferDao.transferDenial(transfer);
            return "Transfer Denied";
        } else if(transfer.getToAccountId() != transferDao.getAccountIdFromPrincipal(principal.getName())){
           return "Unauthorized";
        }
        else {
            return "Transfer Failed";
        }
    }

}
