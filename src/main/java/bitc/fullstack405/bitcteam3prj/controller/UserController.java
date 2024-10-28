package bitc.fullstack405.bitcteam3prj.controller;

import bitc.fullstack405.bitcteam3prj.database.entity.ImgFileEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.UserEntity;
import bitc.fullstack405.bitcteam3prj.database.repository.ImgFileRepository;
import bitc.fullstack405.bitcteam3prj.database.repository.UserRepository;
import bitc.fullstack405.bitcteam3prj.service.ImageService;
import bitc.fullstack405.bitcteam3prj.service.UserService;
import bitc.fullstack405.bitcteam3prj.utils.CookieUtil;
import bitc.fullstack405.bitcteam3prj.utils.FileUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

  @Autowired
  private UserService userService;
    @Autowired
    private FileUtil fileUtil;

  //  로그인 화면 뷰
  @GetMapping ("/login")
  public ModelAndView  login(HttpServletRequest req, @RequestParam(required = false, value = "error") String error) throws Exception{
    ModelAndView mv = new ModelAndView();

    HttpSession session = req.getSession();

    if (session.getAttribute("userId") != null) {
      error = "logined";
    }

    if (!Objects.isNull(error)) {
      mv.addObject("error", error);
    }


    boolean cookieCheck = false;

    String userId = CookieUtil.readCookie(req, "userId");

    if (!userId.isEmpty()) {
      cookieCheck = true;
    }

    mv.addObject("userId", userId);
    mv.addObject("cookieCheck", cookieCheck);

    mv.setViewName("/login/Login");

    return mv;
  }


//  로그인 프로세스
  @PostMapping("/login")
  public ModelAndView loginProcess(@RequestParam(defaultValue = "N", required = false, value = "rememberId") List<String> rememberId, @RequestParam("userId")String userId, @RequestParam("userPw") String userPw, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    ModelAndView mv = new ModelAndView();

    int result = userService.isUserInfo(userId, userPw);

    UserEntity userEntity = userService.findUserIdForProfile(userId);

    if (userId != null && userPw != null && result == 1) {
      if (rememberId.get(0) != null && rememberId.get(0).equals("Y")) {
        CookieUtil.makeCookie(resp, "userId", userId, (60 * 60 * 24 * 7));
      }
      else {
        CookieUtil.deleteCookie(resp, "userId");
      }

      UserEntity user = userService.findUserIdForProfile(userId);
      var deleted = user.getDeletedYn();


      HttpSession session = req.getSession();

      session.setAttribute("userId", userId);
      session.setAttribute("userPw", userPw);
      session.setAttribute("userEmail", user.getEmail());

      session.setMaxInactiveInterval(60 * 60 * 1);

      mv.setViewName("redirect:/home");
    }
    else {
      mv.setViewName("redirect:/login?error=loginFailed");
    }

    return mv;
  }



//  로그아웃 프로세스
  @RequestMapping("/logout")
  public ModelAndView logout(HttpServletRequest req) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:/login");
    HttpSession session = req.getSession();

    session.removeAttribute("userId");
    session.removeAttribute("userPw");

    session.invalidate();

    return mv;
  }

//  회원가입 뷰 페이지
  @GetMapping("/signIn")
  public ModelAndView signIn(@RequestParam(required = false, value = "error") String error) throws Exception {
    ModelAndView mv = new ModelAndView();

    mv.setViewName("/login/signIn");

    if (error != null) {
      mv.addObject("error", error);
    }

    return mv;
  }

//  화원가입 프로세스
  @PostMapping("/signIn")
  public ModelAndView signInProcess(UserEntity userEntity, @RequestParam("userPwChk") String userPwChk, @RequestParam("userId") String userId, @RequestParam("email") String email) throws Exception {

    ModelAndView mv = new ModelAndView();

    UserEntity entity = userService.findByUserIdCheckSignOut(userId);

    if (Objects.equals(userPwChk, userEntity.getUserPw())) {
        if (userService.userIdCheck(userId) == 0) {
          if (userService.userEmailCheck(email) == 0) {
            userService.insertUser(userEntity);
            mv.setViewName("redirect:/login");
          }
          else {
            mv.setViewName("redirect:/signIn?error=existEmail");
          }
        }
        else {
          mv.setViewName("redirect:/signIn?error=existId");
        }
      }
      else {
        mv.setViewName("redirect:/signIn?error=pwChk");
      }
    return mv;
  }

//  비밀번호 찾기 뷰
  @GetMapping("/findPassword")
  public ModelAndView findPasswordView(@RequestParam(required = false, value = "error") String error) throws Exception {
    ModelAndView mv = new ModelAndView();

    if (error != null) {
      mv.addObject("error", error);
    }

    mv.setViewName("/login/findUserPw");

    return mv;
  }

//  비밀번호 찾기 프로세스 및 변경 뷰페이지 이동
  @PostMapping("/findPassword")
  public ModelAndView findPassword(@RequestParam("userId") String userId, @RequestParam("email") String email) throws Exception {
    ModelAndView mv = new ModelAndView();

    UserEntity entity = userService.findPassword(userId, email);
    try {
      if (entity.getUserId() != null) {
        if (Objects.equals(entity.getUserId(), userId) && Objects.equals(entity.getEmail(), email)) {
          if (entity.getDeletedYn() == 'N') {
            mv.addObject("userId", entity.getUserId());
            mv.addObject("email", entity.getEmail());
            mv.setViewName("redirect:/changePassword");
          }
          else {
            mv.setViewName("redirect:/findPassword?error=signOutUser");
          }
        }
        else {
          mv.setViewName("redirect:/findPassword?error=notFoundUser");
        }
      }
      else {
        mv.setViewName("redirect:/findPassword?error=notFoundUser");
      }
    }
    catch (NullPointerException e) {
      mv.setViewName("redirect:/findPassword?error=notFoundUser");
    }
    catch (Exception e) {
      mv.setViewName("redirect:/findPassword?error=error");
    }

    return mv;
  }

//  비밀번호 변경 뷰
  @GetMapping("/changePassword")
  public ModelAndView changePasswordView(@RequestParam("userId") String userId, @RequestParam(required = false, value = "error") String error) {
    ModelAndView mv = new ModelAndView();

    if (error != null) {
      mv.addObject("error", error);
    }

    mv.setViewName("login/changeUserPw");
    mv.addObject("userId", userId);

    return mv;
  }

//  비밀번호 변경 프로세스
  @PutMapping("/changePassword")
  public ModelAndView changePassword(@RequestParam("userId") String userId, @RequestParam("userPw") String userPw, @RequestParam("userPwChk") String userPwChk) throws Exception {
    ModelAndView mv = new ModelAndView();

    UserEntity userEntity = userService.findByUserIdCheckSignOut(userId);

    if (userEntity.getDeletedYn() == 'N') {
      if (userPw.equals(userPwChk)) {
        userService.updateUserPw(userId, userPw);
        mv.setViewName("redirect:/login");
      }
      else {
        mv.setViewName("redirect:/changePassword?error=pwChk");
      }
    }
    else {
      mv.setViewName("redirect:/changePassword?error=signOutUser");
    }

    return mv;
  }

//  id 찾기 뷰
  @GetMapping("/findId")
  public ModelAndView findIdView(@RequestParam(required = false, value = "error") String error) throws Exception {

    ModelAndView mv = new ModelAndView();

    if (error != null) {
      mv.addObject("error", error);
    }

    mv.setViewName("/login/findUserId");

    return mv;
  }

//  id 찾기 프로세스
  @ResponseBody
  @PostMapping("/findId")
  public ModelAndView findId(@RequestParam("email") String email, @RequestParam("userPw") String userPw, @RequestParam(required = false, value = "error") String error) throws Exception {
    ModelAndView mv = new ModelAndView();

    if (error != null) {
      mv.addObject("error", error);
    }

    UserEntity userEntity = userService.findUserId(email, userPw);


    if (userEntity != null && email.equals(userEntity.getEmail()) && userPw.equals(userEntity.getUserPw())) {
      if (userEntity.getDeletedYn() == 'N') {
        mv.addObject("userId", userEntity.getUserId());
        mv.setViewName("/login/foundUserId");
      }
      else {
        mv.setViewName("redirect:/findId?error=signOutUser");
      }
    }
    else {
        mv.setViewName("redirect:/findId?error=notFoundUser");
    }

    return mv;
  }

//  마이페이지(프로필)
  @GetMapping("profile/{userId}")
  public ModelAndView userProfile(HttpServletRequest req, @PathVariable("userId") String userId, @RequestParam(required = false, value = "error") String error) throws Exception {
    ModelAndView mv = new ModelAndView();

    if (error != null) {
      mv.addObject("error", error);
    }

    HttpSession session = req.getSession();

    String findImg = userId + ".jpg";

    UserEntity userEntity = userService.findUserIdForProfile(userId);


    if(session.getAttribute("userId") != null) {
      mv.addObject("user", userEntity);

      if (userEntity != null) {

        if (userEntity.getDeletedYn() == 'N') {
          mv.addObject("userInfo", userEntity);
          if (session.getAttribute("userId").equals(userId)) { // 자신의 프로필인지 타인의 프로필인지 확인
            mv.addObject("me", true);
            mv.setViewName("/user/myProfile");
          }
          else if (!session.getAttribute("userId").equals(userId)) {
            mv.addObject("me", false);
            mv.setViewName("/user/myProfile");
          }
        }
        else if (userEntity.getDeletedYn() == 'Y') {
          mv.addObject("me", false);
          mv.addObject("signOutUserMsg", "(탈퇴한 회원)");
          mv.addObject("profileImage", "/image/DefaultProfileImage.jpg");
          mv.setViewName("/user/myProfile");
        }
      }
      else {
        mv.setViewName("redirect:/home?error=notFoundUser");
      }
    }
    else {
      mv.setViewName("redirect:/login");
    }
    return mv;
  }

//  마이페이지(비밀번호 변경)
  @PostMapping("changePasswordProfile/{userId}")
  public ModelAndView changePw(@RequestParam("userPw") String userPw, @RequestParam("changePw") String changePw, @PathVariable("userId") String userId, @RequestParam("changePwChk") String changePwChk, HttpServletRequest req) throws Exception {
    ModelAndView mv = new ModelAndView();

    HttpSession session = req.getSession();

    UserEntity userEntity = userService.findUserIdForProfile((String) session.getAttribute("userId"));

    if (session.getAttribute("userId") != null) {
      if (userEntity.getDeletedYn() == 'N') {
        if (userPw.equals(userEntity.getUserPw())) {
          if(session.getAttribute("userId").equals(userEntity.getUserId())) {
            if (changePw.equals(changePwChk)) {
              userService.updateUserPw((String)session.getAttribute("userId"), changePw);
              mv.setViewName("redirect:/user/myProfile");
            }
            else {
              mv.setViewName("redirect:/profile/" + userEntity.getUserId() + "?error=pwChk");
            }
          }
          else {
            mv.setViewName("redirect:/profile/" + userEntity.getUserId() + "?error=notYourProfile");
          }
        }
        else {
          mv.setViewName("redirect:/profile/" + userEntity.getUserId() + "?error=pwNotMatch");
        }
      }
      else {
        mv.setViewName("redirect:/main?error=signOutUser");
      }
    }
    else if (session.getAttribute("userId") != null) {
      mv.setViewName("redirect:/login");
    }
    return mv;
  }

////  회원탈퇴 뷰(GET) (기본 뷰)
//  @GetMapping("/signOut")
//  public String signOutPwChk() throws Exception {
//    return "user/signOutTest";
//  }


//  마이페이지(프로필) 탈퇴 취소 시 넘어옴
  @PostMapping("/profile/{userId}")
  public ModelAndView signOutCancel(HttpServletRequest req) throws Exception {
    ModelAndView mv = new ModelAndView();
    HttpSession session = req.getSession();


    mv.setViewName("redirect:/profile/" + session.getAttribute("userId"));

    return mv;
  }


//  회원탈퇴
  @DeleteMapping("/signOut")
  public ModelAndView deleteUser(HttpServletRequest req, @RequestParam("userPw") String userPw) throws Exception {
    ModelAndView mv = new ModelAndView();

    HttpSession session = req.getSession();

    String userId = (String)session.getAttribute("userId");

    if (session.getAttribute("userId") != null) {
      UserEntity userEntity = userService.findUserIdForProfile(userId);
      if (userPw.equals(userEntity.getUserPw())){
        if (userEntity.getDeletedYn() == 'N') {
          userService.deleteUser(userId);
          session.invalidate();
          mv.setViewName("redirect:/");
        }
        else {
          mv.setViewName("redirect:/login?error=alrdyOutUser");
        }
      }
      else {
        mv.setViewName("redirect:/profile/" + userEntity.getUserId() + "?error=pwChk");
      }
    }
    else {
      mv.setViewName("redirect:/login");
    }
    return mv;
  }

  //  프로필 이미지 업로드(프사수정)
  @PutMapping("/uploadProfileImg")
  public ModelAndView uploadProfileImg(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    ModelAndView mv = new ModelAndView();

    HttpSession session = req.getSession();

    String userId = (String)session.getAttribute("userId");


    userService.insertUserProfileImg(userId, req, resp);


    mv.setViewName("redirect:/user/myProfile");

    return mv;
  }

  //  프로필 이미지 삭제
  @PostMapping("/deleteProfileImg")
  public ModelAndView deleteProfileImg(HttpServletRequest req) throws Exception{

    HttpSession session = req.getSession();
    ModelAndView mv = new ModelAndView();

    userService.deleteProfileImg((String)session.getAttribute("userId"));

    mv.setViewName("redirect:/user/myProfile");

    return mv;
  }
}
