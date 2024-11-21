package com.icia.semi.service;

import com.icia.semi.dao.MusicDetailRepository;
import com.icia.semi.dto.ArtistDetailEntity;
import com.icia.semi.dto.MusicDetailDTO;
import com.icia.semi.dto.MusicDetailEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicDetailRepository mrepo;

    private ModelAndView mav;


    public List<MusicDetailDTO> ChartList() {
        List<MusicDetailDTO> dtoList = new ArrayList<>();
        List<Object[]> entityList = mrepo.findAlbum();

        for (Object[] obj : entityList) {
            ArtistDetailEntity artistEntity = (ArtistDetailEntity) obj[0];
            MusicDetailEntity musicEntity = (MusicDetailEntity) obj[1];

            // MusicDetailDTO로 변환
            MusicDetailDTO dto = new MusicDetailDTO();
            dto.setMId(musicEntity.getMId());
            dto.setMTitle(musicEntity.getMTitle());
            dto.setMGenre(musicEntity.getMGenre());
            dto.setMAlbum(musicEntity.getMAlbum());
            dto.setMReleaseDate(musicEntity.getMReleaseDate());
            dto.setMHit(musicEntity.getMHit());
            dto.setMLike(musicEntity.getMLike());
            dto.setMAlbumCoverName(musicEntity.getMAlbumCoverName());
            dto.setArtistName(artistEntity.getArtistName()); // 아티스트 이름 추가
            dto.setArtistId(artistEntity.getArtistId());

            dtoList.add(dto);
        }

        return dtoList;
    }


    public MusicDetailDTO musicInfo(String mId) {

        Optional<MusicDetailEntity> entity = mrepo.findById(mId);
        MusicDetailDTO dto = new MusicDetailDTO();

        if(entity.isPresent()){
            dto = MusicDetailDTO.toDTO(entity.get());
        }

        return dto;
    }



    public List<MusicDetailDTO> top50(String category) {

        List<MusicDetailDTO> dtoList = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 30);
        List<Object[]> entityList = mrepo.findMusicTop1(category, pageable);

        for (Object[] obj : entityList) {
            ArtistDetailEntity artistEntity = (ArtistDetailEntity) obj[0];
            MusicDetailEntity musicEntity = (MusicDetailEntity) obj[1];

            // MusicDetailDTO로 변환
            MusicDetailDTO dto = new MusicDetailDTO();
            dto.setMId(musicEntity.getMId());
            dto.setMTitle(musicEntity.getMTitle());
            dto.setMGenre(musicEntity.getMGenre());
            dto.setMAlbum(musicEntity.getMAlbum());
            dto.setMReleaseDate(musicEntity.getMReleaseDate());
            dto.setMHit(musicEntity.getMHit());
            dto.setMLike(musicEntity.getMLike());
            dto.setMAlbumCoverName(musicEntity.getMAlbumCoverName());
            dto.setArtistName(artistEntity.getArtistName()); // 아티스트 이름 추가
            dto.setArtistId(artistEntity.getArtistId());

            dtoList.add(dto);
        }
        System.out.println(dtoList);

        return dtoList;
    }


    public List<MusicDetailDTO> artistTop(int artistId) {
        List<MusicDetailDTO> dtoList = new ArrayList<>();
        List<Object[]> entityList = mrepo.findMusicTopByArtistId(artistId);

        for (Object[] obj : entityList) {
            ArtistDetailEntity artistEntity = (ArtistDetailEntity) obj[0];
            MusicDetailEntity musicEntity = (MusicDetailEntity) obj[1];

            MusicDetailDTO dto = new MusicDetailDTO();
            dto.setMId(musicEntity.getMId());
            dto.setMTitle(musicEntity.getMTitle());
            dto.setMGenre(musicEntity.getMGenre());
            dto.setMAlbum(musicEntity.getMAlbum());
            dto.setMReleaseDate(musicEntity.getMReleaseDate());
            dto.setMHit(musicEntity.getMHit());
            dto.setMLike(musicEntity.getMLike());
            dto.setMAlbumCoverName(musicEntity.getMAlbumCoverName());
            dto.setArtistName(artistEntity.getArtistName());
            dto.setArtistId(artistEntity.getArtistId());

            dtoList.add(dto);
        }
        System.out.println(dtoList);

        return dtoList;
    }


    public boolean increaseHit(String mtitle)
    { try { MusicDetailEntity music = (MusicDetailEntity) mrepo.findByMTitle(mtitle).orElseThrow(() -> new RuntimeException("Music not found")); music.setMHit(music.getMHit() + 1); mrepo.save(music); return true; } catch (Exception e) { return false; } }



    
    public List<MusicDetailDTO> searchMusic(String searchMusic) {
        List<MusicDetailDTO> dtoList = new ArrayList<>();
        List<Object[]> entityList = mrepo.findSearchMusic(searchMusic);

        for (Object[] obj : entityList) {
            ArtistDetailEntity artistEntity = (ArtistDetailEntity) obj[0];
            MusicDetailEntity musicEntity = (MusicDetailEntity) obj[1];

            MusicDetailDTO dto = new MusicDetailDTO();
            dto.setMId(musicEntity.getMId());
            dto.setMTitle(musicEntity.getMTitle());
            dto.setMGenre(musicEntity.getMGenre());
            dto.setMAlbum(musicEntity.getMAlbum());
            dto.setMReleaseDate(musicEntity.getMReleaseDate());
            dto.setMHit(musicEntity.getMHit());
            dto.setMLike(musicEntity.getMLike());
            dto.setMAlbumCoverName(musicEntity.getMAlbumCoverName());
            dto.setArtistName(artistEntity.getArtistName());
            dto.setArtistId(artistEntity.getArtistId());

            dtoList.add(dto);
        }
        System.out.println("서치 뮤직 : " + dtoList);

        return dtoList;

    }
}

