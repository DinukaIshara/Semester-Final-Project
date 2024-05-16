package lk.ijse.chama.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BrandNewItem {
    private String itemId;
    private String Name;
    private String category;
    private String brand;
    private LocalDate stockDate;
    private String description;
    private String warranty;
    private String type;
    private String path;
}
