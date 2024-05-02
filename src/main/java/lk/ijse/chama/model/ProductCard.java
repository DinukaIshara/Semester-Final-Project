package lk.ijse.chama.model;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductCard {
    private Image image;
    private String itrmName;
    private double price;
    private int qty;
}
