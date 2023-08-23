package shop.mtcoding.blogv2.user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.vo.MyPath;
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

        // 네트워크상 고유성을 부여하기 위해서 랜덤해시값을 부여
        // getOriginalFilename() 은 확장자를 포함하는 이름이다.
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + joinDTO.getPic().getOriginalFilename();

        // 자바에서 받은 파일을 저장할 경로를 상대경로로 지정(IMG_PATH의 ./는 현재위치를 의미)
        // 현재 경로 Desktop/WS/workspace/project/blogV2
        // 배포 시 해당 실행 파일 경로에 images 폴더가 필요함
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);

        // 파일 쓰기 write(경로, 바이트파일)
        try {
            Files.write(filePath, joinDTO.getPic().getBytes());
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

        // DB에 저장
        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                // DB에는 경로는 제외하고 파일명만 저장한다. << 경로 변화에 대응하기 위해
                .picUrl(fileName)
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
