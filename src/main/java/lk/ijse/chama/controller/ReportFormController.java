package lk.ijse.chama.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class ReportFormController {
    @FXML
    private TextField txtSearchCustomerTel;

    @FXML
    private TextField txtSearchEmployeeId;

    @FXML
    private TextField txtSearchItemName;

    @FXML
    void btnCustomerReportOnAction() {
        System.out.println("Customer");

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
        btnCustomerReportOnAction();
    }

    public void txtSearchEmployeeIdOnAction(javafx.event.ActionEvent actionEvent) {
        btnEmployeeReportOnAction();
    }
}
