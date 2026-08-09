package car.example.injection.constructor;

public class Car {

    private Specification specification;
    public Car(Specification specification)
    {
        this.specification=specification;
    }
    public void display()
    {
        System.out.println("Car Detail\'s: "+specification.toString());
    }
}
