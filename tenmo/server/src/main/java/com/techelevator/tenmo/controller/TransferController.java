package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
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

    @RequestMapping(path = "/account/{accountId}/transfer", method = RequestMethod.GET)
    public List<Transfer>  listAllTransfersByAccountId(@Valid @PathVariable int accountId){
        return transferDao.listAllTransfersByAccountId(accountId);
    }

    @RequestMapping(path = "/account/transfer/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferById(@Valid @PathVariable int transferId){

        //String idHash = new BCryptPasswordEncoder().encode(Id);

        return transferDao.getTransferById(transferId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/account/transfer/request", method = RequestMethod.POST)
    public String createTransfer(@Valid @RequestParam int fromId, @RequestParam int toId, @RequestParam BigDecimal transferAmount) {

        //THROWING UNABLE TO SEND

        try {
            transferDao.createTransfer(fromId, toId, transferAmount);

        } catch (Exception e) {
           e.printStackTrace();
            return "Unable to send transfer.";
        }
        return "Transfer Successful.";
    }


}
