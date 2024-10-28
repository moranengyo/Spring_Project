package bitc.fullstack405.bitcteam3prj.database.repository;

import bitc.fullstack405.bitcteam3prj.database.entity.MovieBoardEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.MovieBoardRatingEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRatingRepository extends JpaRepository<MovieBoardRatingEntity, Long> {

  List<MovieBoardRatingEntity> findAllByMovieBoard(MovieBoardEntity movieBoard);

  MovieBoardRatingEntity findById(long movieBoardRatingId);

  void deleteById(long movieBoardRatingId);

  @Query("select avg(mbr.movieRating) from MovieBoardRatingEntity mbr where mbr.movieBoard = :movieBoard ")
  MovieBoardRatingEntity queryAvgRating(@Param("movieBoard") MovieBoardEntity movieBoard);


}