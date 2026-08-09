package car.example.injection.constructor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    static void main(String[] args) {
        ApplicationContext context= new ClassPathXmlApplicationContext("applicationConstructorInjection.xml");

        Car car= (Car) context.getBean("myCar");
       car.display();
    }
}
