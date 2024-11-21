package com.icia.semi.controller;


import com.icia.semi.dto.ArtistDetailDTO;
import com.icia.semi.dto.ArtistDetailEntity;
import com.icia.semi.dto.MusicDetailDTO;
import com.icia.semi.dto.MusicDetailEntity;
import com.icia.semi.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService ssvc;

    @GetMapping("/search")
    public ArtistDetailDTO searchA(@RequestParam("keyword") String keyword){
        return ssvc.searchA(keyword);
    }


    @GetMapping("/searchMusic")
    public MusicDetailDTO searchM(@RequestParam("keyword") String keyword) {
        return ssvc.searchM(keyword);
    }

}

