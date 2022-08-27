package com.techelevator.dao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class JdbcAccountDaoTests extends BaseDaoTests {

    private JdbcAccountDao sut;
    private static final Account testAccount =  new Account(4001, 5001, new BigDecimal(1000.00));
    private static final Account testAccount2 =  new Account(4002, 5002, new BigDecimal(750.00));

    @Before
    public void setup(){
        sut = new JdbcAccountDao(new JdbcTemplate(dataSource));

    }

    @Test
    public void getBalanceById_returns_correct_balance(){
        BigDecimal balance = new BigDecimal(0);
        balance = sut.getBalanceById(testAccount.getAccountId());
        Assert.assertEquals(balance, sut.getBalanceById(4001));
        BigDecimal balance2;
        balance2 = sut.getBalanceById(testAccount2.getAccountId());
        Assert.assertEquals(balance2, sut.getBalanceById(4002));
    }

    @Test
    public void listAccountsForTransfer_returns_lists(){
        Assert.assertEquals(2,sut.listAccountsForTransfer().size());


    }


}
