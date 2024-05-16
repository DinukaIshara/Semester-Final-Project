package lk.ijse.chama.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private String custId;
    private String cName;
    private String cAddress;
    private String cNIC;
    private String contactNo;
    private String cEmail;

    @Override
    public String toString() {
        return "Customer{" +
                "custId='" + custId + '\'' +
                ", cName='" + cName + '\'' +
                ", cAddress='" + cAddress + '\'' +
                ", cNIC='" + cNIC + '\'' +
                ", contactNo=" + contactNo +
                ", cEmail='" + cEmail + '\'' +
                '}';
    }
}