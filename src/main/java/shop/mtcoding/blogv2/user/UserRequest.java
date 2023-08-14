package shop.mtcoding.blogv2.user;

import lombok.Getter;
import lombok.Setter;

// 요청하는 DTO
public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
    }

    @Getter
    @Setter
    public static class LoginDTO {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    public static class updateDTO {
        private String password;
    }
}
