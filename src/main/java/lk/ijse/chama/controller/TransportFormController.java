package lk.ijse.chama.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.model.Customer;
import lk.ijse.chama.model.Supplier;
import lk.ijse.chama.model.Transport;
import lk.ijse.chama.model.tm.SupplierTm;
import lk.ijse.chama.model.tm.TransportTm;
import lk.ijse.chama.repository.CustomerRepo;
import lk.ijse.chama.repository.SupplierRepo;
import lk.ijse.chama.repository.TransportRepo;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.List;

public class TransportFormController {

    public TextField txtSearchLocation;
    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colDriverName;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colTransportId;

    @FXML
    private TableColumn<?, ?> colVehicalNo;

    @FXML
    private AnchorPane mapRootNode;

    @FXML
    private TableView<TransportTm> tblTransport;

    @FXML
    private TextField txtCost;

    @FXML
    private TextField txtDriverName;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtVehicalNo;

    public void initialize() {
        setCellValueFactory();
        loadAllTransport();
        getLoaction();
    }

    private void setCellValueFactory() {
        colTransportId.setCellValueFactory(new PropertyValueFactory<>("trId"));
        colVehicalNo.setCellValueFactory(new PropertyValueFactory<>("vehicalNo"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    private void loadAllTransport() {
        ObservableList<TransportTm> obList = FXCollections.observableArrayList();

        try {
            List<Transport> transportList = TransportRepo.getAll();
            for (Transport transport : transportList) {
                TransportTm tm = new TransportTm(
                        transport.getTrId(),
                        transport.getVehicalNo(),
                        transport.getDriverName(),
                        transport.getLocation(),
                        transport.getCost()
                );

                obList.add(tm);
            }

            tblTransport.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String trId = txtId.getText();
        String vehicalNo = txtVehicalNo.getText();
        String driverName = txtDriverName.getText();
        String location = txtLocation.getText();
        double cost = Double.parseDouble(txtCost.getText());

        Transport transport = new Transport(trId,vehicalNo,driverName,location,cost);

        try {
            boolean isSaved = TransportRepo.save(transport);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Transport saved!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String trId = txtId.getText();
        String vehicalNo = txtVehicalNo.getText();
        String driverName = txtDriverName.getText();
        String location = txtLocation.getText();
        double cost = Double.parseDouble(txtCost.getText());

        Transport transport = new Transport(trId,vehicalNo,driverName,location,cost);

        try {
            boolean isSaved = TransportRepo.update(transport);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Transport updated!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = TransportRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "transport deleted!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtId.setText("");
        txtDriverName.setText("");
        txtVehicalNo.setText("");
        txtLocation.setText("");
        txtCost.setText("");
    }

    public void txtSearchLocationOnAction(ActionEvent actionEvent) {
        try {
            btnSearchLocationOnAction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getLoaction() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> locationList = TransportRepo.getlocation();

            for (String location : locationList) {
                obList.add(location);
            }
            TextFields.bindAutoCompletion(txtSearchLocation, obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchLocationOnAction() throws SQLException {
        String location = txtSearchLocation.getText();

        Transport tr = TransportRepo.searchByLocation(location);
        if (tr != null) {
            txtId.setText(tr.getTrId());
            txtVehicalNo.setText(tr.getVehicalNo());
            txtDriverName.setText(tr.getDriverName());
            txtLocation.setText(tr.getLocation());
            txtCost.setText(String.valueOf(tr.getCost()));
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Location not found!").show();
        }
    }

    public void txtTrIdOnAction(ActionEvent actionEvent) {

    }

    public void txtVehicalNoOnAction(ActionEvent actionEvent) {

    }

    public void txtVehicalNoOnKeyRelesed(KeyEvent keyEvent) {

    }

    public void txtDriverNameOnAction(ActionEvent actionEvent) {

    }

    public void txtLocationOnAction(ActionEvent actionEvent) {


    }

    public void txtcostOnKeyRelesed(KeyEvent keyEvent) {

    }

    public void txtTrIdOnKeyRelesed(KeyEvent keyEvent) {

    }
}
