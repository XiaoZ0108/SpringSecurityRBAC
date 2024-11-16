package com.example.RBAC.Controller;

import com.example.RBAC.Model.MyUser;
import com.example.RBAC.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyController {
    @Autowired
    MyUserService myUserService;

    @PostMapping("/regis")
    public MyUser registerInit(@RequestBody MyUser myUser){
        return myUserService.register(myUser);
    }

    @PostMapping("/AdminRegis")
    public MyUser registerInit2(@RequestBody MyUser myUser){
        return myUserService.registerAdmin(myUser);
    }

    @PostMapping("/ModeRegis")
    public MyUser registerInit3(@RequestBody MyUser myUser){
        return myUserService.registerMode(myUser);
    }

    @PostMapping("/login")
    public String login(@RequestBody MyUser myUser){
        return myUserService.loginVerify(myUser);
    }

    @GetMapping("/getAll")
    public List<MyUser> getAllUser(){
        return myUserService.getAllUser();
    }
}
