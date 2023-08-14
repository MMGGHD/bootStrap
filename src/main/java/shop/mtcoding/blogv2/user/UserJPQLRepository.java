package shop.mtcoding.blogv2.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// JPQL Java Persistance Query Language << SQL과 비슷한 객체지향 쿼리
// 엔티티 객체를 바로 쿼리에 집어넣어서 작성한다.
// DB에 전송되기 전에 SQL로 바뀌어서 날아간다.
// 엔티티와 속성의 대소문자를 구분하며, 별칭이 필수적이다.
public interface UserJPQLRepository extends JpaRepository<User, Integer> {

    @Query(value = "select u from User u where u.id = :id")
    Optional<User> mFindById(@Param("id") Integer id);

    @Query(value = "select u from User u where u.username = :username")
    User findByUsername(@Param("username") String username);

    // Insert, Update, Delete는 지원하지 않는다. (이들은 NativeQuery를 써야한다.)
    // @Modifying << executeUpdate와 같은뜻 (executeUpdate와 executeQuery를 구분하지 못하므로)
    @Modifying
    @Query(value = "insert into user_tb(created_at, email, password, username) values(now(), :email, :password, :username)", nativeQuery = true)
    void mSave(@Param("username") String username, @Param("password") String password, @Param("email") String email);
}
