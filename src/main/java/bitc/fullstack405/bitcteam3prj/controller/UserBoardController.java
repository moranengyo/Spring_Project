package bitc.fullstack405.bitcteam3prj.controller;

import bitc.fullstack405.bitcteam3prj.database.entity.*;
import bitc.fullstack405.bitcteam3prj.database.repository.BoardLikeRepository;
import bitc.fullstack405.bitcteam3prj.service.*;


import bitc.fullstack405.bitcteam3prj.database.entity.BoardCommentEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.BoardEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.BoardLikeEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.UserEntity;
import bitc.fullstack405.bitcteam3prj.service.BoardCommentService;
import bitc.fullstack405.bitcteam3prj.service.BoardService;
import bitc.fullstack405.bitcteam3prj.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserBoardController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieLikeService movieLikeService;

    @Autowired
    private BoardLikeService boardLikeService;

    @Autowired
    private BoardCommentService boardCommentService;
    @Autowired
    private BoardLikeRepository boardLikeRepository;

    //    유저가 작성한 게시글, 댓글 리스트
    @GetMapping("/myProfile")
    public ModelAndView userBoardList(HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("/user/myProfile");

        UserEntity user = userService.findByUserId((String) session.getAttribute("userId"));

        List<BoardEntity> boardList = boardService.findAllByUserId(user.getId());

        List<BoardCommentEntity> commentList = boardCommentService.findAllByUserId(user.getId());

        List<BoardLikeEntity> boardLikeList = boardLikeService.findAllByUserId(user.getId());

        List<BoardLikeEntity> likeBoardList = new ArrayList<>();
        for(var b : boardLikeList){
            if(b.getLikeYn().equals("Y")){
                likeBoardList.add(b);
            }
        }

        List<MovieLikeEntity> movieLikeList = movieLikeService.findByUser(user);

        mv.addObject("boardList", boardList);
        mv.addObject("commentList", commentList);
        mv.addObject("likeList", likeBoardList);
        mv.addObject("movieList", movieLikeList);

        mv.addObject("userInfo", user);

        return mv;
    }


    //    유저가 추천한 게시글 리스트
    @ResponseBody
    @GetMapping("/myProfile/{userId}")
    public ModelAndView userLikeBoardList(HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("/user/myProfile");


        return null;
    }

    //    유저가 북마크한 영화 리스트
    @GetMapping("/boardMovieBookmark/{userId}")
    public ModelAndView movieBookmarkBoardList(
            @PathVariable("userId") String userId) throws Exception {

        ModelAndView mv = new ModelAndView();
        UserEntity entity = userService.findByUserId(userId);


        var movieLikeList = movieLikeService.findByUser(entity);

        List<MovieEntity> movieList = new ArrayList<>();

        for (MovieLikeEntity movieLike : movieLikeList) {
            MovieEntity movie = movieService.selectMovieById(movieLike.getMovie().getId());
            movieList.add(movie);
        }


        mv.addObject("movieList", movieList);
        return mv;
    }


    @PostMapping("/boardMovieBookmark/{userId}/{movieId}")
    public String addMovieBookmark(
            @PathVariable("userId") String userId,
            @PathVariable("movieId") long movieId,
            MovieLikeEntity movieLikeEntity,
            boolean bookMark) throws Exception {

        var user = userService.findByUserId(userId);
        movieLikeEntity.setMovie(movieService.selectMovieById(movieId));
        movieLikeEntity.setUser(user);

        if (bookMark) {
            movieLikeService.addLike(movieLikeEntity);
        } else {
            movieLikeService.deleteLike(user.getId(), movieId);
        }

        return "redirect:/movie/movieinfo/" + movieId;
    }


    @ResponseBody
    @PostMapping("/like/{boardId}")
    public Object addBoardLike(HttpSession session,
                               @PathVariable long boardId,
                               @RequestParam String YN,
                               @RequestParam int onOff
                               ) throws Exception {

        String userId = (String) session.getAttribute("userId");
        UserEntity user = userService.findByUserId(userId);
        BoardLikeEntity boardLike = boardLikeService.findByUserIdAndBoardId(user.getId(), boardId);

        if(onOff == 1) {
            if (boardLike != null) {
                boardLike.setLikeYn(YN);
            } else {
                BoardEntity board = boardService.selectBoardDetail(boardId);

                boardLike = new BoardLikeEntity();
                boardLike.setUser(user);
                boardLike.setBoard(board);
                boardLike.setLikeYn(YN);
            }

            boardLikeService.updateBoardLike(boardLike);

        }else{
            boardLikeService.deleteBoardLike(boardLike);
        }

        Map<String, String> dataMap = new HashMap<>();
        return dataMap;
    }

    @PostMapping("/myprofile/boardlike/{boardLikeId}")
    @ResponseBody
    public Object deleteBoardLike(@PathVariable long boardLikeId) throws Exception {
        boardLikeService.deleteBoardLikeById(boardLikeId);

        Map<String, String> dataMap = new HashMap<>();
        return dataMap;
    }

    @PostMapping("/myprofile/movielike/{movieLikeId}")
    @ResponseBody
    public Object deleteMovieLike(@PathVariable long movieLikeId) throws Exception {
        movieLikeService.deleteMovieLikeById(movieLikeId);

        Map<String, String> dataMap = new HashMap<>();
        return dataMap;
    }
}

