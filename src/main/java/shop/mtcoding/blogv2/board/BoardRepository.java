package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 사용법 : JpaRepository<(모델 타입),(PK의 타입)> 
// JpaRepository내부에 save(), findById(), findAll(), count(), delete() 등이 구현되어있음
// update()는 다른 방식으로
// 그외 필요한 메서드는 EntityManager em; 을 끌어와서 쿼리를 넣으면 된다.
// @Repository << JpaRepository를 상속하면서 생략가능하다
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
