package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private String status = "Pending";
    private BigDecimal amount;
    private int toAccountId;
    private int fromAccountId;

    public Transfer(){ }

    public Transfer(int id, String status, BigDecimal amount, int toAccountId, int fromAccountId) {
        this.transferId = id;
        this.status = status;
        this.amount = amount;
        this.toAccountId = toAccountId;
        this.fromAccountId = fromAccountId;
    }

    public int getId() {
        return transferId;
    }

    public void setId(int id) {
        this.transferId = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }
}
