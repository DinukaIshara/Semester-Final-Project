package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {
    public static boolean placeOrder(PlaceOrder po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false); // meken thama off karanne commit eka

        try {
            System.out.println("Road to order "+po.getOrder());
            boolean isOrderSaved = OrderRepo.save(po.getOrder());
            System.out.println("placed order ");
            if (isOrderSaved) {
                System.out.println("Road to update qty ");
                boolean isQtyUpdated = BrandNewItemRepo.update(po.getOdList());
                System.out.println("updated qty ");
                if (isQtyUpdated) {
                    System.out.println("Road to save Order Detail ");
                    boolean isOrderDetailSaved = OrderDetailRepo.save(po.getOdList());
                    System.out.println("saved order detail");
                    if (isOrderDetailSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();  //  meken commit eka on karano aye
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
