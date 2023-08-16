package shop.mtcoding.blogv2.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2.user.User;

// DataJpaTest << 모든 Repository와 EntityManager만 메모리에 뜬다.
// 그래서 Test하기 편환 환경이 만들어 진다.
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void save_test() {
        Board board = new Board(); // 비영속객체
        board.setTitle("제목6"); // 비영속객체
        board.setContent("내용6"); // 비영속객체

        // board의 user_id에 값을 넣기 위해 관계된 User객체하나를 new해야한다.
        // 넣으면 User객체 내에 컬럼명과 일치하는 값을 찾아서 set한다
        // DB는 객체단위를 넣지 못하기 때문
        // session이 존재하면 sessionUser를 넣으면 된다.
        User user = new User();
        user.setId(1);
        board.setUser(user);
        System.out.println(board.getId());
        boardRepository.save(board); // 영속화, 스프링의 객체와 동기화
        // 여기서 부터 board객체는 영속객체가 됨
        System.out.println(board.getId());
    }

    // builder를 쓰는 방법
    @Test
    public void save_test2() {
        Board board = Board.builder()
                .title("제목7")
                .content("내용7")
                .user(User.builder().id(1).build())
                .build();
        boardRepository.save(board);
    }

    @Test
    public void findAll_test() {
        System.out.println("조회직전");
        List<Board> boardList = boardRepository.findAll();
        System.out.println("조회후 : LAZY");
        System.out.println(boardList.get(0).getId());
        System.out.println(boardList.get(0).getUser().getId());
    }

    @Test
    public void mFindAll_test() {
    }

    @Test
    public void findAll_paging_test() throws JsonProcessingException { // Domain의 Pageable
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
        Page<Board> boardPG = boardRepository.findAll(pageable);
        // 구조를 쉽게 확인하기 위해 JSON으로 변환할것
        // Jackson라이브러리의 ObjectMapper << 자바 <--> JSON 변환을 해준다. (Getter를 이용한 파싱)
        ObjectMapper om = new ObjectMapper();
        // 자바객체를 Json으로 변환하는(직렬화) 메서드
        String json = om.writeValueAsString(boardPG);
        // json 데이터를 jsonViewer에 복붙하면 구조를 확인할수 있다.
        System.out.println(json);
    }

}
