package car.example.coupling;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LooseCouplingExample {
   public static void main(String args[]){
//     UserDataProvider userDataProvider=new UserDatabaseProvider();
//    UserManager userManager =new UserManager(userDataProvider);
       //System.out.println(userManager.getUserInfo());
       ApplicationContext context = new ClassPathXmlApplicationContext("applicationLooseCoupling.xml");

       UserManager manager1= (UserManager) context.getBean("Manager1");
       System.out.println(manager1.getUserInfo());

       UserManager manager2= (UserManager) context.getBean("Manager2");
       System.out.println(manager2.getUserInfo());

       UserManager manager3= (UserManager) context.getBean("Manager3");
       System.out.println(manager3.getUserInfo());


//    UserDataProvider webServiceProvider = new WebServiceDataProvider();
//    UserManager userManager1=new UserManager(webServiceProvider);
//    System.out.println(userManager1.getUserInfo());
//
//
//    UserDataProvider newDatabaseProvider = new NewDatabaseProvider();
//    UserManager userManager2=new UserManager(newDatabaseProvider);
//    System.out.println(userManager2.getUserInfo());
   }
}
