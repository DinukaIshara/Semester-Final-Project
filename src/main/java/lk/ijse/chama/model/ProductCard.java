package lk.ijse.chama.model;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductCard {
    private String itemName;
    private double price;
    //private String image;
    private int handOnQty;
}
