package lk.ijse.chama.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MostSellItemTm {
    private String itemId;
    private int orderCount;
    private int sumQty;
}
