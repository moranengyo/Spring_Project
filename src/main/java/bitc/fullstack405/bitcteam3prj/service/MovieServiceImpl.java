package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.MovieEntity;
import bitc.fullstack405.bitcteam3prj.database.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

  @Autowired
  private MovieRepository movieRepository;

  @Override
  public List<MovieEntity> selectMovieList() throws Exception {
    return movieRepository.findAll();
  }

  @Override
  public MovieEntity selectMovieById(long id) {
    return movieRepository.findById(id);
  }
}