package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRepo {
    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?, ?, ?, ? ,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        System.out.println(order);
        pstm.setString(1, order.getOrderId());
        pstm.setString(2, order.getCustomerId());
        pstm.setString(3, order.getTrId());
        pstm.setDate(4, order.getDate());
        pstm.setString(5, order.getPayment());

        return pstm.executeUpdate() > 0;
    }

    public static List<Order> getAll() throws SQLException {
        String sql = "SELECT * FROM orders";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Order> orderList = new ArrayList<>();

        while (resultSet.next()) {
            String orderId = resultSet.getString(1);
            String custId = resultSet.getString(2);
            String trId = resultSet.getString(3);
            Date date = resultSet.getDate(4);
            String payment = resultSet.getString(5);

            Order order = new Order(orderId, custId, trId, (java.sql.Date) date, payment);
            orderList.add(order);
        }
        return orderList;
    }

    public static List<String> getAllDate() throws SQLException {
        String sql = "SELECT order_date FROM orders GROUP BY order_date";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> dateList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            dateList.add(id);
        }
        return dateList;
    }

    public static String getLastOId() throws SQLException {
        String sql = "SELECT order_id FROM orders ORDER BY CAST(SUBSTRING(order_id, 2) AS UNSIGNED) DESC LIMIT 1;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    public static double getNetTot(String oId) throws SQLException {
        String sql = "SELECT SUM(od.qty * od.unit_price) AS net_total FROM orders o JOIN order_detail od ON o.order_id = od.order_id WHERE o.order_id = ? GROUP BY o.order_id;";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        System.out.println(oId);
        pstm.setString(1, oId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            double netTot = resultSet.getDouble(1);
            System.out.println(netTot);
            return netTot;
        }
        return 0.0;
    }

}
