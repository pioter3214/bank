package com.example.project1.repo;

import com.example.project1.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser,Long> {
    AppUser findAppUserByUsername(String username);

}
