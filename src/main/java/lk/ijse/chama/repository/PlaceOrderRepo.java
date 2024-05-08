package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {
    public static boolean placeOrder(PlaceOrder po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            System.out.println("Road to order "+po.getOrder());
            boolean isOrderSaved = OrderRepo.save(po.getOrder());
            System.out.println("placed order " + isOrderSaved);
            if (isOrderSaved) {
                System.out.println("Road to update qty " + po.getOdList());
                boolean isQtyUpdated = BrandNewItemRepo.update(po.getOdList());
                System.out.println("updated qty " + isQtyUpdated);
                if (isQtyUpdated) {
                    System.out.println("Road to save Order Detail ");
                    boolean isOrderDetailSaved = OrderDetailRepo.save(po.getOdList());
                    System.out.println("saved order detail" + isOrderDetailSaved);
                    if (isOrderDetailSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
