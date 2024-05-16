package lk.ijse.chama.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.Employee;
import lk.ijse.chama.model.tm.EmployeeTm;
import lk.ijse.chama.repository.EmployeeRepo;
import lk.ijse.chama.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeFormController {

    @FXML
    private ImageView ImgView;

    @FXML
    private Pane main_pain;

    @FXML
    private TextField txtSearchEmployee;

    @FXML
    private TextField txtSallary;

    @FXML
    private TableColumn<?, ?> colEid;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colDOR;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private TableColumn<?, ?> colTel;
    @FXML
    public DatePicker txtEnrollDate;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<EmployeeTm> tblCustomer;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtAddress;

    @FXML
    private DatePicker txtDOB;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPosition;

    @FXML
    private TextField txtTel;

    private Image image;

    public void initialize() {
        setCellValueFactory();
        loadAllEmployee();
        getEmpId();
    }

    private void setCellValueFactory() { // Set EmployeeTm Data in column
        colEid.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("empNic"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("empTel"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("empEmail"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colDOR.setCellValueFactory(new PropertyValueFactory<>("dateRegister"));
    }

    private void loadAllEmployee() { // Load All Employees In EmployeeTm Table
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<Employee> employeeList = EmployeeRepo.getAll();
            for (Employee employee : employeeList) {
                EmployeeTm tm = new EmployeeTm(
                        employee.getEmpId(),
                        employee.getEmpName(),
                        employee.getEmpNic(),
                        employee.getPosition(),
                        employee.getEmpTel(),
                        employee.getDob(),
                        employee.getDateRegister(),
                        employee.getEmpEmail(),
                        employee.getSalary()
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
        String position = txtPosition.getText();
        String contact = txtTel.getText();
        Date dob = Date.valueOf(txtDOB.getValue());
        Date dateRegistration = Date.valueOf(txtEnrollDate.getValue());
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSallary.getText());
        String path = image.getUrl();

        Employee employee = new Employee(id, name, address, nic, position, contact , dob, dateRegistration, email, salary, path); // Set Employee Data

        try {
            if(isValidate()) {
                boolean isSaved = EmployeeRepo.save(employee);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                    clearFields();
                    initialize();
                }
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
        String position = txtPosition.getText();
        String contact = txtTel.getText();
        Date dob = Date.valueOf(txtDOB.getValue());
        Date dateRegistration = Date.valueOf(txtEnrollDate.getValue());
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSallary.getText());
        String path = image.getUrl();

        Employee employee = new Employee(id, name, address, nic, position, contact , dob, dateRegistration, email, salary, path); // Set Employee Data

        try {
            if(isValidate()) { // Validated
                boolean isUpdated = EmployeeRepo.update(employee);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
                    initialize();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            if(isValidate()) { // Validated
                boolean isDeleted = EmployeeRepo.delete(id); // Delete Employee Data
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
                    initialize();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) { // Clear Text Field Data
        clearFields();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNIC.setText("");
        txtPosition.setText("");
        txtTel.setText("");
        txtDOB.setValue(null);
        txtEnrollDate.setValue(null);
        txtEmail.setText("");
        txtSallary.setText("");
    }

    public void btnImportImgOnAction() { // Search Image Path in Your PC
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(main_pain.getScene().getWindow());

        if (file != null) {
            image = new Image(file.toURI().toString(), 153, 176, false, true);
            ImgView.setImage(image);
        }
    }

    private void getEmpId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = EmployeeRepo.getId();

            for(String tel : telList) {
                obList.add(tel);
            }
            TextFields.bindAutoCompletion(txtSearchEmployee,obList); // Load Employee Ids In Text Field

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtSearchEmployeeOnAction(ActionEvent actionEvent) { // Search Employee Button Call
        try {
            btnSearchEmployeeOnAction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchEmployeeOnAction() throws SQLException { // Search Employees
        String tel = txtSearchEmployee.getText();

        Employee emp = EmployeeRepo.searchById(String.valueOf(tel)); // Search Employees In Employee Id
        if (emp != null) {
            txtId.setText(emp.getEmpId());
            txtName.setText(emp.getEmpName());
            txtAddress.setText(emp.getEmpAddress());
            txtNIC.setText(emp.getEmpNic());
            txtPosition.setText(emp.getPosition());
            txtTel.setText(emp.getEmpTel());
            txtDOB.setValue(emp.getDob().toLocalDate());
            txtEnrollDate.setValue(emp.getDateRegister().toLocalDate());
            txtEmail.setText(emp.getEmpEmail());
            txtSallary.setText(String.valueOf(emp.getSalary()));
            image = new Image(emp.getPath(), 153, 176, false, true);
            ImgView.setImage(image);

        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }
    }

    @FXML
    void addressOnAction(ActionEvent event) {
        txtNIC.requestFocus();
    }

    @FXML
    void nameOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void nicOnAction(ActionEvent event) {
        txtTel.requestFocus();
    }

    @FXML
    void telOnAction(ActionEvent event) {
        txtDOB.requestFocus();
    }

    public void txtPositionOnAction(ActionEvent actionEvent) {
        btnImportImgOnAction();

    }

    public void txtSalOnAction(ActionEvent actionEvent) {
        txtPosition.requestFocus();
    }

    public void txtIdOnAction(ActionEvent actionEvent) {
        txtName.requestFocus();
    }

    public void txtDOBOnAction(ActionEvent actionEvent) {
        txtEnrollDate.requestFocus();
    }

    public void txtRegDateOnAction(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }

    public void txtEmailOnAction(ActionEvent actionEvent) {
        txtSallary.requestFocus();
    }

    // Validation Part
    public void txtAddressOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.ADDRESS,txtAddress);
    }

    public void txtTelOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.PHONENO,txtTel);
    }

    public void txtEmailOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.EMAIL,txtEmail);
    }

    public void txtEmpIdOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.EID,txtId);
    }

    public void txtNicOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.NIC,txtNIC);
    }

    public void txtSalOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.PRICE,txtSallary);
    }

    public boolean isValidate(){
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.EID,txtId))return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.NIC,txtNIC))return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.ADDRESS,txtAddress))return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.PHONENO,txtTel))return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.EMAIL,txtEmail))return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.PRICE,txtSallary))return false;

        return true;
    }

    // Employee Report Generate ----------------------------------------------------------------------------
    public void btnEmployeeReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/EmployeeReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("emp_id",txtId.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
}
