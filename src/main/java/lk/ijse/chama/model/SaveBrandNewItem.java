package lk.ijse.chama.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SaveBrandNewItem {
    private BrandNewItem brandNewItem;
    private ItemSupplierDetail itemSupplier;
}
