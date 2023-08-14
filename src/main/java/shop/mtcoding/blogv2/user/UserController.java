package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// Controller의 역할 << 유효성 검사와 요청에 따른 응답 
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // MVC 패턴으로 생성
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        // joinDTO를 이용, Service에서 Repository연결
        // UserController는 Service에 의존한다. << 의존성 주입 해주어야함
        userService.회원가입(joinDTO);
        return "user/loginForm"; // 이때 persist 초기화(Clear())됨
    }
}
