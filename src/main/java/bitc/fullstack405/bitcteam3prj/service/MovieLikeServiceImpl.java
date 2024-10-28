package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.MovieLikeEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.UserEntity;
import bitc.fullstack405.bitcteam3prj.database.repository.MovieLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieLikeServiceImpl implements MovieLikeService {

  @Autowired
  MovieLikeRepository movieLikeRepository;


  @Override
  public int getMovieLikeCnt(long userId, long movieId) {
    return movieLikeRepository.countByUser_IdAndMovie_Id(userId, movieId);
  }

  @Override
  public List<MovieLikeEntity> findByUser(UserEntity user) throws Exception {
    return movieLikeRepository.findByUser(user);
  }

  @Override
  public void addLike(MovieLikeEntity movieLikeEntity) throws Exception {
    if (movieLikeRepository.findByUserAndMovie
            (movieLikeEntity.getUser(), movieLikeEntity.getMovie())== null) {
      movieLikeRepository.save(movieLikeEntity);
    }
  }

  @Override
  public void deleteLike(long userId, long movieId) throws Exception {
    movieLikeRepository.deleteByUser_IdAndMovie_Id(userId, movieId);
  }

  @Override
  public void deleteMovieLikeById(long ratingId) throws Exception {
    movieLikeRepository.deleteById(ratingId);
  }


}
