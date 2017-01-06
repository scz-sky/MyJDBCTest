package com.dhcc.test;

public class Table1 {
 private String username;
 private int age;
 private String email;
 
public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}

public int getAge() {
    return age;
}

public void setAge(int age) {
    this.age = age;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}
public Table1() {
    super();
}

public Table1(String username, int age, String email) {
    super();
    this.username = username;
    this.age = age;
    this.email = email;
}

@Override
public String toString() {
    return "table1 [username=" + username + ", age=" + age + ", email=" + email
            + "]";
}
 
}
