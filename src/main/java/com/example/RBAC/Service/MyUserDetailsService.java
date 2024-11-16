package com.example.RBAC.Service;

import com.example.RBAC.Model.MyUser;
import com.example.RBAC.Model.MyUserPrincipal;
import com.example.RBAC.Repo.MyUserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private MyUserRepo myUserRepo;

    public MyUserDetailsService(MyUserRepo myUserRepo) {
        this.myUserRepo = myUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser=myUserRepo.findByEmail(username).orElse(null);

        if(myUser==null){
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(myUser);
    }
}
