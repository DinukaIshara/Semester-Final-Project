package lk.ijse.chama.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lk.ijse.chama.MyListener;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.model.*;
import lk.ijse.chama.model.tm.CartTm;
import lk.ijse.chama.repository.*;
import lk.ijse.chama.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PlaceOrderFormController {

    public AnchorPane addToCartItemRootNode;
    public Label lblItemName;
    public Label lblItemId;
    public Label lblCatagory;
    public Label lblHandOnQty;
    public ImageView imageCart;
    public TextField txtItemNameSearch;
    public JFXButton btnOrderReceipt;
    public TextField txtLocation;
    public Label lblTrId;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField txtCustomerTel;

    @FXML
    private JFXComboBox cmbPaymentMethod;

    @FXML
    private TableColumn colTotal;

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
    private Label lblNetTotal;

    @FXML
    private Label lblOrderCode;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane menuRootNode;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CartTm> tblOrder;

    @FXML
    private TextField txtQty;

    @FXML
    private Label lblOrderTime;

    @FXML
    private Label lblTransportCost;

    @FXML
    private Label lblCustomerId;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    private List<ProductCard> productCards = new ArrayList<>();

    ProductCard pc = null;

    MyListener myListener;

    List<BrandNewItem> itemList = BrandNewItemRepo.getAll();

    public void initialize() {
        setDateAndTime();
        getCurrentOrderId();
        getCustomerTel();
        getItemName();
        setCellValueFactory();
        getTransportLocation();
        getPMethod();
        addAl();
    }

    private void setCellValueFactory() {
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private List<ProductCard> getData(){

        try {
            //List<BrandNewItem> itemList = BrandNewItemRepo.getAll();
            List<ItemSupplierDetail> supplierDetail = ItemSupplierDetailRepo.getAll();
            for(ItemSupplierDetail itemSupplier : supplierDetail) {

                String item_name = null;
                String item_cat = null;
                String path = null;


                for (BrandNewItem item : itemList){
                    if (item.getItemId().equals(itemSupplier.getItemId())){
                        item_name =   item.getName();
                        item_cat = item.getCategory();
                        path = item.getPath();
                    }
                }


                pc = new ProductCard(

                        itemSupplier.getItemId(),
                        item_name,
                        itemSupplier.getUnitPrice(),
                        itemSupplier.getQty(),
                        item_cat,
                        path

                );
                productCards.add(pc);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productCards;

    }

    private void addAl(){
        productCards.addAll(getData());

        if (productCards.size() > 0){
            setChosenItem(productCards.get(0));

            myListener = new MyListener() {
                @Override
                public void onClickListener(ProductCard productCard) {
                    setChosenItem(productCard);
                }
            };
        }

        int col = 0;
        int row = 1;

        try {
            for (int i = 0; i < itemList.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/productCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                ItemCardController productCardController = fxmlLoader.getController();
                productCardController.setData(productCards.get(i), myListener);

                if (col == 2){
                    col = 0;
                    row++;
                }

                gridPane.add(anchorPane, col++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddToCartOnAction() {
        String code = lblItemName.getText();
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

    private void setChosenItem(ProductCard productCard) {
        lblItemName.setText(productCard.getItemName());
        lblCatagory.setText(productCard.getCategory());
        lblUnitPrice.setText(String.valueOf(productCard.getPrice()));
        lblItemId.setText(productCard.getItemId());
        lblHandOnQty.setText(String.valueOf(productCard.getHandOnQty()));
        Image image = new Image(productCard.getImage(),281, 178, false, true);
        imageCart.setImage(image);

    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane customerRootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));
        rootNode.getChildren().clear();
        rootNode.getChildren().add(customerRootNode);

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        String orderId = lblOrderCode.getText();
        String cusId = lblCustomerId.getText();
        String transId = String.valueOf(lblTrId.getText());
        Date date = Date.valueOf(LocalDate.now());
        String payment = String.valueOf(cmbPaymentMethod.getValue());

        var order = new Order(orderId, cusId, transId, date, payment);

        List<OrderDetail> odList = new ArrayList<>();
        BrandNewItem item;

        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            CartTm tm = obList.get(i);
            item = BrandNewItemRepo.searchByName(tm.getItemName());

            OrderDetail od = new OrderDetail(
                    orderId,
                    item.getItemId(),
                    tm.getQty(),
                    tm.getUnitPrice()
            );

            odList.add(od);
        }

        PlaceOrder po = new PlaceOrder(order, odList);
        try {
            boolean isPlaced = PlaceOrderRepo.placeOrder(po);
            if(isPlaced) {
                getLastOrderId();
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                clear();
            } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbPaymentMethod(ActionEvent event) {

    }

    private void getItemName() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = BrandNewItemRepo.getName();

            for (String code : codeList) {
                obList.add(code);
            }
            TextFields.bindAutoCompletion(txtItemNameSearch,obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction();
    }

    private void setDateAndTime() {
        LocalDate nowDate = LocalDate.now();
        lblOrderDate.setText(String.valueOf(nowDate));

        Time nowTime = Time.valueOf(LocalTime.now());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm");
        String time = simpleDateFormat1.format(nowTime);
        lblOrderTime.setText(time);
    }

    private void getCustomerTel() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = CustomerRepo.getTel();

            for(String tel : telList) {
                obList.add(tel);
            }
            TextFields.bindAutoCompletion(txtCustomerTel,obList);

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

    private String getCurrentOrderId() {
        String nextOrderId = "";

        try {
            String currentId = OrderRepo.getLastOId();

            nextOrderId = generateNextOrderId(currentId);
            lblOrderCode.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nextOrderId;
    }


    private void getTransportLocation() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> locationList = TransportRepo.getLoca();

            for(String location : locationList) {
                obList.add(location);
            }

            TextFields.bindAutoCompletion(txtLocation,obList);

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

    }

    @FXML
    void txtCustomerIdOnAction(ActionEvent actionEvent) {

        String tel = txtCustomerTel.getText();

        try {
            Customer customer = CustomerRepo.searchByTel(tel);

            lblCustName.setText(customer.getCName());
            lblCustomerId.setText(customer.getCustId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        txtLocation.requestFocus();
    }

    public void txtItemNameSearchOnAction(ActionEvent actionEvent) {
        btnItemNameSearchOnAction();
    }

    public void btnItemNameSearchOnAction() {
        String name = txtItemNameSearch.getText();

        try {
            BrandNewItem item = BrandNewItemRepo.searchByName(name);
            ItemSupplierDetail itemDetail = ItemSupplierDetailRepo.searchById(item.getItemId());
            if(item != null) {
                lblItemName.setText(item.getName());
                lblItemId.setText(item.getItemId());
                Image image = new Image(item.getPath(),281, 178, false, true);
                imageCart.setImage(image);
                lblCatagory.setText(item.getCategory());
                lblUnitPrice.setText(String.valueOf(itemDetail.getUnitPrice()));
                lblHandOnQty.setText(String.valueOf(itemDetail.getQty()));
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnOrderReceiptOnAction(ActionEvent actionEvent) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/OrderBill.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("oId",getLastOrderId());
        data.put("netAm", String.valueOf(gettNetTotal()));

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }


    private double gettNetTotal() {
        double netTotal = 0.0;
        try {
            System.out.println(getLastOrderId());
            netTotal = OrderRepo.getNetTot(getLastOrderId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return netTotal;
    }

    private String getLastOrderId(){
        String orderId;
        try {
            orderId = OrderRepo.getLastOId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderId;
    }

    private void clear() {
        txtItemNameSearch.setText("");
        txtCustomerTel.setText("");
        lblCustomerId.setText("");
        lblCustName.setText("");
        txtLocation.setText("");
        cmbPaymentMethod.setValue("");
        lblNetTotal.setText("");
        lblTrId.setText("");
        lblTransportCost.setText("");
        getCurrentOrderId();
        tblOrder.getItems().clear();
    }

    public void txtLocationOnAction(ActionEvent actionEvent) {
        String location = txtLocation.getText();

        try {
            Transport tr = TransportRepo.searchByLoca(location);
            if(tr != null) {
                lblTrId.setText(tr.getTrId());
                lblTransportCost.setText(String.valueOf(tr.getCost()));

            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        txtItemNameSearch.requestFocus();
    }

    public void txtQtyOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.QTY,txtQty);
    }

    public void txtTelOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.PHONENO,txtCustomerTel);
    }

    public boolean isValidate(){
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.PHONENO,txtCustomerTel))return false;
        if(!Regex.setTextColor(lk.ijse.chama.util.TextField.QTY,txtQty))return false;

        return true;
    }
}
