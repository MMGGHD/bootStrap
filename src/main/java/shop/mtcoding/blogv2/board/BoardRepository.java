package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// 사용법 : JpaRepository<(모델 타입),(PK의 타입)> 
// JpaRepository내부에 save(), findById(), findAll(), count(), delete() 등이 구현되어있음
// update()는 다른 방식으로
// 그외 필요한 메서드는 EntityManager em; 을 끌어와서 쿼리를 넣으면 된다.
// @Repository << JpaRepository를 상속하면서 생략가능하다
// 스프링이 시작될때 임의의 하위 클래스를 만들어서 자동으로 BoardRepository를 구현한다.
// 이 임의의 클래스를 IoC컨테이너에 띄우고 BoardRepository를 호출하여 메서드를 가져올수있다.
// 인터페이스의 구현도 다형성을 만족하기 때문에 가능한 것
public interface BoardRepository extends JpaRepository<Board, Integer> {

    // JPQL에서 조인쿼리 (join하면 inner조인이 디폴트)
    // select b from Board b join b.user 는 아래의 SQL문과 같음
    // select id, title, content, user_id, created_at from board_tb b
    // inner join user_tb u on b.user_id = u.id;
    // 이 경우 user_tb가 프로젝션되지 않기 때문에 똑같이 1+N 문제가 발생한다.
    // select * from board_tb b inner join user_tb u on b.user_id = u.id; 를 뽑기 위해서
    // fetch를 넣어줘야 함
    // fetch << 연관객체안에 들어가서 있는 컬럼을 모두 뽑아주는 키워드.
    @Query("select b from Board b join fetch b.user")
    List<Board> mFindAll();

    @Query("select b from Board b join fetch b.user where b.id = :id")
    Board mFindById(@Param("id") Integer id);

    @Query("select b from Board b left join fetch b.replies r left join fetch r.user ru where b.id = :id")
    Optional<Board> mFindByIdJoinUserAndReplies(@Param("id") Integer id);
}
