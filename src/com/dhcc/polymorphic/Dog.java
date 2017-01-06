package com.dhcc.polymorphic;

public class Dog extends Animal{
    public void say() {
      //  super.say();
        System.out.println("我是狗。。。"+this.getName());
    }
}
