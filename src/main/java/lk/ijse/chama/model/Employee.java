package lk.ijse.chama.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
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
    private String path;

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
