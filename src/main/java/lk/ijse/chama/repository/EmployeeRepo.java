package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.Employee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class EmployeeRepo {
    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employee.getEmpId());
        pstm.setObject(2, employee.getEmpName());
        pstm.setObject(3, employee.getEmpAddress());
        pstm.setObject(4, employee.getEmpNic());
        pstm.setObject(5, employee.getPosition());
        pstm.setObject(6, employee.getEmpTel());
        pstm.setObject(7, employee.getDob());
        pstm.setObject(8, employee.getDateRegister());
        pstm.setObject(9, employee.getEmpEmail());
        pstm.setObject(10, employee.getSalary());
        //pstm.setObject(11, employee.getPath());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET name = ?, address = ?, nic = ?, position = ?, contact_no = ?, dob = ?, enroll_date = ?, email = ?, basic_salary = ? WHERE emp_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employee.getEmpName());
        pstm.setObject(2, employee.getEmpAddress());
        pstm.setObject(3, employee.getEmpNic());
        pstm.setObject(4, employee.getPosition());
        pstm.setObject(5, employee.getEmpTel());
        pstm.setObject(6, employee.getDob());
        pstm.setObject(7, employee.getDateRegister());
        pstm.setObject(8, employee.getEmpEmail());
        pstm.setObject(9, employee.getSalary());
        //pstm.setObject(10, employee.getPath());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM employee WHERE emp_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> empList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String nic = resultSet.getString(4);
            String position = resultSet.getString(5);
            String contact = resultSet.getString(6);
            java.sql.Date dob = resultSet.getDate(7);
            Date dateRegistration = resultSet.getDate(8);
            String email = resultSet.getString(9);
            double salary = resultSet.getDouble(10);
            //String path = resultSet.getString(11);

            Employee employee = new Employee(id, name, address, nic, position, contact , dob, dateRegistration, email, salary);
            empList.add(employee);
        }
        return empList;
    }
}
