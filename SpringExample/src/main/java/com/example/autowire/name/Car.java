package com.example.autowire.name;

public class Car {

    private Specification mySpecification1;
    //Initialisation is done by Setter
    public void setMySpecification1(Specification mySpecification) {
        this.mySpecification1 = mySpecification;
    }

//    public Car(Specification specification)
//    {
//        this.mySpecification=specification;
//    }
    public void display()
    {
        System.out.println("Car Detail\'s: "+mySpecification1.toString());
    }
}
