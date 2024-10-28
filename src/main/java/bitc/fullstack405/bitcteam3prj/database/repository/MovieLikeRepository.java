package bitc.fullstack405.bitcteam3prj.database.repository;


import bitc.fullstack405.bitcteam3prj.database.entity.MovieEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.MovieLikeEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieLikeRepository extends JpaRepository<MovieLikeEntity, Long> {

  int countByUser_IdAndMovie_Id(Long userId, Long movieId);
  List<MovieLikeEntity> findByUser(UserEntity user);
  MovieLikeEntity findByUserAndMovie(UserEntity user, MovieEntity movie);
  void deleteById(long movieLikeId);

  @Transactional
  void deleteByUser_IdAndMovie_Id(long user_id, long movie_id);
}
