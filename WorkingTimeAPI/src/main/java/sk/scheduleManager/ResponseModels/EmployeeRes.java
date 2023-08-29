package sk.scheduleManager.ResponseModels;

public class EmployeeRes {
    public EmployeeRes(String ID, String firstName, String lastName) {
        this.ID = ID;
        FirstName = firstName;
        LastName = lastName;
    }

    public String ID;
    public String FirstName;
    public String LastName;
}
