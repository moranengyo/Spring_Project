package bitc.fullstack405.bitcteam3prj.database.repository;


import bitc.fullstack405.bitcteam3prj.database.entity.MovieBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieBoardRepository extends JpaRepository<MovieBoardEntity, Long> {

  MovieBoardEntity findById(long id) throws Exception;


    Page<MovieBoardEntity> findAllByMovie_MovieNameContains(Pageable pageable, String searchTitle);

    Page<MovieBoardEntity> findAllByMovie_MovieCateContains(Pageable pageable, String searchCategory);

    Page<MovieBoardEntity> findAllByMovie_MovieNameContainsAndMovie_MovieCateContains(Pageable pageable, String searchTitle, String searchCate);
}