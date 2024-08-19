package com.example.userssrvice.repo;

import com.example.userssrvice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepoitory  extends JpaRepository<Users,String> {
}
