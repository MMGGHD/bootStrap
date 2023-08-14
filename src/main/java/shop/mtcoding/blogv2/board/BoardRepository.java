package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 사용법 : JpaRepository<(모델 타입),(PK의 타입)> 
// JpaRepository내부에 save(), findById(), findAll(), count(), delete() 등이 구현되어있음
// update()는 다른 방식으로
// 그외 필요한 메서드는 EntityManager em; 을 끌어와서 쿼리를 넣으면 된다.
// @Repository << JpaRepository를 상속하면서 생략가능하다
// 스프링이 시작될때 임의의 하위 클래스를 만들어서 자동으로 BoardRepository를 구현한다.
// 이 임의의 클래스를 IoC컨테이너에 띄우고 BoardRepository를 호출하여 메서드를 가져올수있다.
// 이것은 인터페이스의 구현도 다형성을 만족하기 때문에 가능한 것이다.
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
