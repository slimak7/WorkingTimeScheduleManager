package sk.scheduleManager.Services;

import sk.scheduleManager.Exceptions.ScheduleAuthException;
import sk.scheduleManager.Exceptions.ScheduleDataAccessException;
import sk.scheduleManager.Models.Employee;
import sk.scheduleManager.RequestModels.EmployeeReq;
import sk.scheduleManager.RequestModels.LogInReq;
import sk.scheduleManager.ResponseModels.EmployeeRes;
import sk.scheduleManager.ResponseModels.JwtTokenRes;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface IEmployeeService {
    List<EmployeeRes> GetAll(int pageNumber, int count) throws ScheduleDataAccessException;
    public EmployeeRes Add(EmployeeReq employee) throws NoSuchAlgorithmException, InvalidKeySpecException;

    public JwtTokenRes LogIn(LogInReq logInReq) throws ScheduleAuthException, NoSuchAlgorithmException, InvalidKeySpecException;
}
