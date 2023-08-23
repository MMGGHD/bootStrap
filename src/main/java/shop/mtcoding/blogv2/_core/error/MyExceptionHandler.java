package shop.mtcoding.blogv2._core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;

// @RestController'Advice' << Exception을 모을수있게 안내하는 어노테이션
@RestControllerAdvice
public class MyExceptionHandler {

    // @ExceptionHandler("받을 예외 타입")
    // 응답객체가 필요없는 (View를 return하는) 작업의 에러에 MyException 던짐
    // 이때 back스크립트와 alert창 에러메세지가 전달
    @ExceptionHandler(MyException.class)
    public String error(MyException e) {
        return Script.back(e.getMessage());
    }

    // 응답객체가 필요한 (데이터를 return하는) 작업의 에러에 MyApiException 던짐
    // 이때 return값으로 false와, 에러메시지가 getMessage()가 담긴 객체가 전달됨
    @ExceptionHandler(MyApiException.class)
    public ApiUtil<String> error(MyApiException e) {
        return new ApiUtil<String>(false, e.getMessage());
    }

    // @ExceptionHandler(NoSuchElementException.class)
    // public String error(NoSuchElementException e) {

    // System.out.println("테스트 : 상세보기 ExceptionHandler리턴");
    // return Script.back(e.getMessage());
    // }
}