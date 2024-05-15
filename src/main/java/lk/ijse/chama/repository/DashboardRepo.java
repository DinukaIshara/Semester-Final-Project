package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.DailyOrders;
import lk.ijse.chama.model.tm.MostSellItemTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardRepo {
    public static List<MostSellItemTm> getMostSellItem() throws SQLException {
        String sql = "SELECT item_id,COUNT(order_id),SUM(qty) FROM order_detail GROUP BY item_id ORDER BY SUM(qty) DESC LIMIT 5;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<MostSellItemTm> sellItem = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            int count = resultSet.getInt(2);
            int sumQty = resultSet.getInt(3);

            MostSellItemTm mostSellItem = new MostSellItemTm(id, count, sumQty);
            sellItem.add(mostSellItem);
        }
        return sellItem;
    }

    public static DailyOrders orderDaily(Date date) throws SQLException {
        String sql = "SELECT o.order_date,COUNT(DISTINCT o.order_id),SUM(od.qty) FROM orders o JOIN order_detail od ON o.order_id = od.order_id WHERE o.order_date = ? GROUP BY o.order_date ORDER BY o.order_date";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, date);

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            Date date1 = resultSet.getDate(1);
            int count = resultSet.getInt(2);
            int totQty = resultSet.getInt(3);

            return new DailyOrders(date1, count, totQty);
        }
        return null;
    }
}
