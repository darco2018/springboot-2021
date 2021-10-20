package com.example.demo3jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    // primary key
    @Id
    @GeneratedValue // automatic generation
    private long id; // bigint not null  primary key (id))
    private String name; // varchar(255)
    private String role; // varchar(255)
    private int age; // integer not null

    // skip id from constructor
    public User(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public User(String name, String role, int age) {
        this.name = name;
        this.role = role;
        this.age = age;
    }

    // JPA expects a default constructor !!!!!
    protected User() {
    }

    public int getAge() {
        return age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
