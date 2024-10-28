package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.MovieEntity;

import java.util.List;

public interface MovieService {
    List<MovieEntity> selectMovieList() throws Exception;

    MovieEntity selectMovieById(long id) throws Exception;
}