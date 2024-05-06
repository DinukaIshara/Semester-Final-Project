package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.Employee;
import lk.ijse.chama.model.Repair;
import lk.ijse.chama.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepairRepo {
    public static boolean save(Repair repair) throws SQLException {
        String sql = "INSERT INTO repair VALUES(?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, repair.getRepairId());
        pstm.setObject(2, repair.getReciveDate());
        pstm.setObject(3, repair.getReturnDate());
        pstm.setObject(4, repair.getCost());
        pstm.setObject(5, repair.getDescription());
        pstm.setObject(6, repair.getCustId());
        pstm.setObject(7, repair.getItemName());

        return pstm.executeUpdate() > 0;
    }

    public static List<Repair> getAll() throws SQLException {
        String sql = "SELECT * FROM repair";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Repair> repairList = new ArrayList<>();

        while (resultSet.next()) {
            String repairId = resultSet.getString(1);
            Date reciveDate = resultSet.getDate(2);
            Date returnDate = resultSet.getDate(3);
            double cost = resultSet.getDouble(4);
            String description = resultSet.getString(5);
            String custId = resultSet.getString(6);
            String itemName = resultSet.getString(7);

            Repair repair = new Repair(repairId, reciveDate, returnDate, cost, description, custId, itemName);
            repairList.add(repair);
        }
        return repairList;
    }

    public static boolean update(Repair repair) throws SQLException {
        String sql = "UPDATE repair SET date_recive = ?, date_return = ?, repair_cost = ?, description = ?, cust_id = ?, itemName = ? WHERE rep_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, repair.getReciveDate());
        pstm.setObject(2, repair.getReturnDate());
        pstm.setObject(3, repair.getCost());
        pstm.setObject(4, repair.getDescription());
        pstm.setObject(5, repair.getCustId());
        pstm.setObject(6, repair.getItemName());
        pstm.setObject(7, repair.getRepairId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM repair WHERE rep_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

}