package lk.ijse.chama.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Data
public class Employee {

    private String empId;
    private String empName;
    private String empAddress;
    private String empNic;
    private String position;
    private String empTel;
    private Date dob;
    private Date dateRegister;
    private String empEmail;
    private double salary;
    //private String path;

    public Employee(String empId, String empName, String empAddress, String empNic, String position, String empTel, Date dob, Date dateRegister, String empEmail, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.empAddress = empAddress;
        this.empNic = empNic;
        this.position = position;
        this.empTel = empTel;
        this.dob = dob;
        this.dateRegister = dateRegister;
        this.empEmail = empEmail;
        this.salary = salary;
        //this.path = path;
    }
    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", empNic='" + empNic + '\'' +
                ", position='" + position + '\'' +
                ", empTel='" + empTel + '\'' +
                ", dob='" + dob + '\'' +
                ", dateRegister='" + dateRegister + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", salary=" + salary +
                '}';
    }
}
