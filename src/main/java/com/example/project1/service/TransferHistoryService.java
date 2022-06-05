package com.example.project1.service;

import com.example.project1.entity.TransferHistory;
import com.example.project1.repo.TransferHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransferHistoryService {
    private TransferHistoryRepo transferHistoryRepo;

    @Autowired
    public TransferHistoryService(TransferHistoryRepo transferHistoryRepo) {
        this.transferHistoryRepo = transferHistoryRepo;
    }

    public List<TransferHistory> getTransferHistory(Long fromAccNumber){
        List<TransferHistory> transferHistories = new ArrayList<>();
        transferHistories.addAll(transferHistoryRepo.findAllByFormAccNumber(fromAccNumber));
        transferHistories.addAll(transferHistoryRepo.findAllByToAccNumber(fromAccNumber));
        return transferHistories;
    }
}
