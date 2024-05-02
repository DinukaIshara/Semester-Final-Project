package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.Employee;
import lk.ijse.chama.model.Order;
import lk.ijse.chama.model.tm.EmployeeTm;
import lk.ijse.chama.repository.CustomerRepo;
import lk.ijse.chama.repository.EmployeeRepo;
import lk.ijse.chama.repository.OrderRepo;

import java.sql.SQLException;
import java.util.List;

public class DashboardFormController {

    @FXML
    private Label labCutCount;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblOrderCount;

    public void initialize() {
        loadCustomerCount();
        loadOrderCount();
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

    private void loadOrderCount() {

        int count = 0;
        try {
            List<Order> orderList = OrderRepo.getAll();
            for (Order order : orderList) {
                System.out.println("dinuk");
                count ++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        lblOrderCount.setText(String.valueOf(count));
    }

}
