package com.loose.coupling;

public class UserDatabaseProvider implements UserDataProvider {
    @Override
    public String getUserDetails()
    {
        return "These are the UserDetails from UserDatabase !!!";
    }
}
