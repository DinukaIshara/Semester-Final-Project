package lk.ijse.chama.util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextField textField, String text){
        String filed = "";

        switch (textField){
            case CID:
                filed = "^([C][0-9]{3,10})$";
                break;
            case IID:
                filed = "^([I][0-9]{3,10})$";
                break;
            case EID:
                filed = "^([E][0-9]{3,10})$";
                break;
            case PID:
                filed = "^([P][0-9]{3,10})$";
                break;
            case SID:
                filed = "^([S][0-9]{3,10})$";
                break;
            case RPID:
                filed = "^([RP][0-9]{3,10})$";
                break;
            case TID:
                filed = "^([TR][0-9]{3,10})$";
                break;
            case NAME:
                filed = "^[A-Za-z]{3,}(?:\\s[A-Za-z]{3,})?$";
                break;
            case NIC:
                filed = "^([0-9]{8}[x|X|v|V]|[0-9]{12})$";
                break;
            case DATE:
                filed = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
                break;
            case VEHICALNO:
                filed = "^[A-Z]{2}-\\d{4}$";
                break;
            case EMAIL:
                filed = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case PHONENO:
                filed = "^\\d{10}$";
                break;
            case ADDRESS:
                filed = "^([A-z0-9]|[-/,.@+]|\\\\s){4,}$";
                break;
            case PRICE:
                filed = "^([0-9]){1,}[.]([0-9]){1,}$";
                break;
            case QTY:
                filed = "^\\d+$";
                break;
            case PASSWORD:
                filed = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$";
                break;
        }

        Pattern pattern = Pattern.compile(filed);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(TextField location, javafx.scene.control.TextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){
            textField.setStyle("-fx-border-color: #0081ff; -fx-text-fill: #05bef3;  -fx-border-radius: 5px; -fx-border-width:  2px 2px 2px 2px; -fx-background-radius: 5px;");

            return true;
        }else {
            textField.setStyle("-fx-border-color: #ff0000; -fx-text-fill: #ff0000; -fx-border-radius: 5px; -fx-border-width:  2px 2px 2px 2px; -fx-background-radius: 5px;");

            return false;
        }
    }
}
