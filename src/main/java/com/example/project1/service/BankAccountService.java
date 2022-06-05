package com.example.project1.service;

import com.example.project1.entity.AppUser;
import com.example.project1.entity.BankAccount;
import com.example.project1.entity.TransferHistory;
import com.example.project1.repo.BankAccountRepo;
import com.example.project1.repo.TransferHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {
    private BankAccountRepo bankAccountRepo;
    private TransferHistoryRepo transferHistoryRepo;

    @Autowired
    public BankAccountService(BankAccountRepo bankAccountRepo, TransferHistoryRepo transferHistoryRepo) {
        this.bankAccountRepo = bankAccountRepo;
        this.transferHistoryRepo = transferHistoryRepo;
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void createBankAccout(AppUser user){
        BankAccount bankingAccount = new BankAccount();
        String number = Integer.toString(getRandomNumber(10,99)) + "12345678" + Integer.toString(getRandomNumber(1000000,9999999));
        bankingAccount.setAccNumber(Long.valueOf(number));
        bankingAccount.setBalance(15);
        bankingAccount.setAppUser(user);
        bankAccountRepo.save(bankingAccount);
    }

    public void setTransferLimit(BankAccount bankAccount, Long id, int limit){
        bankAccount = bankAccountRepo.findByAppUserId(id);
        bankAccount.setTransferLimit(limit);
        if(bankAccount.getTransferLimit()<=0){
            bankAccount.setTransferLimitEnabled(false);
        }
        bankAccount.setTransferLimitEnabled(true);
    }

    public boolean transfer(Long fromNumber,Long toNumber,double sum){
        BankAccount fromBankAccount = bankAccountRepo.findByAccNumber(fromNumber);
        BankAccount toBankAccount = bankAccountRepo.findByAccNumber(toNumber);

        if (sum >= fromBankAccount.getTransferLimit() && fromBankAccount.isTransferLimitEnabled() == true){
            return false;
        }
        TransferHistory transferHistory = new TransferHistory(fromNumber,toNumber,sum);

        fromBankAccount.setBalance(fromBankAccount.getBalance()-sum);
        toBankAccount.setBalance(toBankAccount.getBalance()+sum);

        bankAccountRepo.save(fromBankAccount);
        bankAccountRepo.save(toBankAccount);

        transferHistoryRepo.save(transferHistory);
        return true;
    }

    public BankAccount findBankAccountByUser(AppUser appUser){
        return bankAccountRepo.findByAppUserId(appUser.getId());
    }

    public void setTransferLimit(BankAccount bankAccount,int limit){
        bankAccount.setTransferLimit(limit);
        if(limit > 0){
            bankAccount.setTransferLimitEnabled(true);
        }else{
            bankAccount.setTransferLimitEnabled(false);
        }
        bankAccountRepo.save(bankAccount);
    }
}
