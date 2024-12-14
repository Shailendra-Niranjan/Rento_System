package msp.group.rento.system.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import msp.group.rento.system.Enum.PrefferedContactMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@Data
public class LandLord  extends BaseEntity{

    @Enumerated
    private PrefferedContactMethod prefferedContactMethod;
    private String notes;

    @OneToMany(mappedBy = "landLord" ,cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonManagedReference
    private List<Property> propertyList;

    @OneToMany
//    @JsonManagedReference
    private List<Users> tenantList;

    @OneToOne
    @JsonBackReference
    private Users users;
}
