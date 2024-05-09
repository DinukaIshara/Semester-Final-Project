package lk.ijse.chama.util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextField textField, String text){
        String filed = "";

        switch (textField){
            case ID:
                filed = "^([A-Z0-9])$";
                break;
            case NAME:
                filed = "^[A-z|\\\\s]{3,}$";
                break;
            case EMAIL:
                filed = "^[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$";
                break;
            case ADDRESS:
                filed = "^([A-z0-9]|[-/,.@+]|\\\\s){4,}$";
                break;
            case CONTACT:
                filed = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
                break;
            case NIC:
                filed = "";
                break;
            case SALARY:
                filed = "^([0-9]){1,}[.]([0-9]){1,}$";
                break;
            case AGE:
                filed = "";
                break;
            case PASSWORD:
                filed = "^(?=.\\d)(?=.[a-z])(?=.[A-Z])(?=.[a-zA-Z]).{8,}$";
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
            textField.setStyle("-fx-border-color: green;");
            return true;
        }else {
            textField.setStyle("-fx-border-color: red;");
            return false;
        }
    }
}
