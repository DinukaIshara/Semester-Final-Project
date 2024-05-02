package lk.ijse.chama.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.model.*;
import lk.ijse.chama.repository.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UsedItemFormController {

    @FXML
    private JFXComboBox<String> cmbBrand;

    @FXML
    private JFXComboBox<String> cmbCategory;

    @FXML
    private JFXComboBox<String> cmbSupId;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupCompany;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblSupCompany;

    @FXML
    private Label lblSupCompanyName;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<?> tableUsedItem;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;
    public void initialize() {
        getSupplierId();
        getBrand();
        getCategory();
    }

    private void getSupplierId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = SupplierRepo.getId();

            for(String id : idList) {
                obList.add(id);
            }

            cmbSupId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getBrand() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Hp");
        obList.add("Msi");
        obList.add("Asus");
        obList.add("Acer");

        cmbBrand.setItems(obList);

    }

    private void getCategory(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Laptop");
        obList.add("Monitor");
        obList.add("Keyboard");
        obList.add("Mouse");
        obList.add("Headset");
        obList.add("Processor");
        obList.add("Motherboard");
        obList.add("Memory");
        obList.add("Storage");
        obList.add("Graphic Card");

        cmbCategory.setItems(obList);
    }

    @FXML
    void btnAddNewSupplierOnAction(ActionEvent event) throws IOException {
        AnchorPane supRootNode = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));
        rootNode.getChildren().clear();
        rootNode.getChildren().add(supRootNode);
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane itemRootNode = FXMLLoader.load(this.getClass().getResource("/view/item_form.fxml"));
        rootNode.getChildren().clear();
        rootNode.getChildren().add(itemRootNode);
    }

    @FXML
    void btnSaveItemOnAction(ActionEvent event) {
        String itemId = txtItemId.getText();
        String name = txtName.getText();
        String category = cmbCategory.getValue();
        String brand = cmbBrand.getValue();
        String description = txtDescription.getText();

        var item = new UsedItem(itemId, name, category, brand, description);

        /*String supId = cmbSupId.getValue();
        int handOnQty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        var itemSupplier = new ItemSupplierDetail(itemId,supId,handOnQty,unitPrice);

        SaveUsedItem ui = new SaveUsedItem(item, itemSupplier);*/
        try {
            boolean isSaved = UsedItemRepo.save(item);
            System.out.println("saved used Item");

            if (isSaved) {
                System.out.println("Road to Item Supplier save");
                String supId = cmbSupId.getValue();
                int handOnQty = Integer.parseInt(txtQty.getText());
                double unitPrice = Double.parseDouble(txtUnitPrice.getText());

                var itemSupplier = new ItemSupplierDetail(itemId,supId,handOnQty,unitPrice);

                boolean isDetailSave = ItemSupplierDetailRepo.save(itemSupplier);
                System.out.println("Item Supplier saved");

                if(isDetailSave){
                    new Alert(Alert.AlertType.CONFIRMATION, "Item saved!").show();
                    //clearFields();
                    initialize();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {

    }

    @FXML
    void cmbBarandOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCategoryOnAction(ActionEvent event) {

    }

    @FXML
    void cmbSupIdOnAction(ActionEvent event) {
        String id = String.valueOf(cmbSupId.getValue());
        try {
            Supplier supplier = SupplierRepo.searchById(id);

            lblSupCompanyName.setText(supplier.getCompanyName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtDescriptionOnAction(ActionEvent event) {

    }

    @FXML
    void txtItemId(ActionEvent event) {

    }

    @FXML
    void txtNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {

    }

    @FXML
    void txtUnitPriceOnAction(ActionEvent event) {

    }

}