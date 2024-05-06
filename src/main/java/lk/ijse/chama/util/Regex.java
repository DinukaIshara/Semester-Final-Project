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
                filed = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";
                break;
            case ADDRESS:
                filed = "10";
                break;
            case CONTACT:
                filed = "1";
                break;
            case NIC:
                filed = "";
                break;
            case SALARY:
                filed = "";
                break;
            case AGE:
                filed = "";
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

    public static boolean setTextColor(TextField location, JFXTextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){
            textField.setFocusColor(Paint.valueOf("Green"));
            textField.setUnFocusColor(Paint.valueOf("Green"));
            return true;
        }else {
            textField.setFocusColor(Paint.valueOf("Red"));
            textField.setUnFocusColor(Paint.valueOf("Red"));
            return false;
        }
    }
}
