package msp.group.rento.system.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Room  extends BaseEntity{

    private long roomNo;
    private String profilePic;
    private String publicIdProfilePic;
    private List<String> pics;
    private List<String> publicIdPics;

    private long emptyWithInDays;
    @ManyToOne
    @JsonBackReference
    private Tenant tenant;

    @ManyToOne
    @JsonBackReference
    private Property property;
}
