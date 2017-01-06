package com.dhcc.polymorphic;


public class Test {

    @org.junit.Test
    public void test() {
        //继承
        Animal animal = new Animal();
        Cat cat =new Cat();
        Dog dog=new Dog();
        animal.setName("大爷");
        cat.setName("蜜蜜");
        dog.setName("二狗");
        
        animal.say();
        cat.say();
        dog.say();
        
        System.out.println("多态体现........");
        
        //多态体现
        AnimalInt aniInterface = null;
        
        aniInterface = animal;
        aniInterface.say();
        
        aniInterface = cat;
        aniInterface.say();
        
        aniInterface = dog;
        aniInterface.say();
        
        // 4>  3> 2> 1     AnimalInt> Animal >Dog =Cat
    }

}
