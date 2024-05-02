package lk.ijse.chama.repository;

import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.BrandNewItem;
import lk.ijse.chama.model.ItemSupplierDetail;
import lk.ijse.chama.model.SaveBrandNewItem;

import java.sql.Connection;
import java.sql.SQLException;


public class SaveBrandNewItemRepo {
    public static boolean saveBrandNewItem(SaveBrandNewItem bni) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            System.out.println("Road to ItemSave");
            boolean isItemSave = BrandNewItemRepo.save(bni.getBrandNewItem());
            System.out.println("isItemSaved");

            if (isItemSave) {
               System.out.println("Road to ItemSupplierSave");
               boolean isItemSupplierDetailSaved = ItemSupplierDetailRepo.save(bni.getItemSupplier());
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

    public static boolean updateBrandNewItem(SaveBrandNewItem si) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            System.out.println("Road to ItemSave");
            boolean isItemUpdate = BrandNewItemRepo.update(si.getBrandNewItem());
            System.out.println("isItemSaved");

            if (isItemUpdate) {
                System.out.println("Road to ItemSupplierSave");
                boolean isItemSupplierDetailUpdate = ItemSupplierDetailRepo.update(si.getItemSupplier());
                System.out.println("isItemSupplierSaved");

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
