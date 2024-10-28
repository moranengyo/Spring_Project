package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.BoardLikeEntity;
import bitc.fullstack405.bitcteam3prj.database.repository.BoardLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardLikeServiceImpl implements BoardLikeService {
    @Autowired
    private BoardLikeRepository boardLikeRepository;

    @Override
    public int countByBoardIdAndUserId(long boardId, long userId) throws Exception {
        return boardLikeRepository.countByUser_IdAndBoard_Id(userId, boardId);
    }

    @Override
    public int countByBoardId(long boardId) throws Exception {
        return boardLikeRepository.countByBoard_Id(boardId);
    }

    @Override
    public Page<BoardLikeEntity> findAllByUserIdPaging(Pageable pageable, long userId) throws Exception {
        return boardLikeRepository.findAllByUser_Id(pageable, userId);
    }

    @Override
    public BoardLikeEntity findByUserIdAndBoardId(long userId, long boardId) throws Exception {
        return boardLikeRepository.findByUser_IdAndBoard_Id(userId, boardId);
    }

    @Override
    public List<BoardLikeEntity> findAllByBoardId(long boardId) throws Exception {
        return boardLikeRepository.findAllByBoard_Id(boardId);
    }

    @Override
    public void updateBoardLike(BoardLikeEntity boardLike) throws Exception {
        boardLikeRepository.save(boardLike);
    }

    @Override
    public void deleteBoardLike(BoardLikeEntity boardLike) throws Exception {
        boardLikeRepository.delete(boardLike);
    }

    @Override
    public void deleteBoardLikeById(Long boardLikeId) throws Exception {
        boardLikeRepository.deleteById(boardLikeId);
    }

    @Override
    public List<BoardLikeEntity> findAllByUserId(long userId) {
        return boardLikeRepository.findAllByUser_Id(userId);
    }
}
