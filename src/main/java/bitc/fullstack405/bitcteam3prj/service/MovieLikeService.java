package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.MovieEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.MovieLikeEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.UserEntity;

import java.util.List;

public interface MovieLikeService {

  int getMovieLikeCnt(long userId, long movieId) throws Exception;
  List<MovieLikeEntity> findByUser(UserEntity user) throws Exception;
  void addLike(MovieLikeEntity movieLikeEntity) throws Exception;
  void deleteLike(long userId, long movieId) throws Exception;

    void deleteMovieLikeById(long ratingId) throws Exception;
}
