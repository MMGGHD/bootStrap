package shop.mtcoding.blogv2._core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.blogv2._core.filter.MyFilter1;

// IOC에 담기위한 어노테이션
@Configuration
public class FilterConfig {

    // 필터를 등록하는 메서드 문법임(암기),
    // 등록 방법에서는 여러가지가 있지만 FilterRegistrationBean객체를 쓴다.
    // new FilterRegistrationBean<>()의 매개변수에 Filter 객체를 넣으므로써 Filter Chain에 넣는다.
    // Filter Chain은 스프링 내부에 들어가기 전에 처리하므로 스프링 셋팅파일의 적용을 받지 못한다.
    @Bean
    public FilterRegistrationBean<MyFilter1> myFilter1() {
        // new FilterRegistrationBean<>(); << 매개변수에 필터를 등록한다. 문법임(암기)
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        // 슬래시로 시작하는 모든 주소에 발동됨 (어떨때는 /** 일수도 있음)
        bean.addUrlPatterns("/*");
        // 필터 실행의 우선순위 (필터 실행 순서를 결정한다.) 낮을수록 빠름
        bean.setOrder(0);
        return bean;
    }

    // // 다음 필터(myFilter2)를 등록하는 메서드
    // @Bean
    // public FilterRegistrationBean<?> myFilter2() {
    // FilterRegistrationBean<?> bean = new FilterRegistrationBean<>();
    // // 필터 실행의 우선순위 (필터 실행 순서를 결정한다.) 낮을수록 빠름
    // bean.setOrder(1);
    // return bean;
    // }
}
