package UserServiceTwo.user.dto;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordDto {

    private String newPassword;
}
