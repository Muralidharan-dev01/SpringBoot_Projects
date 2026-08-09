package com.loose.coupling;

public class LooseCouplingExample {
   public static void main(String args[]){
     UserDataProvider userDataProvider=new UserDatabaseProvider();
    UserManager userManager =new UserManager(userDataProvider);
    System.out.println(userManager.getUserInfo());

    UserDataProvider webServiceProvider = new WebServiceDataProvider();
    UserManager userManager1=new UserManager(webServiceProvider);
    System.out.println(userManager1.getUserInfo());


    UserDataProvider newDatabaseProvider = new NewDatabaseProvider();
    UserManager userManager2=new UserManager(newDatabaseProvider);
    System.out.println(userManager2.getUserInfo());
   }
}
