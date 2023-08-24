package shop.mtcoding.blogv2._core.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.user.User;

// HandlerInterceptor를 생성하는 클래스
// HandlerInterceptor인터페이스는 Default 메서드로 구체적으로 구현됨 (강제성도 없음)
public class LoginInterceptor implements HandlerInterceptor {

    // return값이 boolean인 이유
    // ture 이면 컨트롤러 메서드 진입
    // false 이면 요청이 종료됨
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // preHandle에 인증 안된 사용자는 팅
        System.out.println("LoginInterceptor PreHandle");
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 요청 주소에 /api/가 있으면 응답을 JSON으로 해야한다. << api 요청에 대한 분기가 필요함
        // request.getRequestURI() << request의 요청주소를 찾아주는 메서드
        String startEndPoint = request.getRequestURI().split("/")[1];

        if (sessionUser == null) {

            // api요청에 인증이 안되어 있을 경우 // JSON으로 응답이 전해져야함
            if (startEndPoint.equals("api")) {
                response.setHeader("Content-Type", "text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                ApiUtil<String> apiUtil = new ApiUtil<String>(false, "인증이 필요합니다.");

                // ObjectMapper << 오브젝트를 JSON으로 변환해준다. (JSON은 String 문자열임)
                String responseBody = new ObjectMapper().writeValueAsString(apiUtil);
                out.println(responseBody);
            }

            // 일반 요청에 인증이 안되어 있을 경우 // alert 메시지응답이 전해져야함
            else {
                response.setHeader("Content-Type", "text/html; charset=utf-8");
                // PrintWriter << BuffedWriter에 역슬러시n(문장끊기)와 AutoFlush기능 추가됨
                PrintWriter out = response.getWriter();
                out.println(Script.href("/loginForm", "인증이 필요합니다."));
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        System.out.println("LoginInterceptor PostHandle");
    }

}
