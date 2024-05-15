package lk.ijse.chama.repository;

import javafx.collections.FXCollections;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.BrandNewItem;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.ItemSupplierDetail;
import lk.ijse.chama.model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandNewItemRepo {

    public static boolean save(BrandNewItem bi) throws SQLException {
        System.out.println("item : " + bi);
        String sql = "INSERT INTO item VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, bi.getItemId());
        pstm.setString(2, bi.getName());
        pstm.setString(3, bi.getCategory());
        pstm.setString(4, bi.getBrand());
        pstm.setString(5, bi.getModelNo());
        pstm.setString(6, bi.getDescription());
        pstm.setString(7, bi.getWarranty());
        pstm.setString(8, bi.getType());
        pstm.setString(9, bi.getPath());

        return pstm.executeUpdate() > 0;
    }

    public static List<BrandNewItem> getAll() {
        String sql = "SELECT * FROM item";
        List<BrandNewItem> itemList;

        PreparedStatement pstm = null;
        try {
            pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();

            itemList = new ArrayList<>();

            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String category = resultSet.getString(3);
                String brand = resultSet.getString(4);
                String modelNo = resultSet.getString(5);
                String warranty = resultSet.getString(6);
                String description = resultSet.getString(7);
                String type = resultSet.getString(8);
                String path = resultSet.getString(9);

                BrandNewItem item = new BrandNewItem(id, name, category, brand, modelNo, warranty, description, type, path);
                itemList.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return itemList;
    }

    public static List<String> getCode() throws SQLException {
        String sql = "SELECT item_id FROM item";

        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> idList = new ArrayList<>();
        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static List<String> getName() throws SQLException {
        String sql = "SELECT name FROM item";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> nameList = new ArrayList<>();
        while (resultSet.next()) {
            nameList.add(resultSet.getString(1));
        }
        return nameList;
    }

    public static BrandNewItem searchById(String code) throws SQLException {
        String sql = "SELECT * FROM item WHERE item_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, code);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new BrandNewItem(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)

            );
        }
        return null;
    }

    public static BrandNewItem searchByName(String name) throws SQLException {
        String sql = "SELECT * FROM item WHERE name = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, name);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new BrandNewItem(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)

            );
        }
        return null;
    }

    public static boolean update(List<OrderDetail> isList) throws SQLException {
        for (OrderDetail is : isList) {
            System.out.println(is.getItemCode() + "qtyUpdate Item - " + is.getQty());
            boolean isUpdateQty = updateQty(is.getItemCode(), is.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(String itemCode, int qty) throws SQLException {
        String sql = "UPDATE item_supplier_detail SET qty = qty - ? WHERE item_id = ?";
        System.out.println("update Item QTY");
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, itemCode);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(BrandNewItem brandNewItem) throws SQLException {
        String sql = "UPDATE item SET name = ?, category = ?, brand = ?, model_no = ?, description = ?, warranty = ?, type = ?, path = ? WHERE item_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, brandNewItem.getName());
        pstm.setObject(2, brandNewItem.getCategory());
        pstm.setObject(3, brandNewItem.getBrand());
        pstm.setObject(4, brandNewItem.getModelNo());
        pstm.setObject(5, brandNewItem.getDescription());
        pstm.setObject(6, brandNewItem.getWarranty());
        pstm.setObject(7, brandNewItem.getType());
        pstm.setObject(8, brandNewItem.getPath());
        pstm.setObject(9, brandNewItem.getItemId());

        return pstm.executeUpdate() > 0;
    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM item WHERE item_id = ?";

        System.out.println(id);

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
