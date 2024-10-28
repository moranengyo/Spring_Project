
package bitc.fullstack405.bitcteam3prj.controller;

import bitc.fullstack405.bitcteam3prj.database.constant.MovieCategory;
import bitc.fullstack405.bitcteam3prj.database.entity.*;
import bitc.fullstack405.bitcteam3prj.database.repository.MovieRatingRepository;
import bitc.fullstack405.bitcteam3prj.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/movie")
public class MovieBoardController {

    @Autowired
    private MovieBoardService movieBoardService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaginationService paginationService;

    @Autowired
    private MovieLikeService movieLikeService;


    @GetMapping({"/", ""})
    public ModelAndView getMovieBoardList(
            @PageableDefault(page=0, size=10, sort = "id") Pageable pageable,
            @RequestParam(required = false) String searchCate,
            @RequestParam(required = false) String searchTitle
    ) throws Exception {
        ModelAndView mv = new ModelAndView("/movie/movieList");

        Page<MovieBoardEntity> movieBoardList;

        boolean searchCateChk = searchCate == null || searchCate.isEmpty() || searchCate.equals("None");
        boolean searchTitleChk = searchTitle == null || searchTitle.isEmpty();
        
        if(searchCateChk && searchTitleChk){
            movieBoardList = movieBoardService.selectMovieBoardList(pageable);
        }
        else if (searchTitleChk){
            movieBoardList = movieBoardService.selectMovieBoardListByCate(pageable, searchCate);
        }
        else if(searchCateChk){
            movieBoardList = movieBoardService.selectMovieBoardListByTitle(pageable, searchTitle);
        }
        else{
            movieBoardList = movieBoardService.selectMovieBoardListByCateAndTitle(pageable, searchTitle, searchCate);
        }


        mv.addObject("movieCate", MovieCategory.values());
        mv.addObject("movieBoardList", movieBoardList);
        mv.addObject(
                "barList",
                paginationService.getPaginationBarNumbers(
                        pageable.getPageNumber(),
                        movieBoardList.getTotalPages()
                ));

        return mv;
    }

    @GetMapping("/movieinfo/{movieBoardId}")
    public ModelAndView getMovieBoardDetail(
            @PathVariable("movieBoardId") long id,
            HttpSession session)  throws Exception {

        ModelAndView mv = new ModelAndView();

        MovieBoardEntity entity = movieBoardService.findByMovieId(id);
        entity.setViewCnt(entity.getViewCnt() + 1);

        movieBoardService.insertMovieBoard(entity);

        var ratingList = ratingService.findAllByMovieBoard(entity);

        float avg = 0.0f;
        int size = ratingList.size();
        if(size > 0) {
            int sum = 0;
            for (var rating : ratingList) {
                sum += rating.getMovieRating();
            }

            avg = (float) sum / size;
            mv.addObject("avg", avg);
        }

        boolean isLike = false;

        String userId = (String)session.getAttribute("userId");

        if(userId != null) {
            UserEntity user = userService.findByUserId((String) session.getAttribute("userId"));
            int likeCnt = movieLikeService.getMovieLikeCnt(user.getId(), entity.getMovie().getId());
            if(likeCnt > 0) {isLike = true;}
        }


        mv.addObject("movie", entity);
        mv.addObject("ratingList", ratingList);
        mv.addObject("isLike", isLike);

        MovieEntity movieEntity = movieService.selectMovieById(id);

        mv.setViewName("/movie/movieInfo");
        return mv;
    }

    @GetMapping("/{movieBoardId}/rating")
    public String writeMovieRating() throws Exception {
        return "/movie/movieinfo/{movieBoardId}";
    }

    @PostMapping("/{movieBoardId}/rating/{userId}")
    public String writeMovieRating(
            @PathVariable("userId") String userId,
            @PathVariable("movieBoardId") long movieBoardId,
            MovieBoardRatingEntity ratingEntity) throws Exception {

        var user = userService.findByUserId(userId);
        ratingEntity.setMovieBoard(movieBoardService.selectMovieBoardDetail(movieBoardId));
        ratingEntity.setUser(user);

        ratingService.insertRating(ratingEntity);

        return "redirect:/movie/movieinfo/" + movieBoardId;
    }


    @PostMapping("/movieinfo/update/{movieBoardRatingId}")
    public String updateMovieRating(
            @PathVariable("movieBoardRatingId") long movieBoardRatingId,
            MovieBoardRatingEntity ratingEntity,
            long movieBoardId, String userId) throws Exception {

        ratingEntity.setId(movieBoardRatingId);

        MovieBoardEntity movieBoardEntity = movieBoardService.selectMovieBoardDetail(movieBoardId);
        UserEntity userEntity = userService.findByUserId(userId);
        ratingEntity.setMovieBoard(movieBoardEntity);
        ratingEntity.setUser(userEntity);

        ratingService.updateRating(ratingEntity);

        return "redirect:/movie/movieinfo/" + movieBoardId;
    }

    @PostMapping("/movieinfo/delete/{movieBoardRatingId}")
    public String deleteMovieRating(
            @PathVariable("movieBoardRatingId") long movieBoardRatingId,
            long movieBoardId) throws Exception {

        ratingService.deleteById(movieBoardRatingId);

        return "redirect:/movie/movieinfo/" + movieBoardId;
    }

}
