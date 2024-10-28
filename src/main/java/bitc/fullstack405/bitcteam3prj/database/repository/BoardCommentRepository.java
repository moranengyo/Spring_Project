package bitc.fullstack405.bitcteam3prj.database.repository;

import bitc.fullstack405.bitcteam3prj.database.entity.BoardCommentEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Long> {
    List<BoardCommentEntity> findAllByUser(UserEntity user);
    List<BoardCommentEntity> findAllByUser_Id(Long id);
    BoardCommentEntity findByContentsIs(String contents);

    List<BoardCommentEntity> findByContentsOrderByContentsAsc(String contents);
    List<BoardCommentEntity> findByContentsOrderByContentsDesc(String contents);

    @Query("SELECT b FROM BoardCommentEntity AS b WHERE b.contents = :contents ")
    BoardCommentEntity findByContents(@Param("contents") BoardCommentEntity boardCommentEntity);

}
