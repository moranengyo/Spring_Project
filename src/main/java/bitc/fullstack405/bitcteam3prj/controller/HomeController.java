package bitc.fullstack405.bitcteam3prj.controller;

import bitc.fullstack405.bitcteam3prj.database.constant.BoardCategory;
import bitc.fullstack405.bitcteam3prj.database.entity.BoardEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.MovieBoardEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.MovieEntity;
import bitc.fullstack405.bitcteam3prj.service.BoardService;
import bitc.fullstack405.bitcteam3prj.service.MovieBoardService;
import bitc.fullstack405.bitcteam3prj.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class HomeController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieBoardService movieBoardService;

    @Autowired
    private BoardService boardService;


    @GetMapping({"", "/", "/home", "/Home"})
    private ModelAndView mainHome() throws Exception{
        ModelAndView mv = new ModelAndView("/main/mainHome");

        List<MovieEntity> movieList = movieService.selectMovieList();
        List<BoardEntity> boardList = boardService.selectBoardList();
        List<MovieBoardEntity> movieBoardList = movieBoardService.selectMovieBoardList();

        List<MovieEntity> showingMovieList = new ArrayList<>();
        List<MovieEntity> recommendedMovieList = new ArrayList<>();

        List<BoardEntity> noticeBoardLatestList = new ArrayList<>();
        List<MovieBoardEntity> movieBoardLatestList = new ArrayList<>();
        List<BoardEntity> reviewBoardLatestList = new ArrayList<>();
        List<BoardEntity> publicBoardLatestList = new ArrayList<>();

        Random rand = new Random();

        for(int i = 0; i < 7; i++){
            int randIdx = rand.nextInt(movieList.size());

            MovieEntity randMovie = movieList.get(randIdx);
            showingMovieList.add(randMovie);
            movieList.remove(randIdx);
        }

        for(int i = 0; i < 3; i++){
            int randIdx = rand.nextInt(movieList.size());

            MovieEntity randMovie = movieList.get(randIdx);
            recommendedMovieList.add(randMovie);
            movieList.remove(randIdx);
        }

        for(int i = 0; i < 3; i++){
            movieBoardLatestList.add(movieBoardList.get(i));
        }


        for(var board : boardList){
            if(board.getCategory().equals(BoardCategory.CATE_1.getDescription())){
                if(noticeBoardLatestList.size() >= 3){
                    continue;
                }
                noticeBoardLatestList.add(board);
            }else if(board.getCategory().equals(BoardCategory.CATE_2.getDescription())){
                if(reviewBoardLatestList.size() >= 3){
                    continue;
                }
                reviewBoardLatestList.add(board);
            }else{
                if(publicBoardLatestList.size() >= 3){
                    continue;
                }
                publicBoardLatestList.add(board);
            }
        }


        mv.addObject("showingMovieList", showingMovieList);
        mv.addObject("recommendedMovieList", recommendedMovieList);

        mv.addObject("noticeBoardLatestList", noticeBoardLatestList);
        mv.addObject("movieBoardLatestList", movieBoardLatestList);
        mv.addObject("reviewBoardLatestList",reviewBoardLatestList);
        mv.addObject("publicBoardLatestList", publicBoardLatestList);


        return mv;
    }
}
