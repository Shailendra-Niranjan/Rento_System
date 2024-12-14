package msp.group.rento.system.Controller;

import msp.group.rento.system.DTO.UserDto;
import msp.group.rento.system.Entity.Users;
import msp.group.rento.system.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getDetailsOfMe(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = userRepository.findUsersByEmail(userName);
        if (users==null)return ResponseEntity.ok(new UserDto());
        UserDto userDto = new UserDto();
        userDto.setEmail(users.getEmail());
        userDto.setFullName(users.getFullName());
        userDto.setPhoneNo(users.getPhoneNo());
        userDto.setState(users.getAddress().getState());
        userDto.setCity(users.getAddress().getCity());
        userDto.setPincode(users.getAddress().getPincode());

        return ResponseEntity.ok(userDto);

    }
}
