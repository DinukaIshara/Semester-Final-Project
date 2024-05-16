package lk.ijse.chama.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ItemCard {
    private String itemId;
    private String itemName;
    private double price;
    private int handOnQty;
    private String category;
    private String image;
}
