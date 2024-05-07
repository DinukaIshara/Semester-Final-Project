package lk.ijse.chama.model;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductCard {
    private String itemId;
    private String itemName;
    private double price;
    private int handOnQty;
    private String category;
    private String image;
}
