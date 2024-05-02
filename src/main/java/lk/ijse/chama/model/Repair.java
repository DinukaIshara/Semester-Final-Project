package lk.ijse.chama.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Repair {
    private String repairId;
    private Date reciveDate;
    private Date returnDate;
    private double cost;
    private String description;
    private String custId;
    private String itemName;
}
