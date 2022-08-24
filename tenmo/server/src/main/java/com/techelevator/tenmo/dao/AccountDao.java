package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

public interface AccountDao {
/*
    As a user, when I register a new account is created for me.

The new account has an initial balance of $1000.
Account ids start at 2001.


As an authenticated user of the system, I need to be able to see my Account Balance.
As an authenticated user of the system, I need to be able to send a transfer of a specific amount of TE Bucks to a registered user.

I should be able to choose from a list of users to send TE Bucks to.
I must not be allowed to send money to myself.
A transfer includes the User IDs of the from and to users and the amount of TE Bucks.
The receiver's account balance is increased by the amount of the transfer.
The sender's account balance is decreased by the amount of the transfer.
I can't send more TE Bucks than I have in my account.
I can't send a zero or negative amount.
A Sending Transfer has an initial status of Approved.
Transfer ids start at 3001.


As an authenticated user of the system, I need to be able to see transfers I have sent or received.
As an authenticated user of the system, I need to be able to retrieve the details of any transfer based upon the transfer ID.
  */

    //userId?



   void transfer(int fromAccountId, int toAccountId, int transferAmount);
}
