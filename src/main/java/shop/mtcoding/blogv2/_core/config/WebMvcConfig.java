package shop.mtcoding.blogv2._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import shop.mtcoding.blogv2._core.interceptor.LoginInterceptor;

// 중간언어(XML, JSON, YML파일 등) 설정파일은 자바코드로 바뀌어 실행된다.
// 내부에 구현되어 직접 건드릴수 없기 때문에 자바 파일을 만들어서 오버라이드를 통해 제어한다.
// @Configuration << Web.xml 설정파일을 덮어씌우기 위한 어노테이션 (ComponentScan됨)
// implements WebMvcConfigurer << 오버라이드 가능
// WebMvcConfigurer << Interceptors 등등 등록하는 메서드를 포함한다.
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // addResourceHandler() << 매개변수안에 요청받는 주소
    // addResourceLocations() << 요청받으면 전달할 경로
    // file: << 내부에서 주소를 찾아오는 프로토콜
    // setCachePeriod() << 캐싱되는 시간 (초단위)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + "./images/")
                .setCachePeriod(10) // 초단위
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    // 생성한 Interceptor를 등록하는 메서드
    // 주소를 통해서 걸러내지므로 컨트롤러 주소를 작성하는 방식이 매우 중요해진다.
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                // addPathPatterns( "해당 주소로 갈때" )
                .addPathPatterns("/user/update", "/user/updateForm")
                .addPathPatterns("/api/**")
                .addPathPatterns("/board/**")
                // excludePathPatterns ( "해당주소로 갈때는 예외적 허용" )
                .excludePathPatterns("/board/{id:[0-9]+}");
    }
}
