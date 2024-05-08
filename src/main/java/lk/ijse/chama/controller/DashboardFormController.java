package lk.ijse.chama.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.*;
import lk.ijse.chama.model.tm.CustomerTm;
import lk.ijse.chama.model.tm.EmployeeTm;
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
        lineChart1();

        try {
            pieChartConnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMostSellItemTable() {
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colOrderCount.setCellValueFactory(new PropertyValueFactory<>("orderCount"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("sumQty"));
    }

    private void loadAll() {
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

    private void loadCustomerCount() {

        int count = 0;
        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer cust : customerList) {
                //System.out.println("dinuk");
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
                //System.out.println("dinuk");
                count ++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        lblOrderCount.setText(String.valueOf(count));
    }

    public void pieChartConnect() throws SQLException {
        //ObservableList<MostSellItemTm> obList = FXCollections.observableArrayList();

        List<MostSellItemTm> itemList = DashboardRepo.getMostSellItem();
        BrandNewItem item;
        for (MostSellItemTm sellItem : itemList) {
            item = BrandNewItemRepo.searchById(sellItem.getItemId());

            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data(item.getName(), sellItem.getSumQty())
                    );
            pieChartData.forEach(data ->
                    data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), " amount: ", data.pieValueProperty()
                            )
                    )
            );

            pieChart.getData().addAll(pieChartData);
        }
    }

    private void getOrderDate() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> dateList = OrderRepo.getAllDate();

            for (String date : dateList) {
                obList.add(date);
            }
            TextFields.bindAutoCompletion(txtOrderDate, obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOrderDateOnAction() throws SQLException {
        Date date = java.sql.Date.valueOf(txtOrderDate.getText());

        DailyOrders dailyOrders = orderDaily(date);

        lblOrderCountlab.setText(String.valueOf(dailyOrders.getCountOr()));
        lblItemQty.setText(String.valueOf(dailyOrders.getCountQty()));
    }

    public void lineChart1(){
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Chama Computers");

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

        PreparedStatement stm = null;
        try {
            stm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rst = null;
        try {
            rst = stm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (!rst.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String date = null;
            try {
                date = rst.getString(2);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int count = 0;
            try {
                count = rst.getInt(4);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            series1.getData().add(new XYChart.Data<>(date, count));
        }
        barChart.getData().addAll(series1);
    }

    public void txtOrderDateOnAction(ActionEvent actionEvent) throws SQLException {
        btnSearchOrderDateOnAction();
    }
}
