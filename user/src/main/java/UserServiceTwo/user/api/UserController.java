package UserServiceTwo.user.api;

import UserServiceTwo.user.dto.UserChangePasswordDto;
import UserServiceTwo.user.dto.UserCreateDto;
import UserServiceTwo.user.dto.UserSignInDto;
import UserServiceTwo.user.service.KeycloakService;
import UserServiceTwo.user.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final KeycloakService keycloakService;

    @PostMapping(value = "/sign-in")
    public String signIn(@RequestBody UserSignInDto userSignInDto) {
        return keycloakService.signIn(userSignInDto);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto userCreateDto) {
        keycloakService.createUser(userCreateDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordDto userChangePasswordDto) {
        String currentUserName = UserUtils.getCurrentUserName();
        if (currentUserName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Couldn't Identify User");
        }

        try {

            keycloakService.changePassword(currentUserName, userChangePasswordDto.getNewPassword());
            return ResponseEntity.ok("Password changed");

        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error on changing password");
        }
    }
}
