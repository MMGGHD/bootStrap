package shop.mtcoding.blogv2.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.util.Script;

// Controller의 역할 << 유효성 검사와 요청에 따른 응답 
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

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

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login")
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userService.로그인(loginDTO);
        System.out.println("postMapping Login");
        if (sessionUser == null) {
            return Script.back("로그인 실패");
        }
        session.setAttribute("sessionUser", sessionUser);
        return Script.href("/");
    }

    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request) {
        User sessioUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessioUser.getId());
        request.setAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("/user/update")
    public String update(UserRequest.updateDTO updateDTO) {
        // 1. 수정할 세션을 가져온다.
        User sessioUser = (User) session.getAttribute("sessionUser");
        // 2. 서비스에서 수정된 유저 객체를 받는다.
        User user = userService.회원수정(updateDTO, sessioUser.getId());
        // 3. 바뀐 유저정보로 세션을 동기화한다.
        session.setAttribute("sessionUser", user);
        return "redirect:/";
    }
}
