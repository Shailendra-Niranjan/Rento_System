package msp.group.rento.system.Entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Users extends BaseEntity {

    private Tenant tenant;
}
