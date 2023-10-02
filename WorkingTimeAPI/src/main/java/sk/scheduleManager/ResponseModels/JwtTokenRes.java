package sk.scheduleManager.ResponseModels;

public class JwtTokenRes {

    public String Token;
    public EmployeeRes Employee;

    public JwtTokenRes(String token, EmployeeRes employee) {
        Token = token;
        Employee = employee;
    }
}
