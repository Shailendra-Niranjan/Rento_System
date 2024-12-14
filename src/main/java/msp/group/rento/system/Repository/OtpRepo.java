package msp.group.rento.system.Repository;

import msp.group.rento.system.Entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OtpRepo extends JpaRepository<Otp , UUID> {
    @Query("select u from Otp u where u.otp = :otp and u.userEmail = :email and u.isUsed = false")
    Otp findByOtpAndEmail(@Param("otp") String otp, @Param("email") String email);

}
