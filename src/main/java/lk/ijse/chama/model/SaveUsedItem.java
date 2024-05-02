package lk.ijse.chama.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaveUsedItem {
    private UsedItem usedItem;
    private ItemSupplierDetail itemSupplierDetail;
}
