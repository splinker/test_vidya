package com.test.procuderes.testdemoprocedures.model;

import org.springframework.stereotype.Component;

@Component
public class Employee {
    private int id;
    private String username;
    private String name;
    private String role;
    private String city;
    private String country;

    public Employee(int id, String name, String role, String city, String country) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.city = city;
        this.country = country;
    }

    public Employee() {
    }

    public Employee(int id, String username, String name, String role, String city, String country) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
        this.city = city;
        this.country = country;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
