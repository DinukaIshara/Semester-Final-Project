package lk.ijse.chama.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerTm {
    private String custId;
    private String cName;
    private String cAddress;
    private String cNIC;
    private String contactNo;
    private String cEmail;
    //private JFXButton remove;
}
