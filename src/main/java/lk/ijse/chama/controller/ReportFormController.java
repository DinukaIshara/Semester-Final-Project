package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.repository.BrandNewItemRepo;
import lk.ijse.chama.repository.CustomerRepo;
import lk.ijse.chama.repository.EmployeeRepo;
import lk.ijse.chama.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportFormController {

    @FXML
    private TextField txtSearchItemStockDate;
    @FXML
    private TextField txtSearchCustomerTel;

    public void initialize(){
        getCustomerTel();
        getItemDate();
    }

    @FXML
    void btnCustomerReportOnAction() throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/CustomerReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        if(isValidateNum()) {
            data.put("custTel", txtSearchCustomerTel.getText());
        }else{
            new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
        }


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    @FXML
    void btnStockOnAction() throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/supplierDateVise.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        if(isValidateDate()) {
            data.put("date", txtSearchItemStockDate.getText());
        }else{
            new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
        }

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

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
    private void getItemDate() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> dateList = BrandNewItemRepo.getDate();

            for (String date : dateList) {
                obList.add(date);
            }
            TextFields.bindAutoCompletion(txtSearchItemStockDate,obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtCustTelOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.PHONENO,txtSearchCustomerTel);
    }

    public void txtStockDateOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.DATE,txtSearchItemStockDate);
    }

    public boolean isValidateNum(){
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.PHONENO,txtSearchCustomerTel))return false;

        return true;
    }

    public boolean isValidateDate(){
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.DATE,txtSearchItemStockDate))return false;

        return true;
    }
}
