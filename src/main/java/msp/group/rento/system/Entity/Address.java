package msp.group.rento.system.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Embeddable
public class Address {
    private long houseNo;
    private String street;
    private String landmark;
    private String city;
    private String state;
    @Column(length = 6)
    private String pincode;
}
