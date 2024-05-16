package lk.ijse.chama.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeTm {
    private String empId;
    private String empName;
    private String empNic;
    private String position;
    private String empTel;
    private Date dob;
    private Date dateRegister;
    private String empEmail;
    private double salary;
}
