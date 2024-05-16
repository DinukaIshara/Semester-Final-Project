package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.Repair;

import java.sql.*;
import java.time.LocalDate;
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
            LocalDate reciveDate = resultSet.getDate(2).toLocalDate();
            LocalDate returnDate = resultSet.getDate(3).toLocalDate();
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

    public static List<String> getId() throws SQLException {
        String sql = "SELECT rep_id FROM repair";
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

    public static Repair searchById(String id) throws SQLException {
        String sql = "SELECT * FROM repair WHERE rep_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String rep_id = resultSet.getString(1);
            LocalDate recive = resultSet.getDate(2).toLocalDate();
            LocalDate retu = resultSet.getDate(3).toLocalDate();
            double cost = resultSet.getDouble(4);
            String description =resultSet.getString(5);
            String cust_id = resultSet.getString(6);
            String itemName = resultSet.getString(7);

            return new Repair(rep_id, recive, retu, cost, description, cust_id, itemName);
        }

        return null;
    }
}
