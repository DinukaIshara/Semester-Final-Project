package lk.ijse.chama.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailTm {
    private String code;
    private String model;
    private int qty;
    private double unitPrice;
    private double total;
    private JFXButton btnRemove;

}
