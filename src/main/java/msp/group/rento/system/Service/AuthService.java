package msp.group.rento.system.Service;

import msp.group.rento.system.DTO.UserDto;
import org.apache.coyote.BadRequestException;

public interface AuthService {

    String registerUser(UserDto userDto );

    String loginUser (UserDto dto) throws BadRequestException;

    String forgetUserPassword(UserDto userDto);

    String OptForEmailVerification(String fName, String email);
    String readOtpForEmailVerification(String otp , String email);

}
