package bitc.fullstack405.bitcteam3prj.database.repository;

import bitc.fullstack405.bitcteam3prj.database.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

  int countByUserIdAndUserPw(String userId, String userPw); // 로그인 회원정보 확인

  int countByUserIdAndUserPwAndDeletedYn(String userId, String userPw, char deleteYn);

  int countByUserId(String userId); // 회원가입 UserId 존재여부 확인

  int countByEmail(String email); // 회원가입 Email 존재여부 확인


  UserEntity findByUserIdAndEmail(String userId, String email);

  @Transactional
  @Modifying
  @Query("UPDATE UserEntity AS u SET u.userPw = ?1 WHERE u.userId = ?2")
  void changePw(String userPw, String userId); // pw찾기 - pw 변경

  UserEntity findByEmailAndUserPw(String email, String userPw);

  UserEntity findByUserId(String userId);

  @Transactional
  @Modifying
  @Query("UPDATE UserEntity AS u SET u.deletedYn = 'Y' WHERE u.userId = ?1")
  void signOut(String userId);

  @Transactional
  @Modifying
  @Query ("UPDATE UserEntity AS u SET u.profileImageName = null WHERE u.userId = ?1")
  void deletingUserProfileImg(String userId);

  @Transactional
  @Modifying
  @Query ("UPDATE UserEntity AS u SET u.profileImageName = ?1 WHERE u.userId = ?2")
  void insertUserProfile(String profileImageName, String userId);

}
