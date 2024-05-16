package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.SaveBrandNewItem;

import java.sql.Connection;
import java.sql.SQLException;


public class SaveBrandNewItemRepo {
    public static boolean saveBrandNewItem(SaveBrandNewItem bni) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isItemSave = BrandNewItemRepo.save(bni.getBrandNewItem());

            if (isItemSave) {
               boolean isItemSupplierDetailSaved = ItemSupplierDetailRepo.save(bni.getItemSupplier());

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

    public static boolean updateBrandNewItem(SaveBrandNewItem si) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isItemUpdate = BrandNewItemRepo.update(si.getBrandNewItem());

            if (isItemUpdate) {
                boolean isItemSupplierDetailUpdate = ItemSupplierDetailRepo.update(si.getItemSupplier());

                if (isItemSupplierDetailUpdate) {
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
