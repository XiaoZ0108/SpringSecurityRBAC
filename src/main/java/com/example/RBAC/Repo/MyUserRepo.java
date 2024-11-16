package com.example.RBAC.Repo;

import com.example.RBAC.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepo extends JpaRepository<MyUser,Long> {
    Optional<MyUser> findByEmail(String email);
}
