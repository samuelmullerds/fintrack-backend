package com.fintrack.fintrack_backend.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Transaction> transactions;
    
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    public User(){}

        public Long getId(){
            return id;
        }
        public void setId(Long id){
            this.id = id;
        }
        public String getName(){
            return name;
        }

        public void setName(String name){
            this.name = name;
        }
        public String getEmail(){
            return email;
        }
        public void setEmail(String email){
            this.email = email;
        }
        public String getPassword(){
            return password;
        }
        public void setPassword(String password){
            this.password = password;
        }
    }
    

