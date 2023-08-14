package shop.mtcoding.blogv2.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// UserRepository는 Entity로 User를 관리한다.
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from user_tb where id = :id", nativeQuery = true)
    User mFindById(@Param("id") Integer id);

    // 얘는 쓸모 있음
    @Query(value = "select * from user_tb where username = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);

    // 쿼리메소드(쿼리 문자열이 없어도 findBy와 And 키워드를 이용, 자동으로 쿼리를 만들어서 해줌)
    // User findByUsernam(@Param("username") String username);

    @Modifying
    @Query(value = "insert into user_tb(created_at, email, password, username) values(now(), :email, :password, :username)", nativeQuery = true)
    void mSave(@Param("username") String username, @Param("password") String password, @Param("email") String email);
}
