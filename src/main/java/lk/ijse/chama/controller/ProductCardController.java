package lk.ijse.chama.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.chama.MyListener;
import lk.ijse.chama.model.ProductCard;

public class ProductCardController {

    @FXML
    private ImageView itemImage;

    @FXML
    private Label lblHandOnQty;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblPrice;

    private ProductCard productCard;

    private MyListener myListener;


    public void setData(ProductCard productCard, MyListener myListener) {
        this.productCard = productCard;
        this.myListener = myListener;

        lblItemName.setText(productCard.getItemName());
        lblPrice.setText(String.valueOf(productCard.getPrice()));
        Image image = new Image(productCard.getImage(),190, 155, false, true);
        itemImage.setImage(image);


    }
    @FXML
    public void click(javafx.scene.input.MouseEvent mouseEvent) {
        myListener.onClickListener(productCard);
    }
}