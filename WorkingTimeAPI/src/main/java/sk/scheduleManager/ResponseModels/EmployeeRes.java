package sk.scheduleManager.ResponseModels;

public class EmployeeRes {


    public EmployeeRes(String ID, String firstName, String lastName, String login, boolean isAdmin) {
        this.ID = ID;
        FirstName = firstName;
        LastName = lastName;
        Login = login;
        IsAdmin = isAdmin;
    }

    public String ID;
    public String FirstName;
    public String LastName;
    public String Login;
    public boolean IsAdmin;
}
