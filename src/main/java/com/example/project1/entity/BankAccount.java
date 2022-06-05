package com.example.project1.entity;

import javax.persistence.*;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private AppUser appUser;

    private Long accNumber;

    private double balance;

    private int transferLimit;

    private boolean isTransferLimitEnabled;

    public BankAccount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Long getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(Long accNumber) {
        this.accNumber = accNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(int transferLimit) {
        this.transferLimit = transferLimit;
    }

    public boolean isTransferLimitEnabled() {
        return isTransferLimitEnabled;
    }

    public void setTransferLimitEnabled(boolean transferLimitEnabled) {
        isTransferLimitEnabled = transferLimitEnabled;
    }
}
