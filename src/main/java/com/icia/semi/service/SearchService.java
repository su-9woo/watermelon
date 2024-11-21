package com.icia.semi.service;

import com.icia.semi.dao.ArtistDetailRepository;
import com.icia.semi.dao.MusicDetailRepository;
import com.icia.semi.dto.ArtistDetailDTO;
import com.icia.semi.dto.ArtistDetailEntity;
import com.icia.semi.dto.MusicDetailDTO;
import com.icia.semi.dto.MusicDetailEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final MusicDetailRepository mrepo;

    private final ArtistDetailRepository arepo;


    public ArtistDetailDTO searchA(String keyword) {

        ArtistDetailEntity entity = arepo.findArtistInfo(keyword);

        ArtistDetailDTO dto = ArtistDetailDTO.toDTO(entity);

        return dto;

    }

    public MusicDetailDTO searchM(String keyword) {
        MusicDetailEntity mEntity = mrepo.findMusicInfo(keyword);

        MusicDetailDTO mDto = MusicDetailDTO.toDTO(mEntity);

        return mDto;
    }

}

