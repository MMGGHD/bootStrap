package shop.mtcoding.blogv2._core.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// <Filter의 역할>
// 1. IP 로그 남기기
// 2. 블랙리스트 추가
// 3. 

// Component Scan이 안되면 @Autowired가 안된다. 즉, 여기서 Repository를 DI할수 없다
// 스프링이 Component Scan으로 객체를 생성 할때 빈 생성자로 생성한다.
// 빈 생성자가 없으면 다른 유일한 생성자의 매개변수의 타입에 맞는 객체를 IOC컨테이너에서 찾는다.
// 찾아서 주입할 변수에 대입한다.

// Filter타입이어야만 FilterRegistrationBean에 등록할수 있다.
public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // // 1. IP 로그 남기기
        // System.out.println("접속자 ip : " + req.getRemoteAddr());
        // System.out.println("접속자 userAgent : " + req.getHeader("User-Agent"));

        // 2. 블랙리스트 추가
        // setContentType는 setHeader를 편하게 해주는 메서드이다.
        // setHeader("헤더 명","헤더 내용 명") << 직접 설정할수도 있다.
        if (req.getHeader("User-Agent").contains("xxx")) {
            // resp.setContentType("text/html; charset=utf-8");
            resp.setHeader("Content-Type", "text/html; charset=utf-8");

            // PrintWriter << BuffedWriter에 역슬러시n(문장끊기)와 AutoFlush기능 추가됨
            PrintWriter out = resp.getWriter();
            req.setAttribute("name", "홍길동");
            out.println("<h1>나가세요 크롬이면 : " + req.getAttribute("name") + "</h1>");
            return;
        }
        // chain() << request, response를 다음 필터로 전달하는 메서드
        // 다음 필터가 없으면 DS로 간다.
        chain.doFilter(request, response);
    }
}