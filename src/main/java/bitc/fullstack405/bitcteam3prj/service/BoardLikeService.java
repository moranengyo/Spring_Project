package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.BoardLikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardLikeService {
    int countByBoardIdAndUserId(long boardId, long userId) throws Exception;

    int countByBoardId(long boardId) throws Exception;
    Page<BoardLikeEntity> findAllByUserIdPaging(Pageable pageable, long userId) throws Exception;
    BoardLikeEntity findByUserIdAndBoardId(long userId, long boardId) throws Exception;
    List<BoardLikeEntity> findAllByBoardId(long boardId) throws Exception;

    void updateBoardLike(BoardLikeEntity boardLike) throws Exception;


    void deleteBoardLike(BoardLikeEntity boardLike) throws Exception;
    void deleteBoardLikeById(Long boardLikeId) throws Exception;

    List<BoardLikeEntity> findAllByUserId(long userId);
}
