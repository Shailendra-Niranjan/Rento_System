package msp.group.rento.system.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import msp.group.rento.system.Enum.Types;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class RentalAgreement extends BaseEntity{

    private boolean active;
    private Types types;
    private UUID rentId;
    private int interest;
    private double rent;
    private  double totalPaymentDue;
    private double lateCharges;
    private Date startAt;
    private Date endAt;

    @OneToMany(mappedBy = "rentalAgreement" ,cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonManagedReference
    private List<MontlyTransactions> montlyTransactions;


    @ManyToOne
    @JsonBackReference
    private Property property;
}
