package shop.mtcoding.blogv2._core.util;

import lombok.Getter;
import lombok.Setter;

// ApiUtil<T> << JSON으로 받은 댓글을 처리완료했을때 클라이언트로 전달할 객체DTO

@Getter
@Setter
public class ApiUtil<T> {
    private boolean success;
    private T data;

    public ApiUtil(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
}
