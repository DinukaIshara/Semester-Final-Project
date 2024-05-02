package lk.ijse.chama.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.model.BrandNewItem;

public class ProductCardController {

    @FXML
    private AnchorPane card_form;

    @FXML
    private Button prod_addBtn;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private Spinner<?> prod_spinner;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }
    public void setData(BrandNewItem item) {
        /*Image image = new Image(getClass().getResourceAsStream(String.valueOf(item.getImage()));
        prod_imageView.setImage(image);

        prod_name.setText(item.getItrmName());

        prod_price.setText(String.valueOf(item.getPrice()));

        prod_spinner.setPromptText(String.valueOf(item.getQty()));
*/
    }
}