package lk.ijse.chama.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsedItem {
    private String itemId;
    private String Name;
    private String category;
    private String brand;
    private String description;
}
