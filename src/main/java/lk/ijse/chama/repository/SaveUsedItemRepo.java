package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.SaveUsedItem;

import java.sql.Connection;
import java.sql.SQLException;

public class SaveUsedItemRepo {
    public static boolean saveUsedItem(SaveUsedItem bni) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            System.out.println("Road to ItemSave");
            boolean isItemSave = UsedItemRepo.save(bni.getUsedItem());
            System.out.println("isItemSaved");

            if (isItemSave) {
                System.out.println("Road to ItemSupplierSave");
                boolean isItemSupplierDetailSaved = ItemSupplierDetailRepo.save(bni.getItemSupplierDetail());
                System.out.println("isItemSupplierSaved");

                if (isItemSupplierDetailSaved) {
                    connection.commit();
                    return true;
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
