package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.MovieBoardEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.MovieBoardRatingEntity;
import bitc.fullstack405.bitcteam3prj.database.repository.MovieBoardRepository;
import bitc.fullstack405.bitcteam3prj.database.repository.MovieRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

  @Autowired
  private MovieRatingRepository movieRatingRepo;

  @Autowired
  private MovieBoardRepository boardRepository;



  @Override
  public List<MovieBoardRatingEntity> findAllByMovieBoard(MovieBoardEntity movieBoard) throws Exception {
    return movieRatingRepo.findAllByMovieBoard(movieBoard);

  }

  @Override
  public void deleteById(long movieBoardRatingId) throws Exception {
    movieRatingRepo.deleteById(movieBoardRatingId);
  }

  @Override
  public void updateRating(MovieBoardRatingEntity ratingEntity) throws Exception {
    movieRatingRepo.save(ratingEntity);
  }


  @Override
  public void queryAvgRating() throws Exception {

  }

//  @Override
//  public void insertRatingList(List<MovieBoardRatingEntity> ratingList) throws Exception {

//  }

  @Override
  public void insertRating(MovieBoardRatingEntity ratingEntity) {
    movieRatingRepo.save(ratingEntity);
  }

  @Override
  public MovieBoardRatingEntity findIdForRating(long movieBoardRatingId) throws Exception {
    return movieRatingRepo.findById(movieBoardRatingId);
  }
}