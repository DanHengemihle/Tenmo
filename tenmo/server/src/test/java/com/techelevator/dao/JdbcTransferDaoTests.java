package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcTransferDaoTests extends BaseDaoTests {

    private JdbcTransferDao sut;

    private static final Transfer testTransfer =  new Transfer(5001, "Pending", new BigDecimal("100.00"), 4002, 4001);
    private static final Transfer testTransfer2 =  new Transfer(5003, "Pending", new BigDecimal("200.00"), 4001, 4002);
    private static final Account testAccount =  new Account(4001, 5001, new BigDecimal("1000.00"));
    private static final Account testAccount2 =  new Account(4002, 5002, new BigDecimal("750.00"));

    private JdbcAccountDao accountDao;

    @Before
    public void setup(){
        sut = new JdbcTransferDao(new JdbcTemplate(dataSource));
        accountDao = new JdbcAccountDao(new JdbcTemplate(dataSource));
    }

    @Test
    public void getBalanceByAccountId_returns_correct_balance(){
        BigDecimal balance = new BigDecimal(0);
        balance = sut.getBalanceByAccountId(testAccount.getAccountId());
        Assert.assertEquals(balance, sut.getBalanceByAccountId(4001));
        BigDecimal balance2;
        balance2 = sut.getBalanceByAccountId(testAccount2.getAccountId());
        Assert.assertEquals(balance2, sut.getBalanceByAccountId(4002));
    }

    @Test
    public void listAllTransfersByAccountId_returns_list_successfully(){
        Assert.assertEquals(7, sut.listAllTransfersByAccountId(4001).size());
    }

    @Test
    public void listAllPendingTransfers_returns_only_pending_transfers(){
        Assert.assertEquals(4, sut.listAllPendingTransfers(4001).size());
    }

    @Test
    public void getTransferById_returns_correct_transfer(){
        Assert.assertEquals(testTransfer.getId(), sut.getTransferById(5001).getId());
        Assert.assertEquals(testTransfer.getAmount(), sut.getTransferById(5001).getAmount().setScale(2,0));
        Assert.assertEquals(testTransfer.getStatus(), sut.getTransferById(5001).getStatus());
        Assert.assertEquals(testTransfer.getFromAccountId(), sut.getTransferById(5001).getFromAccountId());
        Assert.assertEquals(testTransfer.getToAccountId(), sut.getTransferById(5001).getToAccountId());
    }

    @Test
    public void createTransfer_creates_new_transfer_in_database(){
       Transfer transfer = sut.createTransfer(4001, 4002, new BigDecimal("250.00" ));
       Assert.assertEquals(4001, transfer.getFromAccountId());
       Assert.assertEquals(4002, transfer.getToAccountId());
       Assert.assertEquals(new BigDecimal("250.00"), transfer.getAmount());
       Assert.assertEquals("Pending", transfer.getStatus());
       Assert.assertEquals(3001, transfer.getId());
    }

    @Test
    public void transferApproval_returns_false_when_ids_match() {
        //assert false when to_id matches from_id
        Transfer testTransfer = new Transfer(5005, "Pending", new BigDecimal("50.00"), 4002, 4002);
        Assert.assertFalse(sut.transferApproval(testTransfer));
    }

    @Test
        public void transferApproval_returns_false_when_amount_is_negative_or_zero() {
        //assert false with negative transfer amount
        Transfer negativeTestTransfer = new Transfer(5006, "Pending", new BigDecimal("-10.00"), 4002, 4001);
        Assert.assertFalse(sut.transferApproval(negativeTestTransfer));
        Transfer zeroTransfer = new Transfer(5007, "Pending", new BigDecimal("0.00"), 4001, 4002 );
        Assert.assertFalse(sut.transferApproval(zeroTransfer));
    }

    @Test
    public void transferApproval_changes_transfer_status(){
        sut.transferApproval(sut.getTransferById(5008));
       Assert.assertEquals("Approved",sut.getTransferById(5008).getStatus() );
    }

    @Test
    public void transferApproval_updates_balances(){
        sut.transferApproval(sut.getTransferById(5008));
        Assert.assertEquals(new BigDecimal("1025.00"),accountDao.getBalanceById(4001) );
        Assert.assertEquals(new BigDecimal("725.00"), accountDao.getBalanceById(4002));
    }

    @Test
    public void transferDenial_changes_status_to_denied(){
        sut.transferDenial(sut.getTransferById(5005));
        Assert.assertEquals("Denied",sut.getTransferById(5005).getStatus() );
    }
    @Test
    public void getAccountIdFromPrincipal_returns_username(){
        int bobId = sut.getAccountIdFromPrincipal("bob");
        Assert.assertEquals(4001, bobId);
    }

    }


