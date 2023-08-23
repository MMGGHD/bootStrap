package shop.mtcoding.blogv2.user;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

// 요청하는 DTO
public class UserRequest {

    // 컨트롤러로 받은 pic는 MultipartFile객체이다.
    @Getter
    @Setter
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
        private MultipartFile pic;
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
        private MultipartFile pic;
    }

    @Getter
    @Setter
    public static class CheckDTO {
        private String username;
    }
}
