package shop.mtcoding.blogv2;

// 예외처리 학습을 위한 임의의 테스트용 레이어 3개를 만든것

class MyController {

    // Controller는 Service에 의존
    MyService myService = new MyService();

    void home(boolean check) {
        myService.홈가기(check);
    }
}

class MyService {

    // Service는 Repository에 의존
    MyRepository myRepository = new MyRepository();

    void 홈가기(boolean check) {
        myRepository.findHome(check);
    }
}

class MyRepository {

    // 조회가 안되면 RuntimeException오류를 던지도록(throw) 설계
    // RuntimeException << 프로그램이 실행한 상태에서 발생가능한 오류
    void findHome(boolean check) {
        if (check) {
            System.out.println("조회 완료");
        } else {
            throw new RuntimeException("조회 오류");
        }
    }
}

public class HelloTest {
    public static void main(String[] args) {
        MyController myController = new MyController();
        myController.home(false);
    }
}