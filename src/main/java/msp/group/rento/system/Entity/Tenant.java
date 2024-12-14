package msp.group.rento.system.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Tenant extends  BaseEntity {

    @Column(nullable = false)
    private boolean activeRenter;
    private double totalSpent;
    private Date activationDate;

    @OneToMany(mappedBy = "tenant" , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonManagedReference
    private List<Room> rentedRooms;

    @OneToMany(mappedBy = "tenant" , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonManagedReference
    private List<Property> rentedProperties;

    @OneToOne
    @JsonBackReference
    private Users users;



}
