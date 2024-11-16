package com.example.RBAC.Model;

import jakarta.persistence.*;



import java.util.HashSet;

import java.util.Set;
@Entity
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;


//    @Enumerated(EnumType.STRING)
//    @Column(name="role")
//    private Role2 role=Role2.ROLE_USER;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
              joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles=new HashSet<>();

    public MyUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }


    public MyUser() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRoles(Role role){
        this.roles.add(role);
    }
}
