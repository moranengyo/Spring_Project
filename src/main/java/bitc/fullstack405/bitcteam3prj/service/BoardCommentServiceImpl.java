package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.BoardCommentEntity;
import bitc.fullstack405.bitcteam3prj.database.repository.BoardCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardCommentServiceImpl implements BoardCommentService {

    @Autowired
    private BoardCommentRepository boardCommentRepository;

//    댓글 작성
    @Override
    public void boardCommentWrite(BoardCommentEntity board) throws Exception {
            boardCommentRepository.save(board);
    }

//    댓글 수정
    @Override
    public void boardCommentUpdate(BoardCommentEntity board) throws Exception {
        board.setId(board.getId());
        board.setContents(board.getContents());
        boardCommentRepository.save(board);
    }

//    댓글 삭제
    @Override
    public void boardCommentDelete(Long boardId) throws Exception {
        boardCommentRepository.deleteById(boardId);
    }

    @Override
    public List<BoardCommentEntity> findAllByUserId(long id) throws Exception {
        return boardCommentRepository.findAllByUser_Id(id);
    }
}
