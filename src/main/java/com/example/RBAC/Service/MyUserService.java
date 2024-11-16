package com.example.RBAC.Service;

import com.example.RBAC.Config.AppRunTimeException;
import com.example.RBAC.Model.MyUser;
import com.example.RBAC.Model.Role;

import com.example.RBAC.Repo.MyUserRepo;
import com.example.RBAC.Repo.RoleRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MyUserService {
    private final MyUserRepo myUserRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepo roleRepo;

    public MyUserService(MyUserRepo myUserRepo, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder,RoleRepo roleRepo) {
        this.myUserRepo = myUserRepo;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo=roleRepo;
    }

    public String loginVerify(MyUser myUser) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(myUser.getEmail(),myUser.getPassword()));
        if(authentication.isAuthenticated()){
            MyUser currentUser=myUserRepo.findByEmail(myUser.getEmail()).orElse(null);
            assert currentUser != null;
            return jwtService.generateToken(currentUser);
        }
        throw new AppRunTimeException("Invalid Credential");
    }
    public MyUser register(MyUser myUser) {
        try {
            myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
            myUser.addRoles(getRole("USER"));
            return myUserRepo.save(myUser);
        }catch (Exception e){
            throw new AppRunTimeException("User Already Exist");
        }
    }

    public MyUser registerAdmin(MyUser myUser) {
        try {
            myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
            myUser.addRoles(getRole("USER"));
            myUser.addRoles(getRole("ADMIN"));
            return myUserRepo.save(myUser);
        }catch (Exception e){
            throw new AppRunTimeException("User Already Exist");
        }
    }

    public MyUser registerMode(MyUser myUser) {
        try {
            myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
            myUser.addRoles(getRole("MODERATOR"));
            return myUserRepo.save(myUser);
        }catch (Exception e){
            throw new AppRunTimeException("User Already Exist");
        }
    }

    public List<MyUser> getAllUser(){
        return myUserRepo.findAll();
    }

    private Role getRole(String roles){
        return roleRepo.findByRoleName("ROLE_"+roles).orElse(null);
    }
}
