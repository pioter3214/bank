package com.example.project1.repo;

import com.example.project1.entity.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferHistoryRepo extends JpaRepository<TransferHistory, Integer> {
    TransferHistory findTransferHistoryByFormAccNumber(int formAccNumber);

    List<TransferHistory> findAllByFormAccNumber(Long fromAccNumber);
    List<TransferHistory> findAllByToAccNumber(Long toAccNumber);
}
