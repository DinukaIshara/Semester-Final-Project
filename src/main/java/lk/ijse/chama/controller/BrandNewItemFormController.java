package lk.ijse.chama.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lk.ijse.chama.model.*;
import lk.ijse.chama.model.tm.BrandNewItemTm;
import lk.ijse.chama.repository.*;
import lk.ijse.chama.util.Regex;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BrandNewItemFormController {

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtSearchItemName;

    @FXML
    private TableView tableBrandNewItem;

    @FXML
    private AnchorPane imgRootNode;

    @FXML
    private ImageView itemImage;

    @FXML
    private JFXComboBox cmbType;

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
    private TextField txtDescription;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtWaranty;

    private Image image;

    public void initialize() { // The method that is called first when the page is loaded
        getSupplierId();
        getBrand();
        getCategory();
        getType();
        setCellValueFactory();
        loadAllItems();
        getItemName();
    }

    private void setCellValueFactory() { // Set ItemTm in column
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSupCompany.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
    }

    private void loadAllItems() { // Load All Items In ItemTm Table
        ObservableList<BrandNewItemTm> obList = FXCollections.observableArrayList();

        try {
            List<BrandNewItem> itemList = BrandNewItemRepo.getAll();
            List<ItemSupplierDetail> supplierDetail = ItemSupplierDetailRepo.getAll();
            List<Supplier> suppliers = SupplierRepo.getAll();
            BrandNewItemTm tm = null;
                for(ItemSupplierDetail itemSupplier : supplierDetail) {
                    String item_name = null;
                    String item_category = null;
                    String item_brand = null;
                    String supplierCompany = null;


                    for (BrandNewItem brandNewItemTm : itemList){
                        if (brandNewItemTm.getItemId().equals(itemSupplier.getItemId())){
                          item_name =   brandNewItemTm.getName();
                          item_category = brandNewItemTm.getCategory();
                          item_brand =brandNewItemTm.getBrand();
                        }
                    }

                    for(Supplier supplier: suppliers){
                        if(supplier.getSupId().equals(itemSupplier.getSupId())){
                            supplierCompany = supplier.getCompanyName();
                        }
                    }


                    tm = new BrandNewItemTm(
                        itemSupplier.getItemId(),
                        item_name,
                        item_category,
                        item_brand,
                        itemSupplier.getUnitPrice(),
                        itemSupplier.getQty(),
                        supplierCompany
                    );

                obList.add(tm);
            }
            tableBrandNewItem.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveItemOnAction(ActionEvent event) {
        String itemId = txtItemId.getText();
        String name = txtName.getText();
        String category = txtCategory.getText();
        String brand = txtBrand.getText();
        LocalDate date = txtDate.getValue();
        String warranty = txtWaranty.getText();;
        String description = txtDescription.getText();
        String type = String.valueOf(cmbType.getValue());
        String path = image.getUrl();

        var item = new BrandNewItem(itemId, name, category, brand, date, description, warranty, type, path); //Set Item Table Data

        itemId = txtItemId.getText();
        String supId = txtSupplierId.getText();
        int handOnQty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        var itemSupplier = new ItemSupplierDetail(itemId,supId,handOnQty,unitPrice); //Set Item Supplier Table Data

        SaveBrandNewItem si = new SaveBrandNewItem(item, itemSupplier);
        try {
            if(isValied()) { // Add Validation
                boolean isPlaced = SaveBrandNewItemRepo.saveBrandNewItem(si); // Transaction
                if (isPlaced) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Save Item!").show();
                    clearFields();
                    initialize();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Item Save Unsuccessfully!").show();
                }
            }else{
                new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) { // Update Item
        String itemId = txtItemId.getText();
        String name = txtName.getText();
        String category = txtCategory.getText();
        String brand = txtBrand.getText();
        LocalDate date = txtDate.getValue();
        String warranty = txtWaranty.getText();
        String type = String.valueOf(cmbType.getValue());
        String description = txtDescription.getText();
        String path = image.getUrl();

        var item = new BrandNewItem(itemId, name, category, brand, date, description, warranty, type, path); //Set Item Table Data

        itemId = txtItemId.getText();
        String supId = txtSupplierId.getText();
        int handOnQty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        var itemSupplier = new ItemSupplierDetail(itemId,supId,handOnQty,unitPrice); //Set Item Supplier Table Data

        SaveBrandNewItem si = new SaveBrandNewItem(item, itemSupplier);
        try {
            if(isValied()) { // Add Validation
                boolean isPlaced = SaveBrandNewItemRepo.updateBrandNewItem(si); // Transaction
                if (isPlaced) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Update Item!").show();
                    clearFields();
                    initialize(); // Reload
                } else {
                    new Alert(Alert.AlertType.WARNING, "Item Update Unsuccessfully!").show();
                }
            }else{
                new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtItemId.setText("");
        txtName.setText("");
        txtDescription.setText("");
        txtDate.setValue(null);
        txtQty.setText("");
        txtUnitPrice.setText("");
        txtWaranty.setText("");
        txtBrand.setText("");
        cmbType.setValue(null);
        txtCategory.setText("");
        txtSupplierId.setText("");
        lblSupCompanyName.setText("");
        itemImage.setImage(null);

    }

    public void btnClearOnAction(ActionEvent actionEvent) { // Clear Text Field Data
        clearFields();
    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) { // Delete Item
        String id = txtItemId.getText();

        try {
            boolean isItemDeleted = BrandNewItemRepo.delete(id); //
            if (isItemDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
                clearFields();
                initialize(); // Reload Page
                }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void getType() { // Set Item types
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Brand New");
        obList.add("Used");

        cmbType.setItems(obList);

    }

    private void getBrand() { // Set Item Brands
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Hp");
        obList.add("Msi");
        obList.add("Asus");
        obList.add("Acer");
        obList.add("Toshiba");
        obList.add("Dell");
        obList.add("Lenovo");
        obList.add("Huawei");
        obList.add("Adata");
        obList.add("Corsair");
        obList.add("T-Force");
        obList.add("Cooler Master");
        obList.add("Armaggeddon");
        obList.add("Gamdias");
        obList.add("Fantech");
        obList.add("ProLink");
        obList.add("Jadel");
        obList.add("Logitech");
        obList.add("AMD");
        obList.add("INTEL");

        TextFields.bindAutoCompletion(txtBrand,obList);

    }

    private void getCategory(){ // Set Item Category
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Laptop");
        obList.add("Monitor");
        obList.add("Keyboard & Mouse");
        obList.add("Casing");
        obList.add("Headset & Speaker");
        obList.add("Processor");
        obList.add("Motherboard");
        obList.add("Memory");
        obList.add("Storage");
        obList.add("Graphic Card");
        obList.add("Combo Pack");

        TextFields.bindAutoCompletion(txtCategory,obList);
    }

    @FXML
    void btnAddNewSupplierOnAction(ActionEvent event) throws IOException { // Add New Supplier
        AnchorPane supRootNode = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));
        rootNode.getChildren().clear();
        rootNode.getChildren().add(supRootNode);
    }

    @FXML
    void btnPicImportOnAction() { // Search Image Path in Your PC
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(imgRootNode.getScene().getWindow());

        if (file != null) {
            image = new Image(file.toURI().toString(), 200, 200, false, true);
            itemImage.setImage(image);
        }
    }

    private void getSupplierId() { // Get Supplier ID and Assign Text Field
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = SupplierRepo.getId();

            for(String id : idList) {
                obList.add(id);
            }

            TextFields.bindAutoCompletion(txtSupplierId, obList); //Assign

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtSearchItemNameOnAction(ActionEvent actionEvent) {
        try {
            btnSearchItemNameOnAction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchItemNameOnAction() throws SQLException {
        String name = txtSearchItemName.getText();

        BrandNewItem item = BrandNewItemRepo.searchByName(name); // Get Item name wise Item Details
        ItemSupplierDetail isd = ItemSupplierDetailRepo.searchById(item.getItemId()); // Get Item id wise Item Supplier Details
        Supplier supplier = SupplierRepo.searchById(isd.getSupId()); // Get Item Supplier table Supplier Id Wise Supplier Details

        if (item != null) {
            txtItemId.setText(item.getItemId());
            txtName.setText(item.getName());
            txtDescription.setText(item.getDescription());
            txtDate.setValue(item.getStockDate());
            txtCategory.setText(item.getCategory());
            txtBrand.setText(item.getBrand());
            if(isd != null) {
                txtSupplierId.setText(isd.getSupId());
                txtQty.setText(String.valueOf(isd.getQty()));
                txtUnitPrice.setText(String.valueOf(isd.getUnitPrice()));
            }
            if(supplier != null) {
                lblSupCompany.setText(supplier.getCompanyName());
            }
            cmbType.setValue(item.getType());
            txtWaranty.setText(item.getWarranty());
            image = new Image(item.getPath(), 153, 176, false, true); // Set Image path and size
            itemImage.setImage(image);


        } else {
            new Alert(Alert.AlertType.INFORMATION, "Item not found!").show();
        }
    }
    private void getItemName() { // Get Item Names and Assign Text Field (Sujess Item)

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = BrandNewItemRepo.getName();

            for (String tel : telList) {
                obList.add(tel);
            }
            TextFields.bindAutoCompletion(txtSearchItemName, obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Enter Press Focus Action ----------------------------------------------------------------------------------
    @FXML
    void txtDescriptionOnAction(ActionEvent event) {
        txtSupplierId.requestFocus();
    }

    @FXML
    public void txtItemId(ActionEvent event) {
        txtName.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtCategory.requestFocus();
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        txtUnitPrice.requestFocus();
    }

    @FXML
    void txtUnitPriceOnAction(ActionEvent event) {
        btnPicImportOnAction();
    }

    @FXML
    void txtWarantyOnAction(ActionEvent event) {
        txtQty.requestFocus();
    }

    @FXML
    void cmbtypeOnAction(ActionEvent actionEvent) {
        txtDate.requestFocus();
    }
    public void txtDateOnAction(ActionEvent actionEvent) {
        txtWaranty.requestFocus();
    }

    public void txtCategoryOnAction(ActionEvent actionEvent) {
        txtBrand.requestFocus();
    }

    public void txtBrandOnAction(ActionEvent actionEvent) {
        txtDescription.requestFocus();
    }

    public void txtSupplierIdOnAction(ActionEvent actionEvent) {
        String id = String.valueOf(txtSupplierId.getText());

        Supplier supplier;
        try {
            supplier = SupplierRepo.searchById(id);

            lblSupCompanyName.setText(supplier.getCompanyName()); // Set Supplier Id wise Company Name

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        cmbType.requestFocus();
    }

    //Validation Part ------------------------------------------------------------------------------------------------------------------------------------
    public void txtitemIdOnKeyRelese(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.IID,txtItemId);
    }

    public void txtQtyOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.QTY,txtQty);
    }

    public void txtUnitPriceOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.PRICE,txtUnitPrice);
    }

    public boolean isValied(){
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.IID,txtItemId))return false;
        if (!Regex.setTextColor(lk.ijse.chama.util.TextField.QTY,txtQty)) return false;
        if (!Regex.setTextColor(lk.ijse.chama.util.TextField.PRICE,txtUnitPrice)) return false;
        return true;
    }
}
