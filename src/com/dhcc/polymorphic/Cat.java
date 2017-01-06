package com.dhcc.polymorphic;

public class Cat extends Animal{

    public void say() {
       // super.say();
        System.out.println("我是猫。。。"+this.getName());
    }
     
}
