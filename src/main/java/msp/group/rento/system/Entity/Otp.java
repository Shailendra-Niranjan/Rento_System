package msp.group.rento.system.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.Data;
import msp.group.rento.system.Enum.PurposeOfOtp;

import java.time.LocalDateTime;

@Entity
@Data
public class Otp extends BaseEntity{

    @Column(unique = true)
    private String otp;
    private String userEmail;
    private LocalDateTime expireAt;
    @Enumerated
    private PurposeOfOtp purposeOfOtp;
    @Column(name = "is_used", nullable = false)
    private boolean isUsed;
}
