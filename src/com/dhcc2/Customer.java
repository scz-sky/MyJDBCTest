package com.dhcc2;

import java.sql.Date;

public class Customer {

    private int id;
    private String name;
    private int age;
    private Date brith;
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
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Date getBrith() {
        return brith;
    }
    public void setBrith(Date brith) {
        this.brith = brith;
    }
    public Customer(int id, String name, int age, Date brith) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.brith = brith;
    }
    public Customer() {
        super();
    }
    @Override
    public String toString() {
        return "person_info [id=" + id + ", name=" + name + ", age=" + age
                + ", brith=" + brith + "]";
    }
    
}
