package lk.ijse.chama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Amil Srinath
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CustomerDTO {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String nic;
    private String email;
    private String salary;
    private String age;
}
