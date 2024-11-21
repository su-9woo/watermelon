
package com.icia.semi.controller;

import com.icia.semi.dto.CommentDTO;
import com.icia.semi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService csvc;


    @PostMapping("/cList")
    public List<CommentDTO> cList(@RequestParam("CBNum") int CBNum){
        System.out.println("\n댓글 목록\n[1]html → controller : " + CBNum);
        return csvc.cList(CBNum);
    }

    // cWrite, cModify
    @PostMapping("/cWrite")
    public List<CommentDTO> cWrite(@ModelAttribute CommentDTO comment){
        System.out.println("\n댓글 작성\n[1]html → controller : " + comment);
        return csvc.cWrite(comment);
    }

    // cDelete
    @PostMapping("/cDelete")
    public List<CommentDTO> cDelete(@ModelAttribute CommentDTO comment){
        System.out.println("\n댓글 삭제\n[1]html → controller : " + comment);
        return csvc.cDelete(comment);
    }

    // 내 댓글 보기
    @PostMapping("/myCommentInfo/{UserId}")
    public List<CommentDTO> myCommentInfo(@PathVariable String UserId) {
        System.out.println("\n내 게시글 불러오기 메소드 || UserId : " + UserId);;
        return csvc.myCommentList(UserId);
    }


}