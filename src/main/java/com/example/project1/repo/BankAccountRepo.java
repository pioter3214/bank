package com.example.project1.repo;

import com.example.project1.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount,Long> {
    BankAccount findByAppUserId(Long id);

    BankAccount findByAccNumber(Long accNumber);
}
