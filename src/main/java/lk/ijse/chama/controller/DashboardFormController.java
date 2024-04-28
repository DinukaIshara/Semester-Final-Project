package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.Employee;
import lk.ijse.chama.model.tm.EmployeeTm;
import lk.ijse.chama.repository.CustomerRepo;
import lk.ijse.chama.repository.EmployeeRepo;

import java.sql.SQLException;
import java.util.List;

public class DashboardFormController {
    public Label labCutCount;
    @FXML
    private AnchorPane rootNode;

    public void initialize() {
        loadCustomerCount();
    }

    private void loadCustomerCount() {

        int count = 0;
        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer cust : customerList) {
                System.out.println("dinuk");
                count ++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        labCutCount.setText(String.valueOf(count));
    }

}
