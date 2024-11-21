package com.icia.semi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageMoveController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/playlistForm")
    public String playlist(){
        return "playList";
    }

    // uJoinForm : 회원가입 페이지로
    @GetMapping("uJoinForm")
    public String uJoinForm() {
        return "user/join";
    }

    // uLoginForm
    @GetMapping("/uLoginForm")
    public String uLoginForm() {
        return "user/login";
    }

    // uModLogin{UserId}
    @GetMapping("/uModLogin{UserId}")
    public String uModLogin() {
        return "user/modLogin";
    }

    // b_Top50Form
    @GetMapping("/bTop50Form")
    public String bTop50Form(){
        return "/top/balladTop50";
    }

    // dTop50Form
    @GetMapping("/dTop50Form")
    public String dTop50Form(){
        return "/top/danceTop50";
    }

    // iTop50Form
    @GetMapping("/iTop50Form")
    public String iTop50Form(){
        return "/top/indieTop50";
    }

    // hTop50Form
    @GetMapping("/hTop50Form")
    public String hTop50Form(){
        return "/top/hiphopTop50";
    }

    // rTop50Form
    @GetMapping("/rTop50Form")
    public String rTop50Form(){
        return "/top/RBTop50";
    }

    // tTop50Form
    @GetMapping("/tTop50Form")
    public String tTop50Form(){
        return "/top/trotTop50";
    }

    // banTop50Form
    @GetMapping("/banTop50Form")
    public String banTop50Form(){
        return "/top/bandTop50";
    }

    // pTop50Form
    @GetMapping("/pTop50Form")
    public String pTop50Form(){
        return "/top/popTop50";
    }


    // pTop50Form
    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
