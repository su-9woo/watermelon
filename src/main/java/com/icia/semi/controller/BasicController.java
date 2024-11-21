package com.icia.semi.controller;

import com.icia.semi.dto.ArtistDetailDTO;
import com.icia.semi.dto.MusicDetailDTO;
import com.icia.semi.service.ArtistService;
import com.icia.semi.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasicController {

    private final ArtistService asvc;

    private final MusicService msvc;

    @PostMapping("/artistInfo/{artistId}")
    @ResponseBody
    public ArtistDetailDTO artistInfo(@PathVariable("artistId") int artistId) {
        return asvc.artistId(artistId);
    }

    @PostMapping("/musicInfo/{mId}")
    @ResponseBody
    public MusicDetailDTO musicInfo(@PathVariable("mId") String mId){
        return msvc.musicInfo(mId);
    }

    @PostMapping("/top50")
    @ResponseBody // JSON 응답을 반환하도록 함
    public List<MusicDetailDTO> top50(@RequestParam("category") String category) {
        return msvc.top50(category);
    }

    @GetMapping("/artiInfo")
    public ModelAndView artiInfo(@RequestParam int artistId) {
        return asvc.artiInfo(artistId);
    }

    @PostMapping("/artistTop")
    @ResponseBody
    public List<MusicDetailDTO> artistTop(@RequestParam("artistId") int  artistId) {
        return msvc.artistTop(artistId);
    }

}
