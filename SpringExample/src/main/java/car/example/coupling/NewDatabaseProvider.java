package car.example.coupling;

public class NewDatabaseProvider implements UserDataProvider {

    @Override
    public String getUserDetails() {
        return " New Database in Action!!! ";
    }
}
