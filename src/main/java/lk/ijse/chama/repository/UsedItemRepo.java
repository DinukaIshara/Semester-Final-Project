package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.UsedItem;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsedItemRepo {
    public static boolean save(UsedItem ui) throws SQLException {
        System.out.println("Save Use Item start");
        String sql = "INSERT INTO used_item VALUES(?, ?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, ui.getItemId());
        pstm.setString(2, ui.getName());
        pstm.setString(3, ui.getCategory());
        pstm.setString(4, ui.getBrand());
        pstm.setString(5, ui.getDescription());

        System.out.println("Save use Item end");

        return pstm.executeUpdate() > 0;
    }
}
