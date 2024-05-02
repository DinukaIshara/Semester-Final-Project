package lk.ijse.chama.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.model.*;
import lk.ijse.chama.model.tm.CartTm;
import lk.ijse.chama.repository.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PPlaceOrderFormController {

    @FXML
    private Label lblLocation;

    @FXML
    private JFXComboBox cmbPaymentMethod;

    @FXML
    private TableColumn colTotal;

    @FXML
    private JFXComboBox cmbCustomerTel;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXComboBox<String> cmbItemName;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblCustName;

    @FXML
    private Label lblCustName1;

    @FXML
    private Label lblModel;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderCode;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane menuRootNode;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CartTm> tblOrder;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtQty;

    @FXML
    private Label lblOrderTime;

    @FXML
    private JFXComboBox<String> cmbTransportId;

    @FXML
    private Label lblChange;

    @FXML
    private Label lblTransportCost;

    @FXML
    private Label lblCustomerId;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        setDateAndTime();
        getCurrentOrderId();
        getCustomerTel();
        getItemName();
        setCellValueFactory();
        getTransportId();
        getPMethod();
    }

    private void setCellValueFactory() {
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane customerRootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));
        rootNode.getChildren().clear();
        rootNode.getChildren().add(customerRootNode);

    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = cmbItemName.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;
        JFXButton btnRemove = new JFXButton("remo");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrder.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblOrder.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            if(code.equals(colItemName.getCellData(i))) {

                CartTm tm = obList.get(i);
                qty += tm.getQty();
                total = qty * unitPrice;

                tm.setQty(qty);
                tm.setTotal(total);

                tblOrder.refresh();

                calculateNetTotal();
                return;
            }
        }

        CartTm tm = new CartTm(code, qty, unitPrice, total, btnRemove);
        obList.add(tm);

        tblOrder.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");

    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderCode.getText();
        String cusId = lblCustomerId.getText();
        String transId = String.valueOf(cmbTransportId.getValue());
        Date date = Date.valueOf(LocalDate.now());
        String payment = String.valueOf(cmbPaymentMethod.getValue());

        var order = new Order(orderId, cusId, transId, date, payment);

        List<OrderDetail> odList = new ArrayList<>();

        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            CartTm tm = obList.get(i);

            OrderDetail od = new OrderDetail(
                    orderId,
                    tm.getItemName(),
                    tm.getQty(),
                    tm.getUnitPrice()
            );

            odList.add(od);
        }

        PlaceOrder po = new PlaceOrder(order, odList);
        try {
            boolean isPlaced = PlaceOrderRepo.placeOrder(po);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbCustomerTelOnAction(ActionEvent event) {
        String tel = String.valueOf(cmbCustomerTel.getValue());
        try {
            Customer customer = CustomerRepo.searchById(tel);

            lblCustName.setText(customer.getCName());
            lblCustomerId.setText(customer.getCustId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemNameOnAction(ActionEvent event) {
        String name = cmbItemName.getValue();

        try {
            BrandNewItem item = BrandNewItemRepo.searchById(name);
            ItemSupplierDetail itemDetail = ItemSupplierDetailRepo.searchById(name);
            if(item != null) {
                lblModel.setText(item.getCategory());
                lblUnitPrice.setText(String.valueOf(itemDetail.getUnitPrice()));
                lblQtyOnHand.setText(String.valueOf(itemDetail.getQty()));
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbPaymentMethod(ActionEvent event) {

    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {

    }

    private void setDateAndTime() {
        LocalDate nowDate = LocalDate.now();
        lblOrderDate.setText(String.valueOf(nowDate));

        LocalTime nowTime = LocalTime.now();
        lblOrderTime.setText(String.valueOf(nowTime));
    }

    private void getItemName() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = BrandNewItemRepo.getCode();

            for (String code : codeList) {
                obList.add(code);
            }
            cmbItemName.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCustomerTel() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = CustomerRepo.getTel();

            for(String tel : telList) {
                obList.add(tel);
            }

            cmbCustomerTel.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentOrderId() {
        try {
            String currentId = OrderRepo.getCurrentId();

            String nextOrderId = generateNextOrderId(currentId);
            lblOrderCode.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "O" + ++idNum;
        }
        return "O1";
    }

    private void getTransportId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = TransportRepo.getTel();

            for(String tel : telList) {
                obList.add(tel);
            }

            cmbTransportId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPMethod() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Cash");
        obList.add("Card");

        cmbPaymentMethod.setItems(obList);

    }

    @FXML
    void cmbTransportIdOnAction(ActionEvent actionEvent) {
        String id = cmbTransportId.getValue();

        try {
            Transport tr = TransportRepo.searchById(id);
            if(tr != null) {
                lblLocation.setText(tr.getLocation());
                lblTransportCost.setText(String.valueOf(tr.getCost()));

            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtAmountOnAction(ActionEvent actionEvent) {

    }
}
