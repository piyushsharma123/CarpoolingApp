package com.example.project;

public class User {
    public String name,email,gender,tid,designation,address,phone,role;
    public User(){

    }
    public User(String name, String email, String gender, String tid, String designation, String address, String phone,String role){
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.tid = tid;
        this.designation = designation;
        this.address = address;
        this.phone = phone;
        this.role = role;
    }
}
