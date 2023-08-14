package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;

// @Service << 컴퍼넌트 스캔됨
@Service
public class UserService {

    // UserService는 UserRepository를 의존한다. << 의존성 주입
    @Autowired
    private UserRepository userRepository;

    // Service는 핵심로직, 예외처리, 트랜잭션을 관리할 책임.
    // Transactional이 필요할시 여기서 붙임
    @Transactional
    public void 회원가입(JoinDTO joinDTO) {
        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .build();
        // UserRepository는 User라는 Entity를 관리하는 Repository이다.
        // 그래서 joinDTO를 이용한 user객체를 build해서 Repository의 메서드에 전달
        userRepository.save(user); // 여기서 em.persist()가 된다.
    }

}
