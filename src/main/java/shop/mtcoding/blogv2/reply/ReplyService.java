package shop.mtcoding.blogv2.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.board.BoardRequest.SaveDTO;
import shop.mtcoding.blogv2.user.User;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    public void 댓글쓰기(ReplyRequest.SaveDTO saveDTO, Integer sessionid) {
        // insert into reply_tb(comment, board_id, user_id) values(?,?,?)

        // 연관객체의 FK값만 가지고 온 다음 Reply을 Build할때 사용
        Board board = Board.builder().id(saveDTO.getBoardId()).build();
        User user = User.builder().id(sessionid).build();

        // JpaRepository에는 Entity만 들어갈수 있다
        // save안에서는 Reply객체가 들어가야하기 DTO바로 넣지 않고 Reply객체를 Build해서 넣음
        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(board)
                .user(user)
                .build();
        replyRepository.save(reply);
    }

}
