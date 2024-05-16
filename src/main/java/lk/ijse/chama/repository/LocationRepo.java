package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.BrandNewItem;
import lk.ijse.chama.model.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LocationRepo {
    public static Location searchByPath(String name) throws SQLException {
        String sql = "SELECT * FROM location WHERE place = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, name);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Location(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getDouble(3)

            );
        }
        return null;
    }

    public static List<String> getPlace() throws SQLException {
        String sql = "SELECT place FROM location";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> placeList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String place = resultSet.getString(1);
            placeList.add(place);
        }
        return placeList;
    }
}
