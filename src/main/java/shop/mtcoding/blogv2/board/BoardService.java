package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.board.BoardRequest.UpdateDTO;
import shop.mtcoding.blogv2.user.User;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO saveDTO, int sessionUserId) {
        Board board = Board.builder()
                .title(saveDTO.getTitle())
                .content(saveDTO.getContent())
                .user(User.builder().id(sessionUserId).build())
                .build();
        boardRepository.save(board);
    }

    public Page<Board> 게시글목록보기(Integer page) {
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return boardRepository.findAll(pageable);
    }

    public Board 상세보기(Integer id) {
        Optional<Board> boardOP = boardRepository.mFindByIdJoinUserAndReplies(id);
        if (boardOP.isPresent()) {
            return boardOP.get();
        } else {
            // 상세보기는 Board(View)를 return하기 때문에 MyException으로 alert창
            throw new MyException(id + "번 페이지를 찾을수 없어요");
        }
    }

    @Transactional
    public void 글수정(UpdateDTO updateDTO, Integer id) {
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {

            // 더티 체킹
            Board board = boardOP.get();
            board.setTitle(updateDTO.getTitle());
            board.setContent(updateDTO.getContent());
            boardRepository.save(board); // 있어도되고 없어도됨 (@Transactional flush)
        } else {
            throw new MyException(id + "번 페이지를 찾을수 없어요");
        }
    }

    @Transactional
    public void 글삭제(Integer id) {
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyException(id + "번 페이지를 찾을수 없어요");
        }
    }
}
