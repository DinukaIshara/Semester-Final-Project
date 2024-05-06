package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.chama.model.*;
import lk.ijse.chama.model.tm.RepairTm;
import lk.ijse.chama.repository.CustomerRepo;
import lk.ijse.chama.repository.RepairRepo;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class RepairFormController {

    @FXML
    private TextField txtCustomerTel;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colReceiveDate;

    @FXML
    private TableColumn<?, ?> colRepairId;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colReturncolDescriptionedDescription;

    @FXML
    private Label lblCustomerName;

    @FXML
    private TableView<Repair> tblReturnedRepair;

    @FXML
    private TextField txtCost;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemName;

    @FXML
    private DatePicker txtReceiveDate;

    @FXML
    private TextField txtRepairId;

    @FXML
    private DatePicker txtReturnDate;

    public void initialize() {
        getCustomerTel();
        loadAllRepair();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colRepairId.setCellValueFactory(new PropertyValueFactory<>("repId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colReturncolDescriptionedDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colReceiveDate.setCellValueFactory(new PropertyValueFactory<>("reciveDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("reternDate"));
    }

    private void loadAllRepair() {
        ObservableList<Repair> obList = FXCollections.observableArrayList();

        try {
            List<Repair> repairList = RepairRepo.getAll();
            for (Repair repair : repairList) {
                RepairTm tm = new RepairTm(
                        repair.getRepairId(),
                        repair.getItemName(),
                        repair.getDescription(),
                        repair.getCustId(),
                        repair.getCost(),
                        repair.getReciveDate(),
                        repair.getReturnDate()
                );

                obList.add(tm);
            }

            tblReturnedRepair.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCustomerTel() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = CustomerRepo.getTel();

            for(String tel : telList) {
                obList.add(tel);
            }
            TextFields.bindAutoCompletion(txtCustomerTel,obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String repairId = txtRepairId.getText();
        Date reciveDate = java.sql.Date.valueOf(txtReceiveDate.getValue());
        Date returnDate = java.sql.Date.valueOf(txtReturnDate.getValue());
        double cost = Double.parseDouble(txtCost.getText());
        String description = txtDescription.getText();
        String custId = lblCustomerId.getText();
        String itemName = txtItemName.getText();
        System.out.println(lblCustomerId.getText());

        var repair = new Repair(repairId, reciveDate, returnDate, cost, description, custId, itemName);

        try {
            boolean isPlaced = RepairRepo.save(repair);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Save Repair!").show();
                clearFields();
                initialize();
            } else {
                new Alert(Alert.AlertType.WARNING, "Repair Save Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String repairId = txtRepairId.getText();
        Date reciveDate = java.sql.Date.valueOf(txtReceiveDate.getValue());
        Date returnDate = java.sql.Date.valueOf(txtReturnDate.getValue());
        double cost = Double.parseDouble(txtCost.getText());
        String description = txtDescription.getText();
        String custId = lblCustomerId.getText();
        String itemName = txtItemName.getText();

        var repair = new Repair(repairId, reciveDate, returnDate, cost, description, custId, itemName);

        try {
            boolean isPlaced = RepairRepo.update(repair);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Save Repair!").show();
                initialize();
            } else {
                new Alert(Alert.AlertType.WARNING, "Repair Save Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void checkCustomerIsSelect(MouseEvent event) {

    }

    @FXML
    void dpReceiveDateOnAction(ActionEvent event) {

    }

    @FXML
    void dpReturnDateOnAction(ActionEvent event) {

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        txtCost.setText("");
        txtRepairId.setText("");
        txtDescription.setText("");
        txtItemName.setText("");
        txtReceiveDate.setValue(null);
        txtReturnDate.setValue(null);
        txtCustomerTel.setText("");
        lblCustomerId.setText("");
        lblCustomerName.setText("");

    }

    public void txtCustomerTelOnAction(ActionEvent actionEvent) {
        String tel = txtCustomerTel.getText();
        try {
            Customer customer = CustomerRepo.searchById(tel);

            lblCustomerName.setText(customer.getCName());
            lblCustomerId.setText(customer.getCustId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
