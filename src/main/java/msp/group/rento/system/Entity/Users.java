package msp.group.rento.system.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import msp.group.rento.system.Enum.Role;

import java.util.Date;

@Entity
@Data
@Getter
@Setter
public class Users extends BaseEntity {

    private String email;
    private String password;
    private String fullName;
    private String phoneNo;

    @Enumerated
    private Role role;

    @Column(nullable = false)
    private boolean active;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Column(nullable = false)
    private boolean status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActiveTime;
    private String accountInfo;
    @Embedded
    private Address address;

    @OneToOne(mappedBy = "users" ,cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonManagedReference
    private Tenant tenant;
    @OneToOne(mappedBy = "users" ,cascade =  CascadeType.ALL , orphanRemoval = true)
    @JsonManagedReference
    private LandLord  landLord;



}
