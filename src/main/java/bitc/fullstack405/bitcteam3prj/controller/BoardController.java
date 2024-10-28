package bitc.fullstack405.bitcteam3prj.controller;

import bitc.fullstack405.bitcteam3prj.database.constant.BoardCategory;
import bitc.fullstack405.bitcteam3prj.database.entity.BoardEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.BoardLikeEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.UserEntity;
import bitc.fullstack405.bitcteam3prj.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaginationService paginationService;

    @Autowired
    private BoardLikeService boardLikeService;

    //    게시글 전체 목록
    @GetMapping({"/", ""})
    public ModelAndView selectBoardList(
            @PageableDefault(size=10, sort="createdAt") Pageable pageable,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) String searchCate
    ) throws Exception {
        ModelAndView mv = new ModelAndView("/board/boardList");

        Page<BoardEntity> boardList = null;

        boolean searchCateNullChk = searchCate == null || searchCate.isEmpty() || searchCate.equals("None");
        boolean searchTitleNullChk = searchValue == null || searchValue.isEmpty();

        if(searchCateNullChk && searchTitleNullChk){
            boardList = boardService.selectBoardList(pageable);
        }
        else if (searchTitleNullChk){
            boardList = boardService.selectBoardListByCate(pageable, searchCate);
        }
        else if(searchCateNullChk){
            boardList = boardService.selectBoardListBySearchValue(pageable, searchValue);
        }
        else{
            boardList = boardService.selectBoardListBySearchValueAndSearchCate(pageable, searchValue, searchCate);
        }




        mv.addObject("boardType", BoardCategory.values());
        mv.addObject("boardList", boardList);
        mv.addObject("barList",
                paginationService.getPaginationBarNumbers(
                pageable.getPageNumber(),
                boardList.getTotalPages()
        ));

        return mv;
    }


//    게시글 상세보기
    @GetMapping("/{boardId}")
    public ModelAndView selectBoardDetail(
            HttpSession session,
            @PathVariable("boardId") Long boardId) throws Exception {
        ModelAndView mv = new ModelAndView("board/boardDetail");
        BoardEntity board = boardService.selectBoardDetail(boardId);
        board.setVisitCnt(board.getVisitCnt() + 1);
        boardService.updateBoard(board);

        BoardLikeEntity boardLike = null;
        String userId =(String)session.getAttribute("userId");
        if(!(userId == null  || userId.isEmpty())) {
            boardLike = boardLikeService.findByUserIdAndBoardId(
                    userService.findByUserId((String) session.getAttribute("userId")).getId(),
                    boardId
            );
        }

        String userLike = "";
        if(boardLike != null){
            userLike = boardLike.getLikeYn();
        }

        var boardLikeList = boardLikeService.findAllByBoardId(boardId);
        List<BoardLikeEntity> dislikeList =  new ArrayList<>();
        List<BoardLikeEntity> likeList = new ArrayList<>();
        if(boardLikeList != null) {
            for (var like : boardLikeList) {
                if (like.getLikeYn().equals("N")) {
                    dislikeList.add(like);
                }else{
                    likeList.add(like);
                }
            }
        }



        mv.addObject("userId", userId);
        mv.addObject("userLike", userLike);
        mv.addObject("disLikeCnt", dislikeList.size());
        mv.addObject("likeCnt", likeList.size());
        mv.addObject("board" , board);
        return mv;
    }

    //    게시글 등록(view)
    @GetMapping("/write")
    public ModelAndView insertBoard(HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("board/boardWrite");

        UserEntity user = userService.findByUserId((String)session.getAttribute("userId"));

        mv.addObject("user", user);
        mv.addObject("boardType", BoardCategory.values());


        return mv;
    }

//    게시글 등록 처리
    @PostMapping("/write")
    public String insertBoard(
            @RequestParam String userId,
            @RequestParam String title,
            @RequestParam String category,
            @RequestParam String content,
            @RequestParam(required = false) String option) throws Exception {
        BoardEntity board = new BoardEntity();

        UserEntity user = userService.findByUserId(userId);
        board.setTitle(title);
        board.setCategory(category);
        board.setContent(content);
        board.setWarning(option == null || option.isEmpty() ? "" : option);
        board.setUser(user);

        boardService.insertBoard(board);

        return "redirect:/board/";
    }




//    게시물 삭제
    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable("boardId") long boardId) throws Exception {
        boardService.deleteBoardById(boardId);

        return "redirect:/board/";
    }

//    게시글 수정
    @PutMapping("/{boardId}")
    public String updateBoard(long boardId, String title, String content) throws Exception {
        BoardEntity board = boardService.selectBoardDetail(boardId);
        board.setTitle(title);
        board.setContent(content);
        board.setId(boardId);

        boardService.updateBoard(board);

        return "redirect:/board/" + boardId;
    }



    @ResponseBody
    @PostMapping("/myprofile/{boardId}")
    public Object deleteBoardAtMyProfile(@PathVariable("boardId") long boardId) throws Exception{

        boardService.deleteBoardById(boardId);
        Map<String, Long> data = new HashMap<>();

        data.put("boardId", boardId);

        return data;
    }

}
