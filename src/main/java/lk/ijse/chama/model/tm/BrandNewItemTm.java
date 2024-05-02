package lk.ijse.chama.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BrandNewItemTm {
    private String itemId;
    private String name;
    private String category;
    private String brand;
    private double unitPrice;
    private int qty;
    private String supplierId;
}
