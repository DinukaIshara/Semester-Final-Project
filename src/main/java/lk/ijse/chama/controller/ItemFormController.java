package lk.ijse.chama.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ItemFormController {

    public AnchorPane rootNode;

    @FXML
    void btnBrandNewItemOnAction(ActionEvent event) throws IOException {
        AnchorPane brandRootNode = FXMLLoader.load(this.getClass().getResource("/view/brandNewItem_form.fxml"));
        rootNode.getChildren().clear();
        rootNode.getChildren().add(brandRootNode);
    }

    @FXML
    void btnUsedItemOnAction(ActionEvent event) throws IOException {
        AnchorPane usedRootNode = FXMLLoader.load(this.getClass().getResource("/view/usedItem_form.fxml"));
        rootNode.getChildren().clear();
        rootNode.getChildren().add(usedRootNode);
    }

}
