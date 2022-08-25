package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.AccountDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferIdDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
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

    @RequestMapping(path = "/account/transfer", method = RequestMethod.GET)
    public Transfer getTransferById(@Valid @RequestBody TransferIdDTO transferId){

        String idHash = String.valueOf(String.valueOf(transferId).hashCode());

        return transferDao.getTransferById(transferId.getTransferId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/account/transfer", method = RequestMethod.POST)
    public String createTransfer(@Valid @RequestBody Transfer transfer) {

        try {
            transferDao.createTransfer(transfer.getFromAccountId(), transfer.getToAccountId(), transfer.getAmount());

        } catch (Exception e) {
           e.printStackTrace();
            return "Unable to create transfer.";
        }
        return "Transfer record created - pending approval";
    }


    @RequestMapping(path = "/account/transfer", method = RequestMethod.PUT )
    public String transferApproval(@Valid @RequestBody Transfer transfer) {

        if (transfer.getStatus().equalsIgnoreCase("Approved")) {
            transferDao.transferApproval(transfer);

        }
        if (!transferDao.transferApproval(transfer)) {
            return "Transfer Denied";
        }
        return "Transfer Approved";
    }

}
