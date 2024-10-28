package bitc.fullstack405.bitcteam3prj.database.repository;

import bitc.fullstack405.bitcteam3prj.database.entity.BoardLikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardLikeRepository extends JpaRepository<BoardLikeEntity, Long> {
    int countByUser_IdAndBoard_Id(long userId, long boardId);
    int countByBoard_Id(long boardId);


    BoardLikeEntity findByUser_IdAndBoard_Id(long userId, long boardId);
    Page<BoardLikeEntity> findAllByUser_Id(Pageable pageable, long userId);
    List<BoardLikeEntity> findAllByUser_Id(long userId);
    List<BoardLikeEntity> findAllByBoard_Id(long boardId);

}
