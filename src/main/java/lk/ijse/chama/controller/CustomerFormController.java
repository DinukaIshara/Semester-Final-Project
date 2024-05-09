package lk.ijse.chama.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.tm.CartTm;
import lk.ijse.chama.model.tm.CustomerTm;
import lk.ijse.chama.repository.CustomerRepo;
import lk.ijse.chama.util.Regex;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerFormController {

    @FXML
    private TextField txtId;

    @FXML
    private Label lblCostId;

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
    private TextField txtSearch;

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
    private TableColumn<?, ?> colStats;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TextField txtSearchCustomers;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    JFXButton btnRemove = new JFXButton("remo");

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
        getCustomerTel();
        //removeButtonOnAction();

    }

    @FXML
    void txtSearchCustomersOnAction(ActionEvent event) throws SQLException {
        String tel = txtSearchCustomers.getText();

        Customer customer = CustomerRepo.searchById(String.valueOf(tel));
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

    private void getCustomerTel() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = CustomerRepo.getTel();

            for (String tel : telList) {
                obList.add(tel);
            }
            TextFields.bindAutoCompletion(txtSearchCustomers, obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("cAddress"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("cNIC"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("cEmail"));
        //colStats.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    private void loadAllCustomers() {
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
                        //btnRemove
                );

                obList.add(tm);
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        String contact = txtTel.getText();
        String email = txtEmail.getText();

        Customer customer = new Customer(id, name, address, nic, contact , email );

        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        String contact = txtTel.getText();
        String email = txtEmail.getText();

        Customer customer = new Customer(id, name, address, nic, contact , email);

        try {
            boolean isUpdated = CustomerRepo.update(customer);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction() {
        String id = txtId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNIC.setText("");
        txtTel.setText("");
        txtEmail.setText("");
    }

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
}
