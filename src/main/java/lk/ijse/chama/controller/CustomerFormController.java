package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.tm.CustomerTm;
import lk.ijse.chama.repository.CustomerRepo;
import lk.ijse.chama.util.Regex;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

    @FXML
    private TextField txtId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TextField txtSearchCustomers;


    public void initialize() { // The method that is called first when the page is loaded
        setCellValueFactory();
        loadAllCustomers();
        getCustomerTel();

    }

    private void setCellValueFactory() {  // Set CustomerTm Data in column
        colId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("cAddress"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("cNIC"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("cEmail"));
    }

    private void loadAllCustomers() { // Load All Customers In CustomerTM Table
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer customer : customerList) {
                CustomerTm tm = new CustomerTm(
                        customer.getCustId(),
                        customer.getCName(),
                        customer.getCAddress(),
                        customer.getCNIC(),
                        customer.getContactNo(),
                        customer.getCEmail()
                );

                obList.add(tm);
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) { // Save Customer
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        String contact = txtTel.getText();
        String email = txtEmail.getText();

        Customer customer = new Customer(id, name, address, nic, contact , email ); // Set Customer Data

        try {
            if(isValidate()) { // Add Validation
                boolean isSaved = CustomerRepo.save(customer);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                    initialize();
                    clearFields();
                }
            }else{
                new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) { // Update Customer
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        String contact = txtTel.getText();
        String email = txtEmail.getText();

        Customer customer = new Customer(id, name, address, nic, contact , email); // Set Customer Data

        try {
            if(isValidate()){ // Add Validation
                boolean isUpdated = CustomerRepo.update(customer);
                if(isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                    initialize();
                    clearFields();
                }
            }else{
                new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction() { // Delete Customers
        String id = txtId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNIC.setText("");
        txtTel.setText("");
        txtEmail.setText("");
        txtSearchCustomers.setText("");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void txtSearchCustomersOnAction(ActionEvent event) throws SQLException {
        btnSearchCustomersOnAction();
    }

    private void getCustomerTel() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = CustomerRepo.getTel();

            for (String tel : telList) {
                obList.add(tel);
            }

            TextFields.bindAutoCompletion(txtSearchCustomers, obList); // Set Data List in Text Field

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchCustomersOnAction() throws SQLException { // Search Customers
        String tel = txtSearchCustomers.getText();

        Customer customer = CustomerRepo.searchByTel(String.valueOf(tel));
        if (customer != null) {
            txtId.setText(customer.getCustId());
            txtName.setText(customer.getCName());
            txtNIC.setText(customer.getCNIC());
            txtAddress.setText(customer.getCAddress());
            txtEmail.setText(customer.getCEmail());
            txtTel.setText(customer.getContactNo());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }
    }

    // Focus Actions -------------------------------------------------------
    @FXML
    void nameOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void addressOnAction(ActionEvent event) {
        txtNIC.requestFocus();
    }

    @FXML
    void nicOnAction(ActionEvent event) {
        txtTel.requestFocus();
    }

    @FXML
    void telOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    public void txtIdOnAction(ActionEvent actionEvent) {
        txtName.requestFocus();
    }


    // Validation---------------------------------------------------------------------------------------------------------------------------------
    public void txtCustIdOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.CID,txtId);
    }

    public void txtNicOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.NIC,txtNIC);
    }

    public void txtTelOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.PHONENO,txtTel);
    }

    public void txtAddressOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.ADDRESS,txtAddress);
    }

    public void txtEmailOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.EMAIL,txtEmail);
    }

    public boolean isValidate(){
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.CID,txtId)) return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.NIC,txtNIC)) return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.ADDRESS,txtAddress))return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.PHONENO,txtTel)) return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.EMAIL,txtEmail)) return false;

        return true;
    }

}
