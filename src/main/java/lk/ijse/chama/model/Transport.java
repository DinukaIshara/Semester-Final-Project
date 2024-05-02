package lk.ijse.chama.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transport {
    private String trId;
    private String vehicalNo;
    private String driverName;
    private String location;
    private double cost;
}
