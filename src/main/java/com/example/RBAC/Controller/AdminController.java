package com.example.RBAC.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    //@PreAuthorize("hasRole('ADMIN')")
    //@PostAuthorize("returnObject.username == authentication.name")
    @GetMapping()
    public String tryd(){
        return "ADMIN";
    }
}
