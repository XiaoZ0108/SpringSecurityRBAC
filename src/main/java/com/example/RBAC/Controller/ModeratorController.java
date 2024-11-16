package com.example.RBAC.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mode")
public class ModeratorController {
    @GetMapping()
    public String tryd(){
        return "moderator";
    }
}
