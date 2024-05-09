package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.repository.BrandNewItemRepo;
import lk.ijse.chama.repository.CustomerRepo;
import lk.ijse.chama.repository.EmployeeRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportFormController {
    @FXML
    private TextField txtSearchCustomerTel;

    @FXML
    private TextField txtSearchEmployeeId;

    @FXML
    private TextField txtSearchItemName;

    public void initialize(){
        getCustomerTel();
        getEmpId();
        getItemName();
    }

    @FXML
    void btnCustomerReportOnAction() throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/CustomerReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("custTel",txtSearchCustomerTel.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    @FXML
    void btnEmployeeReportOnAction() {

    }

    @FXML
    void btnStockOnAction() {

    }

    public void txtSearchItemNameOnAction(javafx.event.ActionEvent actionEvent) {
        btnStockOnAction();
    }

    public void txtSearchCustomerTelOnAction(javafx.event.ActionEvent actionEvent) {
        //btnCustomerReportOnAction();
    }

    public void txtSearchEmployeeIdOnAction(javafx.event.ActionEvent actionEvent) {
        btnEmployeeReportOnAction();
    }

    private void getCustomerTel() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = CustomerRepo.getTel();

            for(String tel : telList) {
                obList.add(tel);
            }
            TextFields.bindAutoCompletion(txtSearchCustomerTel,obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getItemName() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = BrandNewItemRepo.getName();

            for (String code : codeList) {
                obList.add(code);
            }
            TextFields.bindAutoCompletion(txtSearchItemName,obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getEmpId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = EmployeeRepo.getId();

            for(String tel : telList) {
                obList.add(tel);
            }
            TextFields.bindAutoCompletion(txtSearchEmployeeId,obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
