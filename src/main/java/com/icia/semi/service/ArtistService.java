package com.icia.semi.service;

import com.icia.semi.dao.ArtistDetailRepository;
import com.icia.semi.dto.ArtistDetailDTO;
import com.icia.semi.dto.ArtistDetailEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private ModelAndView mav;

    private final ArtistDetailRepository arepo;

    public ArtistDetailDTO artistId(int artistId) {

        Optional<ArtistDetailEntity> entity = arepo.findById(artistId);
        ArtistDetailDTO artist = new ArtistDetailDTO();

        if(entity.isPresent()){
            artist = ArtistDetailDTO.toDTO(entity.get());
        }

        return artist;
    }

    public List<ArtistDetailDTO> artistList() {

        List<ArtistDetailDTO> dtoList = new ArrayList<>();
        List<ArtistDetailEntity> entityList = arepo.findAll();

        for(ArtistDetailEntity en : entityList){
            dtoList.add(ArtistDetailDTO.toDTO(en));
        }

        return dtoList;
    }

    public ModelAndView artiInfo(int artistId) {
        mav = new ModelAndView();

        Optional<ArtistDetailEntity> entity = arepo.findById(artistId);

        if(entity.isPresent()){
            ArtistDetailDTO artist = ArtistDetailDTO.toDTO(entity.get());
            mav.addObject("artist", artist);
            mav.setViewName("music/artistInfo");
        } else {
            mav.setViewName("redirect:/index");
            System.out.println("아티스트 정보 빼오기 완전 실패");
        }

        return mav;
    }
}
