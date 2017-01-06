package com.dhcc.polymorphic;

public class Animal implements AnimalInt {
    
    private  String name;
    private int age;
    private String color;
    private int weight;
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
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public Animal() {
        super();
    }
    @Override
    public String toString() {
        return "Animal [name=" + name + ", age=" + age + ", color=" + color
                + ", weight=" + weight + "]";
    }
    
    /* (non-Javadoc)
     * @see com.dhcc.polymorphic.AnimalInt#say()
     */
    @Override
    public void say(){
        System.out.println("我是父类。。。"+this.getName());
    }
    
    public void say2(){
        System.out.println("我是父类。。。"+this.getName());
    }

}
