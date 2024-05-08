package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static boolean save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier VALUES(?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, supplier.getSupId());
        pstm.setObject(2, supplier.getCompanyName());
        pstm.setObject(3, supplier.getPersonName());
        pstm.setObject(4, supplier.getTel());
        pstm.setObject(5, supplier.getLocation());
        pstm.setObject(6, supplier.getEmail());

        return pstm.executeUpdate() > 0;
    }

    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> supList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String companyName = resultSet.getString(2);
            String personName = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String location = resultSet.getString(5);
            String email = resultSet.getString(6);

            Supplier supplier = new Supplier(id, companyName, personName, tel, location, email);
            supList.add(supplier);
        }
        return supList;
    }
    public static Supplier searchById(String id) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE sup_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String sup_id = resultSet.getString(1);
            String comName = resultSet.getString(2);
            String person = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String location =resultSet.getString(5);
            String email = resultSet.getString(6);

            return new Supplier(sup_id, comName, person, contact, location, email);
        }

        return null;
    }

    public static Supplier searchByName(String name) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE person_name = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String sup_id = resultSet.getString(1);
            String comName = resultSet.getString(2);
            String person = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String location =resultSet.getString(5);
            String email = resultSet.getString(6);

            return new Supplier(sup_id, comName, person, contact, location, email);
        }

        return null;
    }

    public static List<String> getId() throws SQLException {
        String sql = "SELECT sup_id FROM supplier";
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

    public static List<String> getName() throws SQLException {
        String sql = "SELECT person_name FROM supplier";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> nameList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            nameList.add(id);
        }
        return nameList;
    }

    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET person_name = ?, company_name = ?, contact_no = ?, location = ?, email = ? WHERE sup_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, supplier.getCompanyName());
        pstm.setObject(2, supplier.getPersonName());
        pstm.setObject(3, supplier.getTel());
        pstm.setObject(4, supplier.getLocation());
        pstm.setObject(5, supplier.getEmail());
        pstm.setObject(6, supplier.getSupId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM supplier WHERE sup_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
