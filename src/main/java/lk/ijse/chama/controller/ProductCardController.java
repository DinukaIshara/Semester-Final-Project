package lk.ijse.chama.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chama.model.BrandNewItem;
import lk.ijse.chama.model.ProductCard;

import java.util.Objects;

public class ProductCardController {


    @FXML
    private Button btnAddtoCart;

    @FXML
    private AnchorPane card_form;

    @FXML
    private ImageView itemImage;

    @FXML
    private Label lblHandOnQty;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblPrice;

    @FXML
    private TextField txtQty;

    private ProductCard productCard;

    @FXML
    void btnAddtoCartOnAction(ActionEvent event) {

    }

    public void setData(ProductCard productCard) {
        this.productCard = productCard;

        lblItemName.setText(productCard.getItemName());
        lblPrice.setText(String.valueOf(productCard.getPrice()));
        lblHandOnQty.setText(String.valueOf(productCard.getHandOnQty()));
        //spinnerQty.setPromptText(String.valueOf(productCard.getQty()));
        //Image image = new Image(productCard.getImage());
        //itemImage.setImage(image);
        //System.out.println(productCard.getImage());


        System.out.println("setData = " + productCard);


    }
}