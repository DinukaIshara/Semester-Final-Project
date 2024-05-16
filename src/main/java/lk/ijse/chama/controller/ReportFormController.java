package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
        data.put("custTel",txtSearchCustomerTel.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    @FXML
    void btnStockOnAction() throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/supplierDateVise.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("date",txtSearchItemStockDate.getText());

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
}
