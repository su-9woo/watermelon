package com.icia.semi.controller;

import com.icia.semi.dto.UserDTO;
import com.icia.semi.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {


    private ModelAndView mav = new ModelAndView();

    private final UserService usvc;

    private final HttpSession session;

    // uJoin : 회원가입
    @PostMapping("uJoin")
    public ModelAndView uJoin(@ModelAttribute UserDTO user){
        System.out.println("\n uJoin : 회원가입 메소드\n[1] html → controller || user: " + user);
        return usvc.uJoin(user);
    }

    // uLogin : 로그인
    @PostMapping("/uLogin")
    public ModelAndView uLogin(@ModelAttribute UserDTO user) {
        System.out.println("\n /uLogin : 회원가입 메소드\n[1] html → controller || user: " + user);
        return usvc.uLogin(user);
    }

    // uLogout : 로그아웃
    @GetMapping("/uLogout")
    public String uLogout() {
        session.invalidate();
        return "index";
    }

    // uView : 회원 상세보기
    @GetMapping("/uView/{UserId}")
    public ModelAndView uView(@PathVariable String UserId) {
        System.out.println("\n /uView/{UserId} : 회원 상세보기 메소드\n[1] html → controller || UserId : " + UserId);
        return usvc.uView(UserId);
    }

    // uDelete : 회원탈퇴
    @GetMapping("/uDelete")
    public ModelAndView uDelete(@ModelAttribute UserDTO user) {
        System.out.println("\n uDelete : 회원탈퇴 메소드\n[1] html → controller || user: " + user);
        return usvc.uDelete(user);
    }

    // uModify : 회원정보 수정
    @PostMapping("/uModify")
    public ModelAndView uModify (@ModelAttribute UserDTO user) {
        System.out.println("\n modifyInfo : 이메일/연락처 수정\n[1] html → controller || user : " + user);
        return usvc.uModify(user);
    }

    // modifyLogin : 로그인 정보(비밀번호)수정
    @PostMapping("/modifyLogin")
    public ModelAndView modifyLogin(@RequestParam("UserPw") String UserPw ) {
        System.out.println("\n modifyLogin : 로그인 정보 수정\n[1] html → controller || UserPw :  " + UserPw);
        return usvc.modifyLogin(UserPw);
    }

}
