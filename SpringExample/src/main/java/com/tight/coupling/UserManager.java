package com.tight.coupling;

public class UserManager {

    //Tightly coupled
    private UserDatabase userDatabase=new UserDatabase();
    public String getUserInfo()
    {
        return userDatabase.getUserDetails();
    }
}
