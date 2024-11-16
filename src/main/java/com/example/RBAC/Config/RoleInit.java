package com.example.RBAC.Config;

import com.example.RBAC.Model.Role;
import com.example.RBAC.Repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInit implements CommandLineRunner {
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepo.findByRoleName("ROLE_USER").isEmpty()) {
            roleRepo.save(new Role("ROLE_USER"));
        }
        if (roleRepo.findByRoleName("ROLE_ADMIN").isEmpty()) {
            roleRepo.save(new Role("ROLE_ADMIN"));
        }
        if (roleRepo.findByRoleName("ROLE_MODERATOR").isEmpty()) {
            roleRepo.save(new Role("ROLE_MODERATOR"));
        }
    }
}
