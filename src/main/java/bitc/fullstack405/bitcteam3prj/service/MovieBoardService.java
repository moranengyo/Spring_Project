package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.MovieBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieBoardService {
    List<MovieBoardEntity> selectMovieBoardList() throws Exception;

    Page<MovieBoardEntity> selectMovieBoardList(Pageable pageable) throws Exception;

    MovieBoardEntity selectMovieBoardDetail(Long movieBoardId) throws Exception;

    void insertMovieBoard(MovieBoardEntity movieBoard) throws Exception;

    void insertMovieBoardList(List<MovieBoardEntity> movieBoardList) throws Exception;

    MovieBoardEntity findByMovieId(long movieId) throws Exception;

    Page<MovieBoardEntity> selectMovieBoardListByCate(Pageable pageable, String searchCate);

    Page<MovieBoardEntity> selectMovieBoardListByTitle(Pageable pageable, String searchTitle);

    Page<MovieBoardEntity> selectMovieBoardListByCateAndTitle(Pageable pageable, String searchTitle, String searchCate);
    

}