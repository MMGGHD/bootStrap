package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;
import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;
import shop.mtcoding.blogv2.user.UserRequest.updateDTO;

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

    public User 로그인(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user == null) { // username이 존재하지 않음
            return null;
        } else { // username 존재함
            if (user.getPassword().equals(loginDTO.getPassword())) {
                return user;
            } else {
                return null;
            }
        }
    }

    public User 회원정보보기(Integer id) {
        return userRepository.findById(id).get();
    }

    @Transactional // << 자동 flush
    public User 회원수정(updateDTO updateDTO, Integer id) {
        // 1. 조회(영속화)
        User user = userRepository.findById(id).get();
        // 2. 변경
        user.setPassword(updateDTO.getPassword());
        // 3. flush
        return null;
    }

}
