package lk.ijse.chama.model.tm;

import lk.ijse.chama.model.Repair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepairTm extends Repair {
    private String repId;
    private String itemName;
    private String description;
    private String custId;
    private double cost;
    private LocalDate reciveDate;
    private LocalDate reternDate;
}
