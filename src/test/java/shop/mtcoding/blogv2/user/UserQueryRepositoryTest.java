package shop.mtcoding.blogv2.user;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserQueryRepository.class)
@DataJpaTest
public class UserQueryRepositoryTest {

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void save_test() {
        User user = User.builder()
                .username("love")
                .password("1234")
                .email("love@nate.com")
                .build();
        userQueryRepository.save(user); // 영속화
        // em.flush();
    }

    // 1차 캐시
    @Test
    public void findById_test() {

        System.out.println("1. PC는 비어있다");
        userQueryRepository.findById(1);
        System.out.println("2. PC는 user1은 영속화 되어있다");
        userQueryRepository.findById(1);
        System.out.println("3. user1 호출하면 select 되지 않는다.");
        em.clear();
        userQueryRepository.findById(1);
        System.out.println("???");

    }

    @Test
    public void update_test() {
        // JPA의 update 알고리즘
        // 1. 업데이트할 객체를 영속화시킨다.
        // 2. 객체 상태를 변경
        // 3. em.flush() or @transactional 종료(자동 flush()발동)
        User user = userQueryRepository.findById(1); // 객체를 영속화
        user.setEmail("ssarmango@nate.com");
        em.flush();
        // rollback
    }
}
