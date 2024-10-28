package bitc.fullstack405.bitcteam3prj.database.repository;

import bitc.fullstack405.bitcteam3prj.database.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    BoardEntity findById(long id);

    Page<BoardEntity> findAllByTitleContains(Pageable pageable, String searchValue);
    Page<BoardEntity> findAllByCategoryContains(Pageable pageable, String searchCate);
    Page<BoardEntity> findAllByTitleContainsAndCategoryContains(Pageable pageable, String searchValue, String Category);

    List<BoardEntity> findAllByUser_Id(long id);

}
