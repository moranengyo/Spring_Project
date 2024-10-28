package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.BoardEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.BoardLikeEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.UserEntity;
import bitc.fullstack405.bitcteam3prj.database.repository.BoardLikeRepository;
import bitc.fullstack405.bitcteam3prj.database.repository.BoardRepository;
import bitc.fullstack405.bitcteam3prj.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardLikeRepository boardLikeRepository;
    @Autowired
    private UserRepository userRepository;

    //    게시판 전체 목록
    @Override
    public List<BoardEntity> selectBoardList() throws Exception {
        List<BoardEntity> boardList =  boardRepository.findAll();

        return boardList;
    }

//    게시글 상세보기
    @Override
    public Page<BoardEntity> selectBoardList(Pageable pageable) throws Exception{
        return boardRepository.findAll(pageable);
    }

    @Override
    public BoardEntity selectBoardDetail(Long boardId) throws Exception {

        Optional<BoardEntity> opt = boardRepository.findById(boardId);

        BoardEntity board = null;
        if(opt.isPresent()){
            board = opt.get();
        }
        return board;
    }

//    게시글 삭제
    @Override
    public void deleteBoardById(long boardId) throws Exception {
        boardRepository.deleteById(boardId);
    }


    //    게시글 등록 처리
    @Override
    public void insertBoard(BoardEntity board) throws Exception {
        boardRepository.save(board);
    }

//    게시글 수정
    @Override
    public void updateBoard(BoardEntity board) throws Exception {
        boardRepository.save(board);
    }



    @Override
    public List<BoardEntity> findAllByUserId(Long userId) throws Exception {
        return boardRepository.findAllByUser_Id(userId);
    }


    @Override
    public Page<BoardEntity> selectBoardListBySearchValue(Pageable pageable, String searchValue) throws Exception {
        return boardRepository.findAllByTitleContains(pageable, searchValue);
    }

    @Override
    public Page<BoardEntity> selectBoardListBySearchValueAndSearchCate(Pageable pageable, String searchValue, String searchCate) {
        return boardRepository.findAllByTitleContainsAndCategoryContains(pageable, searchValue, searchCate);
    }

    @Override
    public Page<BoardEntity> selectBoardListByCate(Pageable pageable, String searchCate) throws Exception {
        return boardRepository.findAllByCategoryContains(pageable, searchCate);
    }


}
