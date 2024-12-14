package msp.group.rento.system.DTO;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;
    private String fullName;
    private String phoneNo;
    private long houseNo;
    private String street;
    private String landmark;
    private String city;
    private String state;
    private String pincode;
}
