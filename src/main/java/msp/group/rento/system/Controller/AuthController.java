package msp.group.rento.system.Controller;

import msp.group.rento.system.DTO.UserDto;
import msp.group.rento.system.Service.AuthService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register (@RequestBody UserDto userDto) {
         return ResponseEntity.ok(Map.of("Message" ,userService.registerUser(userDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) throws BadRequestException {

        return ResponseEntity.ok(userService.loginUser(userDto));
    }
    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.forgetUserPassword(userDto));
    }
    @PostMapping("/emailVerificatonOtp")
    public ResponseEntity<Map<String, String>> sendOtpForEmailVerify(@RequestParam String fullname, @RequestParam String email){
        return ResponseEntity.ok(Map.of("Message",userService.OptForEmailVerification(fullname, email)));
    }

    @GetMapping("/readOtpForEmailVerificatonOtp")
    public ResponseEntity<Map<String, String>> ReadOtpForEmailVerify(@RequestParam String otp, @RequestParam String email){
        return ResponseEntity.ok(Map.of("Message",userService.readOtpForEmailVerification(otp, email)));
    }
    @GetMapping("/test")
    public String testController(){
        return "test controller from task ";
    }



}
