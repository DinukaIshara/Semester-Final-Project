package lk.ijse.chama.repository;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRepo {
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT CONCAT('O', MAX(CAST(SUBSTRING(order_id, 2) AS UNSIGNED))) AS max_order_id FROM orders";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

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

    /*public static List<Order> searchByDate(String date) throws SQLException {
        String sql = "SELECT * FROM orders WHERE order_date = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, date);

        List<Order> newList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String order_id = resultSet.getString(1);
            String cust_id = resultSet.getString(2);
            String tr_id = resultSet.getString(3);
            Date order_date = resultSet.getDate(4);
            String payment = resultSet.getString(5);

            newList.add(new Order(order_id, cust_id, tr_id, (java.sql.Date) order_date,payment));
        }

        return newList;
    }*/

}
