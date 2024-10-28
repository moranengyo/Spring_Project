package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.BoardCommentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardCommentService {

    void boardCommentWrite(BoardCommentEntity board) throws Exception;

    void boardCommentUpdate(BoardCommentEntity board) throws Exception;

    void boardCommentDelete(Long boardId) throws Exception;

    List<BoardCommentEntity> findAllByUserId(long id) throws Exception;
}
