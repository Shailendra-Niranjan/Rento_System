package msp.group.rento.system.Entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Payments extends BaseEntity{
    private String creditor;
    private String debitor;
    private double amount;
    private String transactionId;
    private String paymentMethod;

}
