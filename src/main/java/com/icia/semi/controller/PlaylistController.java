/////////////////////////////////////////////////////////////////////////////////////// PlaylistController
package com.icia.semi.controller;

import com.icia.semi.dto.PlaylistInfoDTO;
import com.icia.semi.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class PlaylistController {

    private final PlayListService psvc;

    @GetMapping("/goToPlaylist/{pinfoNum}")
    public ModelAndView goToPlaylist(@PathVariable("pinfoNum") int pNum){
        System.out.println("1 : " + pNum);
        return psvc.goToPlaylist(pNum);
    }

    // 플레이리스트 삭제
    @GetMapping("/deletePlaylist/{pinfoNum}")
    public ModelAndView deletePlaylist(@PathVariable("pinfoNum") int pNum) {
        System.out.println("플레이리스트 삭제 확인, controller → service ");
        System.out.println(pNum);
        return psvc.deletePlaylist(pNum);
    }
}