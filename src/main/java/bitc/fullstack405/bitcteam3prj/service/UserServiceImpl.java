package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.ImgFileEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.UserEntity;
import bitc.fullstack405.bitcteam3prj.database.repository.ImgFileRepository;
import bitc.fullstack405.bitcteam3prj.database.repository.UserRepository;
import bitc.fullstack405.bitcteam3prj.utils.FileUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService{

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserEntity findByUserId(String userId) throws Exception {
    return  userRepository.findByUserId(userId);
  }
  @Autowired
  private ImgFileRepository imgFileRepository;

  @Autowired
  private FileUtil fileUtil;

  //  로그인 시도 유저 존재 확인
  @Override
  public int isUserInfo(String userId, String userPw) throws Exception {
    int result = userRepository.countByUserIdAndUserPwAndDeletedYn(userId, userPw, 'N');

    return result;
  }

//  Id 중복 여부 확인
  @Override
  public int userIdCheck(String userId) throws Exception {
    int result = userRepository.countByUserId(userId);

    return result;
  }

//  이메일 중복 여부 확인
  @Override
  public int userEmailCheck(String email) throws Exception {
    int result = userRepository.countByEmail(email);

    return result;
  }

//  회원가입 정보 DB 입력
  @Override
  public void insertUser(UserEntity userEntity) throws Exception {
    userRepository.save(userEntity);
  }

//  userId, email로 유저 존재여부 확인
  @Override
  public UserEntity findPassword(String userId, String email) throws Exception {
    UserEntity entity = userRepository.findByUserIdAndEmail(userId, email);
    return entity;
  }

  @Override
  public void updateUserPw(String userId, String userPw) throws Exception {

    userRepository.changePw(userPw, userId);

  }

  @Override
  public UserEntity findUserId(String email, String userPw) throws Exception {

    return userRepository.findByEmailAndUserPw(email, userPw);

  }

  @Override
  public UserEntity findUserIdForProfile(String userId) throws Exception {

    return userRepository.findByUserId(userId);
  }

  @Override
  public void deleteUser(String userId) throws Exception {
    userRepository.signOut(userId);
  }

  @Override
  public UserEntity findByUserIdCheckSignOut(String userId) throws Exception {
    return userRepository.findByUserId(userId);
  }

  @Override
  public void deleteProfileImg(String userId) throws Exception {
    UserEntity user = this.findByUserId(userId);
    String profileImageName = user.getProfileImageName();

    fileUtil.deleteFile(profileImageName);
    user.setProfileImageName("");
    imgFileRepository.deleteByImageName(profileImageName);
    userRepository.save(user);
  }

  @Override
  public void insertUserProfileImg(String userId, HttpServletRequest req, HttpServletResponse resp) throws Exception {

    UserEntity user = this.findByUserId(userId);
    String profileImgName = user.getProfileImageName();
    if(profileImgName == null || profileImgName.isBlank()){
      SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
      profileImgName = "profile_" + user.getId() + "_" +format.format(new Date());
      ImgFileEntity imgFile = new ImgFileEntity();
      imgFile.setImageName(profileImgName);
      user.setProfileImageName(profileImgName);
      imgFileRepository.save(imgFile);
      userRepository.save(user);
    }
    else{
      fileUtil.deleteFile(profileImgName);
    }

    fileUtil.uploadFile(req, profileImgName);
  }
}
