package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.Supplier;
import lk.ijse.chama.model.tm.CustomerTm;
import lk.ijse.chama.model.tm.SupplierTm;
import lk.ijse.chama.repository.CustomerRepo;
import lk.ijse.chama.repository.SupplierRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierFormController {

    @FXML
    private TextField txtSearchSupplier;

    @FXML
    private TableColumn<?, ?> colCompanyName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colPersonName;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> coltel;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtCompanyName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtLoacation;

    @FXML
    private TextField txtPersonName;

    @FXML
    private TextField txtSupId;

    public void initialize() {
        setCellValueFactory();
        loadAllSuppliers();
        supplierCompanyName();
    }

    private void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colPersonName.setCellValueFactory(new PropertyValueFactory<>("personName"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadAllSuppliers() {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<Supplier> supplierList = SupplierRepo.getAll();
            for (Supplier supplier : supplierList) {
                SupplierTm tm = new SupplierTm(
                        supplier.getSupId(),
                        supplier.getCompanyName(),
                        supplier.getPersonName(),
                        supplier.getTel(),
                        supplier.getLocation(),
                        supplier.getEmail()
                );

                obList.add(tm);
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String supId = txtSupId.getText();
        String companyName = txtCompanyName.getText();
        String personName = txtPersonName.getText();
        String tel = txtContact.getText();
        String location = txtLoacation.getText();
        String email = txtEmail.getText();

        Supplier suppler = new Supplier(supId, companyName, personName, tel, location , email );

        try {
            boolean isSaved = SupplierRepo.save(suppler);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtSupId.setText("");
        txtCompanyName.setText("");
        txtPersonName.setText("");
        txtContact.setText("");
        txtLoacation.setText("");
        txtEmail.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String supId = txtSupId.getText();
        String companyName = txtCompanyName.getText();
        String personName = txtPersonName.getText();
        String tel = txtContact.getText();
        String location = txtLoacation.getText();
        String email = txtEmail.getText();

        Supplier suppler = new Supplier(supId, companyName, personName, tel, location , email );

        try {
            boolean isSaved = SupplierRepo.update(suppler);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier update!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtSupId.getText();

        try {
            boolean isDeleted = SupplierRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    @FXML
    void btnSupplierReportOnAction(ActionEvent event) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/SupplerItemReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("supId",txtSupId.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    public void supplierCompanyName() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> nameList = SupplierRepo.getName();

            for (String name : nameList) {
                obList.add(name);
            }
            TextFields.bindAutoCompletion(txtSearchSupplier, obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtSearchSupplierOnAction(ActionEvent actionEvent) throws SQLException {
        String name = txtSearchSupplier.getText();

        Supplier supplier = SupplierRepo.searchByName(name);
        if (supplier != null) {
            txtSupId.setText(supplier.getSupId());
            txtCompanyName.setText(supplier.getCompanyName());
            txtPersonName.setText(supplier.getPersonName());
            txtLoacation.setText(supplier.getLocation());
            txtEmail.setText(supplier.getEmail());
            txtContact.setText(supplier.getTel());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }
    }
}
