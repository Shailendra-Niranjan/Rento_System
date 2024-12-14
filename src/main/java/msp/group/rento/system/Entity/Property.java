package msp.group.rento.system.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import msp.group.rento.system.Enum.BookedStatus;

import java.util.List;

@Entity
@Data
public class Property extends BaseEntity{

    private String name;
    @Embedded
    private Address address;
    @Column(columnDefinition = "text")
    private  String summary;
    @Enumerated
    private BookedStatus bookedStatus;

    private long emptyWithInDays;

    @OneToMany(mappedBy = "property" , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonManagedReference
    private List<Room> rooms;

    @OneToMany(mappedBy = "property" , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonManagedReference
    private List<RentalAgreement> agreements;

    @ManyToOne
    @JsonBackReference
    private LandLord landLord;

    @ManyToOne
    @JsonBackReference
    private Tenant  tenant;


}
