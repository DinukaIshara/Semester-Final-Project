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
            System.out.println("Road to ItemSave" + bni.getBrandNewItem());
            boolean isItemSave = BrandNewItemRepo.save(bni.getBrandNewItem());
            System.out.println("isItemSaved" + isItemSave);

            if (isItemSave) {
               System.out.println("Road to ItemSupplierSave" + bni.getItemSupplier());
               boolean isItemSupplierDetailSaved = ItemSupplierDetailRepo.save(bni.getItemSupplier());
               System.out.println("isItemSupplierSaved" + isItemSupplierDetailSaved);

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
            System.out.println("Road to ItemUpdate = " + si.getBrandNewItem());
            boolean isItemUpdate = BrandNewItemRepo.update(si.getBrandNewItem());
            System.out.println("isItemUpdated");

            if (isItemUpdate) {
                System.out.println("Road to ItemSupplierUpdate = " + si.getItemSupplier());
                boolean isItemSupplierDetailUpdate = ItemSupplierDetailRepo.update(si.getItemSupplier());
                System.out.println("isItemSupplierUpdated");

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
