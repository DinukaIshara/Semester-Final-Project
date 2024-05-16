package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
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

    public static List<String> getLoca() throws SQLException {
        String sql = "SELECT location FROM transport";
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

    public static Transport searchByLoca(String location) throws SQLException {
        String sql = "SELECT * FROM transport WHERE location = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, location);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String tr_id = resultSet.getString(1);
            String vehicalNo = resultSet.getString(2);
            String driverName = resultSet.getString(3);
            String loca = resultSet.getString(4);
            double cost =resultSet.getDouble(5);

            return new Transport(tr_id, vehicalNo, driverName, loca, cost);
        }

        return null;
    }

    public static Transport searchByLocation(String loc) throws SQLException {
        String sql = "SELECT * FROM transport WHERE location = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, loc);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String tr_id = resultSet.getString(1);
            String ve_no = resultSet.getString(2);
            String driver_name = resultSet.getString(3);
            String location = resultSet.getString(4);
            double transport_cost =resultSet.getDouble(5);

            return new Transport(tr_id, ve_no, driver_name, location, transport_cost);
        }

        return null;
    }

    public static List<String> getlocation() throws SQLException {
        String sql = "SELECT location FROM transport";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> locationList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String location = resultSet.getString(1);
            locationList.add(location);
        }
        return locationList;

    }
}
