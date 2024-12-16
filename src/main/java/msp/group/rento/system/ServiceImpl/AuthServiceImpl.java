package msp.group.rento.system.ServiceImpl;

import jakarta.mail.MessagingException;
import msp.group.rento.system.DTO.UserDto;
import msp.group.rento.system.Entity.Address;
import msp.group.rento.system.Entity.Otp;
import msp.group.rento.system.Enum.PurposeOfOtp;
import msp.group.rento.system.Enum.Role;
import msp.group.rento.system.Entity.Users;
import msp.group.rento.system.Repository.OtpRepo;
import msp.group.rento.system.Security.JWTService;
import msp.group.rento.system.Security.LoggedInUser;
import msp.group.rento.system.Security.UserDeatilsServices;
import msp.group.rento.system.Service.AuthService;
import msp.group.rento.system.Service.EmailService;
import msp.group.rento.system.Repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private BCryptPasswordEncoder encoder  = new BCryptPasswordEncoder(11);

    // Character pool for the password
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "abcdefghijklmnopqrstuvwxyz" +
                    "0123456789" +
                    "!@#$%^&*()-_+=<>?";

    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDeatilsServices userDeatilsServices;

    @Autowired
    JWTService jwtService;
//
//    @Autowired
//    Gson gson;

    @Autowired
    EmailService emailService;

    @Autowired
    OtpRepo otpRepo;

    @Override
    public String registerUser(UserDto userDto) {
        if(userDto.getPhoneNo().length()!=10)return "Invalid contact number ";
        Users usersExist = userRepository.findUsersByEmail(userDto.getEmail());
        if (usersExist!=null){
            return "User already Exist !";
        }
        Users users = new Users();
        users.setEmail(userDto.getEmail());
        users.setFullName(userDto.getFullName());
        users.setPassword(encoder.encode(userDto.getPassword()));
        users.setPhoneNo(userDto.getPhoneNo());
        users.setActive(true);
        users.setUpdateAt(new Date());
        users.setLastActiveTime(new Date());
        Address address = new Address();
        address.setHouseNo(userDto.getHouseNo());
        address.setStreet(userDto.getStreet());
        address.setCity(userDto.getCity());
        address.setState(userDto.getState());
        address.setLandmark(userDto.getLandmark());
        address.setPincode(userDto.getPincode());
        users.setAddress(address);
        users.setRole(Role.USER_ROLE);

        try {
            userRepository.save(users);
            emailService.userCreationMail(users.getEmail() ,"Account Created" , userDto.getPassword() ,users.getFullName());

        }
        catch (IOException | MessagingException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Register Successfully !";
    }

    @Override
    public String loginUser(UserDto dto) throws BadRequestException {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        System.out.println(authentication.getAuthorities().toString());
        String token = jwtService.generateToken(dto.getEmail() , String.valueOf(authentication.getAuthorities().stream().toList().get(0)));
        System.out.println(token);
        return token;
    }

    @Override
    public String forgetUserPassword(UserDto userDto) {

        LoggedInUser loggedInUser  = (LoggedInUser) userDeatilsServices.loadUserByUsername(userDto.getEmail());

        String newPassword = generatePassword();
        Users users = loggedInUser.getUsers();
        users.setPassword(encoder.encode(newPassword));
        userRepository.save(users);
        try {
            emailService.sendResetPassword(users.getEmail() ,"Forget Password" , newPassword , users.getFullName());
        } catch (IOException e) {
            System.out.println(e);
            return "IOException occur while sending mail";
        } catch (MessagingException e) {
            System.out.println(e);
            throw  new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return "New Password sent to your email";
    }

    @Override
    public String OptForEmailVerification(String fName, String email) {
        Otp otp = new Otp();
        otp.setOtp(generateOTP());
        otp.setUserEmail(email);
        otp.setExpireAt(LocalDateTime.now().plusMinutes(2));
        otp.setPurposeOfOtp(PurposeOfOtp.EmailVerification);
        otpRepo.save(otp);
        try {
            emailService.otpForUserCreationMail(email, fName, otp.getOtp());
        } catch (IOException e) {
            System.out.println(e);
            return "IOException occur while sending mail";
        } catch (MessagingException e) {
            System.out.println(e);
            return "MessagingException occur while sending mail";
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return "Otp send successfully";
    }

    @Override
    public String readOtpForEmailVerification(String otp, String email) {
        Otp otp1 = otpRepo.findByOtpAndEmail(otp,email);
        if(otp1 == null){
            return "Invailid Otp";
        }
        otp1.setUsed(true);
        otpRepo.save(otp1);
        if(otp1.getExpireAt().isBefore(LocalDateTime.now()))return "Otp expired !";

        return "Email Verify";
    }


    public static String generatePassword() {
        StringBuilder password = new StringBuilder(10); // Fixed length: 10

        // Generate 10 random characters from the pool
        for (int i = 0; i < 10; i++) {
            password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return password.toString();
    }
    public static String generateOTP() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

}
