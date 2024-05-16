package lk.ijse.chama.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.*;
import lk.ijse.chama.model.tm.MostSellItemTm;
import lk.ijse.chama.repository.*;
import org.controlsfx.control.textfield.TextFields;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static lk.ijse.chama.repository.DashboardRepo.orderDaily;

public class DashboardFormController {

    public TableView tblMostSellItems;
    @FXML
    private Label labCutCount;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblOrderCount;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colOrderCount;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private TextField txtOrderDate;

    @FXML
    private Label lblItemQty;

    @FXML
    private Label lblOrderCountlab;

    public void initialize() {
        loadCustomerCount();
        loadOrderCount();
        loadMostSellItemTable();
        loadAll();
        getOrderDate();

        try {
            barChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            pieChartConnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMostSellItemTable() { // Set Most Top 5 Sell Items in column
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colOrderCount.setCellValueFactory(new PropertyValueFactory<>("orderCount"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("sumQty"));
    }

    private void loadAll() { // Load Most Top 5 Sell Items in Most Sell Item TM
        ObservableList<MostSellItemTm> obList = FXCollections.observableArrayList();

        try {
            List<MostSellItemTm> itemList = DashboardRepo.getMostSellItem();
            BrandNewItem item;
            for (MostSellItemTm sellItem : itemList) {
                item = BrandNewItemRepo.searchById(sellItem.getItemId());
                MostSellItemTm tm = new MostSellItemTm(
                        item.getName(),
                        sellItem.getOrderCount(),
                        sellItem.getSumQty()
                );

                obList.add(tm);
            }

            tblMostSellItems.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerCount() { // Check Customer Count
        int count = 0;
        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer cust : customerList) {

                count ++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        labCutCount.setText(String.valueOf(count)); // Set Customer Count
    }

    private void loadOrderCount() { // Chech Order Count
        int count = 0;
        try {
            List<Order> orderList = OrderRepo.getAll();
            for (Order order : orderList) {

                count ++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        lblOrderCount.setText(String.valueOf(count)); // Set Order Count
    }

    // Pie Chart --------------------------------------------------------------------------------------------------------------------------------------------------
    public void pieChartConnect() throws SQLException {
        List<MostSellItemTm> itemList = DashboardRepo.getMostSellItem(); //Load Most sell Item In MostSellItemTm
        BrandNewItem item;
        for (MostSellItemTm sellItem : itemList) {
            item = BrandNewItemRepo.searchById(sellItem.getItemId()); // Item Id wise Search Items

            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data(item.getName(), sellItem.getSumQty()) // Set Most 5 Items In Pie Chart
                    );
            pieChartData.forEach(data ->
                    data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), "amount: ", data.pieValueProperty() // Set Labels
                            )
                    )
            );

            pieChart.getData().addAll(pieChartData); // Add Data In Pie Chart and Show it
        }
    }

    // Bar Chart -----------------------------------------------------------------------------------------------------------------------------------------------------
    public void barChart() throws SQLException {
        XYChart.Series chart = new XYChart.Series();
        chart.setName("Chama Computers");

        String sql = "SELECT\n" +
                "    DATE_FORMAT(MIN(o.order_date), '%Y-%m-%d') AS WeekStartDate,\n" +
                "    DATE_FORMAT(MAX(o.order_date), '%Y-%m-%d') AS WeekEndDate,\n" +
                "    COUNT(DISTINCT o.order_id) AS WeeklyOrders,\n" +
                "    SUM(od.qty * od.unit_price) AS TotalAmount\n" +
                "FROM\n" +
                "    orders o\n" +
                "JOIN \n" +
                "    order_detail od ON o.order_id = od.order_id\n" +
                "WHERE\n" +
                "    o.order_date BETWEEN (SELECT MIN(order_date) FROM orders) AND (SELECT MAX(order_date) FROM orders)\n" +
                "GROUP BY\n" +
                "    YEARWEEK(o.order_date, 1)\n" +
                "ORDER BY\n" +
                "    WeekStartDate;";

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet rst  = stm.executeQuery();

        while (true) {
            if (!rst.next()) break;

            String date = rst.getString(2);

            int count  = rst.getInt(4);
            chart.getData().add(new XYChart.Data<>(date, count));
        }
        barChart.getData().addAll(chart);
    }

    private void getOrderDate() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> dateList = OrderRepo.getAllDate();

            for (String date : dateList) {
                obList.add(date);
            }
            TextFields.bindAutoCompletion(txtOrderDate, obList); // Set Order dates In Text Field

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOrderDateOnAction() throws SQLException {
        Date date = java.sql.Date.valueOf(txtOrderDate.getText());

        DailyOrders dailyOrders = orderDaily(date); // Check Date and get Daily Orders Details

        lblOrderCountlab.setText(String.valueOf(dailyOrders.getCountOr())); // Set Order Count For Search Date
        lblItemQty.setText(String.valueOf(dailyOrders.getCountQty())); // Set Order Item Qty Fro  Search Date
    }

    public void txtOrderDateOnAction(ActionEvent actionEvent) throws SQLException {
        btnSearchOrderDateOnAction();
    }
}
