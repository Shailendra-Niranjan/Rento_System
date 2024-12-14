package msp.group.rento.system.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import msp.group.rento.system.Enum.BookedStatus;

@Entity
@Data
public class MontlyTransactions extends BaseEntity{

    private String creditor;
    private String debitor;
    private double actualRent;
    private double paidRent;
    private double powerBill;
    private double paidPowerBill;
    @Enumerated
    private BookedStatus.RentStatus rentStatus;

    @ManyToOne
    @JsonBackReference
    private RentalAgreement rentalAgreement;
}
