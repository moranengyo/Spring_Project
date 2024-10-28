package bitc.fullstack405.bitcteam3prj.database.repository;

import bitc.fullstack405.bitcteam3prj.database.entity.ImgFileEntity;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImgFileRepository extends JpaRepository<ImgFileEntity, Long> {

    @Transactional
    void deleteByImageName(String imgName) throws Exception;

//  ImgFileEntity findBySavedName(String savedName) throws Exception; // 저장된 이미지 파일 이름을 통해 이미지 찾기

//  @Transactional
//  @Modifying
//  @Query(
//      "DELETE FROM ImgFileEntity AS i WHERE i.savedName = ?1"
//  )
//  void deleteBySavedName(String userId) throws Exception;

}
