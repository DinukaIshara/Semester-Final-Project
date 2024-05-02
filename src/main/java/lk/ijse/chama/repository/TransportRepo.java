package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.Supplier;
import lk.ijse.chama.model.Transport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransportRepo {
    public static boolean save(Transport transport) throws SQLException {
        String sql = "INSERT INTO transport VALUES(?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, transport.getTrId());
        pstm.setObject(2, transport.getVehicalNo());
        pstm.setObject(3, transport.getDriverName());
        pstm.setObject(4, transport.getLocation());
        pstm.setObject(5, transport.getCost());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Transport transport) throws SQLException {
        String sql = "UPDATE transport SET vehicle_no = ?, driver_name = ?, location = ?, transport_cost = ? WHERE tr_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, transport.getVehicalNo());
        pstm.setObject(2, transport.getDriverName());
        pstm.setObject(3, transport.getLocation());
        pstm.setObject(4, transport.getCost());
        pstm.setObject(5, transport.getTrId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM transport WHERE tr_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Transport> getAll() throws SQLException {
        String sql = "SELECT * FROM transport";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Transport> transList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String vehicalNo = resultSet.getString(2);
            String driverName = resultSet.getString(3);
            String location = resultSet.getString(4);
            double cost = resultSet.getDouble(5);

            Transport transport = new Transport(id, vehicalNo, driverName,location, cost);
            transList.add(transport);
        }
        return transList;
    }

    public static List<String> getTel() throws SQLException {
        String sql = "SELECT tr_id FROM transport";
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

    public static Transport searchById(String id) throws SQLException {
        String sql = "SELECT * FROM transport WHERE tr_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String tr_id = resultSet.getString(1);
            String vehicalNo = resultSet.getString(2);
            String driverName = resultSet.getString(3);
            String location = resultSet.getString(4);
            double cost =resultSet.getDouble(5);

            return new Transport(tr_id, vehicalNo, driverName, location, cost);
        }

        return null;
    }

}
