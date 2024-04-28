package lk.ijse.chama.repository;

import lk.ijse.chama.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lk.ijse.chama.db.*;

public class CustomerRepo {
    public static boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getCustId());
        pstm.setObject(2, customer.getCName());
        pstm.setObject(3, customer.getCAddress());
        pstm.setObject(4, customer.getCNIC());
        pstm.setObject(5, customer.getContactNo());
        pstm.setObject(6, customer.getCEmail());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET c_name = ?, c_address = ?, c_nic = ?, contact_no = ?, email = ? WHERE cust_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getCName());
        pstm.setObject(2, customer.getCAddress());
        pstm.setObject(3, customer.getCNIC());
        pstm.setObject(4, customer.getContactNo());
        pstm.setObject(5, customer.getCEmail());
        pstm.setObject(6, customer.getCustId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE cust_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
    public static Customer searchById(String tel) throws SQLException {
        String sql = "SELECT * FROM customer WHERE contact_no = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, tel);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String nic = resultSet.getString(4);
            String contact =resultSet.getString(5);
            String email = resultSet.getString(6);

            return new Customer(cus_id, name, address, nic, contact, email);
        }

        return null;
    }

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> cusList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String nic = resultSet.getString(4);
            String tel = resultSet.getString(5);
            String email = resultSet.getString(6);

            Customer customer = new Customer(id, name, address, nic, tel, email);
            cusList.add(customer);
        }
        return cusList;
    }

    public static List<String> getTel() throws SQLException {
        String sql = "SELECT contact_no FROM customer";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

}
