package com.example.autowire.constructor;

public class Car {

    private Specification mySpecification;

//    public void setMySpecification1(Specification mySpecification) {
//        this.mySpecification1 = mySpecification;
//    }

    public Car(Specification specification)
    {
        this.mySpecification=specification;
    }
    public void display()
    {
        System.out.println("Car Detail\'s: "+mySpecification.toString());
    }
}
