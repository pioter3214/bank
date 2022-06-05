package com.example.project1.repo;

import com.example.project1.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken,Long> {

    VerificationToken findByValue(String value);
}
