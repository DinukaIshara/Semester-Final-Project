package lk.ijse.chama.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BrandNewItem {
    private String itemId;
    private String Name;
    private String category;
    private String brand;
    private String modelNo;
    private String warranty;
    private String description;
    private String type;
    private String path;
}
