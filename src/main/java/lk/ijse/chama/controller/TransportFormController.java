package lk.ijse.chama.controller;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lk.ijse.chama.model.Location;
import lk.ijse.chama.model.Transport;
import lk.ijse.chama.model.tm.TransportTm;
import lk.ijse.chama.repository.LocationRepo;
import lk.ijse.chama.repository.TransportRepo;
import lk.ijse.chama.util.Regex;
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

    private MapPoint eiffelPoint = new MapPoint(6.711053811499971, 79.9097716129893);

    public void initialize() {
        setCellValueFactory();
        loadAllTransport();
        getPlaces();
        getLoaction();

        try {
            MapView mapView = crateMapView();
            mapRootNode.getChildren().add(mapView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

        //if(isValidate()) {
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
        /*}else{
            new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
        }*/
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
            //if(isValidate()) {
                boolean isSaved = TransportRepo.update(transport);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Transport updated!").show();
                    clearFields();
                    initialize();
                }
            /*}else{
                new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
            }*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = TransportRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Transport deleted!").show();
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

    public void getPlaces(){  // Location Table place get
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> placeList = LocationRepo.getPlace();

            for(String place : placeList) {
                obList.add(place);
            }

            TextFields.bindAutoCompletion(txtLocation, obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MapView crateMapView() throws SQLException {
        MapView mapView = new MapView();
        List<String> validLocations = LocationRepo.getPlace();
        if (validLocations.contains(txtLocation.getText())) { // if (txtLocation.getText().equals("Colombo")|| txtLocation.getText().equals("Kandy") || txtLocation.getText().equals("Kalutara"))
            Location location = LocationRepo.searchByPath(txtLocation.getText());
            System.out.println(location.getLatitude());
            System.out.println(location.getLongitude());
            eiffelPoint = new MapPoint(location.getLatitude(), location.getLongitude());

            mapView.setPrefSize(446, 487);
            mapView.addLayer(new CustomLayer());
            mapView.setZoom(15);
            mapView.flyTo(0, eiffelPoint, 0.1);

            return mapView;

        }else if (!(txtLocation.getText().equals(null))) {

            mapView.setPrefSize(446, 487);
            mapView.addLayer(new CustomLayer());
            mapView.setZoom(15);
            mapView.flyTo(0, eiffelPoint, 0.1);

            System.out.println("!null");
            return mapView;
        }
        return mapView;
    }

    public class CustomLayer extends MapLayer {
        private final Node mark;

        public CustomLayer(){
            mark = new Circle(5, Color.RED);
            getChildren().add(mark);
        }

        @Override
        protected void layoutLayer(){
            try {
                List<String> validLocations = LocationRepo.getPlace();
                if (validLocations.contains(txtLocation.getText())) {
                    Location location = LocationRepo.searchByPath(txtLocation.getText());
                    eiffelPoint = new MapPoint(location.getLatitude(), location.getLongitude());

                    Point2D point2D = getMapPoint(eiffelPoint.getLatitude(), eiffelPoint.getLatitude());
                    mark.setTranslateX(point2D.getX());
                    mark.setTranslateY(point2D.getY());

                } else if (!(txtLocation.getText().equals(null))) {
                    Point2D point2D = getMapPoint(eiffelPoint.getLatitude(), eiffelPoint.getLatitude());
                    mark.setTranslateX(point2D.getX());
                    mark.setTranslateY(point2D.getY());
                }
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }

    }

    public void txtSearchLocationOnAction(ActionEvent actionEvent) {
        try {
            btnSearchLocationOnAction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getLoaction() {  // Transport Table Location Loads
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
        txtVehicalNo.requestFocus();
    }

    public void txtVehicalNoOnAction(ActionEvent actionEvent) {
        txtDriverName.requestFocus();
    }

    public void txtDriverNameOnAction(ActionEvent actionEvent) {
        txtLocation.requestFocus();
    }

    public void txtLocationOnAction(ActionEvent actionEvent) {

        try {
            MapView mapView = crateMapView();
            mapRootNode.getChildren().add(mapView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        txtCost.requestFocus();
    }

    public void txtTrIdOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.TID,txtId);
    }

    public void txtVehicalNoOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.VEHICALNO,txtVehicalNo);
    }

    public void txtcostOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.PRICE,txtCost);
    }

    public boolean isValidate(){
        if (!Regex.setTextColor(lk.ijse.chama.util.TextField.TID,txtId))return false;
        if (!Regex.setTextColor(lk.ijse.chama.util.TextField.VEHICALNO,txtVehicalNo))return false;
        if (!Regex.setTextColor(lk.ijse.chama.util.TextField.PRICE,txtCost))return false;

        return false;
    }
}