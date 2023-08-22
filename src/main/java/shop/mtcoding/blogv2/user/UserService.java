package shop.mtcoding.blogv2.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
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
        Optional<User> userOP = userRepository.findByUsername(loginDTO.getUsername());
        if (userOP.isEmpty()) { // username이 존재하지 않음
            return null;
        } else { // username 존재함
            if (userOP.get().getPassword().equals(loginDTO.getPassword())) {
                return userOP.get();
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

    public String 유저네임중복확인(String username) {
        Optional<User> userOP = userRepository.findByUsername(username);
        System.out.println("user 객체 : " + userOP);
        if (userOP.isEmpty()) {
            return "사용가능한 닉네임 입니다.";
        }
        throw new MyApiException("중복된 닉네임 입니다.");
    }
}
