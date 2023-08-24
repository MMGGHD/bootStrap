package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "board_tb")
@Entity // ddl-auto가 create
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = true)
    private String content;

    // FetchType을 LAZY로 하면 연관객체를 Fetch하지 않겠다.
    // 이때 연관객체를 직접 찾기 않으면 DB에서 들고오지 않고 연관객체의 PK만 땡겨온다.
    // 연관객체의 나머지 값들은 null이 된다.
    // 이때 연관객체의 null값을 직접 찾으려(getter)하면 영속화 하기 위해 조회가 일어난다.
    // FetchType을 EAGER로 하면 연관객체를 Fetch하겠다. << 어떻게든 DB에서 들고오겠다.
    // 따라서 EAGER로 조회를 하면 연관객체까지 바로 영속화 된다.

    // 1+N 문제 발생
    // JSON직렬화를 할때 발생하는 추가적인 Select를 막기 위해 @JsonIgnore어노테이션을 붙인다.
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // ManyToOne의 FetchType은 EAGER전략이 Default
    // OneToMany의 FetchType은 LAZY전략이 Default
    // @JsonIgnoreProperties ("a", "b","c") << Json직렬화시 선택한 속성 빼고 찾기
    // 댓글의 FK로 보드가 잡혀있어 댓글이있는 상태로 보드를 삭제하면 오류가 발생한다.
    // cascade << 영속성 전이 관리해주는 어노테이션, 참조개체를 찾아서 모두 삭제할수 있다.
    // 댓글만 남기려면 cascade안쓰고 게시글 조회후 참조값을 모두 null값으로 수정한 후 삭제한다.
    @JsonIgnoreProperties({ "board" })
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<Reply>();

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

}