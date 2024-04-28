package lk.ijse.chama.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Customer {
    private String custId;
    private String cName;
    private String cAddress;
    private String cNIC;
    private String contactNo;
    private String cEmail;

    public Customer(String custId, String cName, String cAddress, String cNIC, String contactNo, String cEmail) {
        this.custId = custId;
        this.cName = cName;
        this.cAddress = cAddress;
        this.cNIC = cNIC;
        this.contactNo = contactNo;
        this.cEmail = cEmail;
    }
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