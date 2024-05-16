
package lk.ijse.chama.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.chama.db.DbConnection;
import lk.ijse.chama.util.Regex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private AnchorPane rootNode;

    @FXML
    void btnLoginOnAction() throws IOException {
        String userName = txtUserName.getText();
        String pw = txtPassword.getText();

        try {
            if(isValied()) { // Validated
                checkCredential(userName, pw);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void checkCredential(String user_name, String pw) throws SQLException, IOException {
        String sql = "SELECT user_name, password FROM users WHERE user_name = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, user_name);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString("password");

            if(pw.equals(dbPw)) {
                navigateToTheDashboard(user_name);
            } else {
                new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
            }

        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user id can't be find!").show();
        }
    }

    private void navigateToTheDashboard(String user_name) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sidepanelform.fxml"));
        Parent dashboardRoot = loader.load();
        SidepanelformController controller = loader.getController();
        controller.setUserName(user_name); // Pass the username to the DashboardFormController

        Scene scene = new Scene(dashboardRoot);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Home Page");
    }

    @FXML
    void hyperRegistrationOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/registration.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage)this.rootNode.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Registration");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void txtUserNameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction() throws IOException {
        btnLoginOnAction();
    }

    // Validation -------------------------------------------------------------------------------
    public void txtUserNameOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.NAME,txtUserName);
    }

    public void txtPasswordOnActionKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.chama.util.TextField.PASSWORD,txtPassword);
    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.chama.util.TextField.NAME,txtUserName)) return false;
        if (!Regex.setTextColor(lk.ijse.chama.util.TextField.PASSWORD,txtPassword)) return false;
        return true;
    }
}
