
package lk.ijse.chama.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.chama.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    public TextField txtUserName;
    public PasswordField txtPassword;
    @FXML
    private AnchorPane rootNode;

    @FXML
    void btnLoginOnAction() throws IOException {
        String userName = txtUserName.getText();
        String pw = txtPassword.getText();

        try {
            checkCredential(userName, pw);
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
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
            }

        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user id can't be find!").show();
        }
    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/sidepanelform.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage)this.rootNode.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Dashboard");

        stage.centerOnScreen();
        stage.show();
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
    void txtPasswordOnAction() throws IOException {
        btnLoginOnAction();
    }


}
