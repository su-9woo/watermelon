package com.icia.semi.controller;

import com.icia.semi.dto.BoardDTO;
import com.icia.semi.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService bsvc;

//    private final HttpSession session;

    // bWriteForm : 게시글 작성 페이지로 이동
    @GetMapping("/bWriteForm")
    public String bWriteForm() {
        //if(session.getAttribute("loginId")==null){
        //      return "member/login";
        //  }
        return "board/write";
    }

    // bWrite : 게시글 작성
    @PostMapping("/bWrite")
    public ModelAndView bWrite(BoardDTO board) {
        System.out.println("\n게시글 작성 메소드\n[1] html → controller || board : " + board);
        return bsvc.bWrite(board);
    }

    // bList : 게시글 목록
    @GetMapping("/bList")
    public String bList() {
        return "board/list";
    }

    // bView : 게시글 상세보기
    @GetMapping("/bView/{bNum}")
    public ModelAndView bView(@PathVariable int bNum) {
        System.out.println("\n게시글 상세보기 메소드\n[1] html → controller || bNum : " + bNum);
        return bsvc.bView(bNum);
    }
    // bModify : 게시글 수정
    @PostMapping("/bModify")
    public ModelAndView bModify(@ModelAttribute BoardDTO board){
        System.out.println("\n게시글 수정 메소드\n[1]html → controller : " + board);
        return bsvc.bModify(board);
    }
    // bModify : 게시글 수정
    @GetMapping("/bDelete")
    public ModelAndView bDelete(@ModelAttribute BoardDTO board){
        System.out.println("\n게시글 삭제 메소드\n[1]html → controller : " + board);
        return bsvc.bDelete(board);
    }
}
