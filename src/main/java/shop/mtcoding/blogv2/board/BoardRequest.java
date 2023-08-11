package shop.mtcoding.blogv2.board;

import lombok.Getter;
import lombok.Setter;

// 이 클래스의 내부클래스로 요청 DTO를 관리한다.
public class BoardRequest {

    // 내부클래스에 public를 붙이면 외부에서 찾을수 있다.
    // static키워드를 붙이므로써 Heap공간에 띄우고, 외부클래스.(이름) << 으로 찾을수있다.
    @Getter
    @Setter
    public static class SaveDTO {
        private String title;
        private String content;

    }
}
