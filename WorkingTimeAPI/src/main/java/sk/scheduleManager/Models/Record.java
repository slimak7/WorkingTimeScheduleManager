package sk.scheduleManager.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "Records")
public class Record {

    @Id
    @GenericGenerator(name = "generator", strategy = "guid", parameters = {})
    @GeneratedValue(generator = "generator")
    @Column(name = "RecordID" , columnDefinition="uniqueidentifier")
    private String ID;

    @ManyToOne
    @JoinColumn(name = "EmployeeIDFK", referencedColumnName = "EmployeeID")
    private Employee Employee;

    @Column(name = "MonthYear")
    private Date MonthYear;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public sk.scheduleManager.Models.Employee getEmployee() {
        return Employee;
    }

    public void setEmployee(sk.scheduleManager.Models.Employee employee) {
        Employee = employee;
    }

    public Date getMonthYear() {
        return MonthYear;
    }

    public void setMonthYear(Date monthYear) {
        MonthYear = monthYear;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
