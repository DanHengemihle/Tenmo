package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao){this.transferDao = transferDao;}

    @RequestMapping(path = "/account/{id}/transfer", method = RequestMethod.GET)
    public List<Transfer>  listAllTransfersByAccountId(@Valid @PathVariable int accountId){
        return transferDao.listAllTransfersByAccountId(accountId);
    }

    @RequestMapping(path = "/account/transfer/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@Valid @PathVariable int transferId){
        return transferDao.getTransferById(transferId);
    }

}
