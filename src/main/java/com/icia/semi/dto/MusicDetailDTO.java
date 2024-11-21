package com.icia.test.dto;

import lombok.Data;

@Data
public class MusicDetailDTO {

    private String MId;                     // 음원 아이디
    private String MTitle;                  // 제목
    private String MGenre;                  // 장르
    private String MAlbum;                  // 앨범
    private String MReleaseDate;            // 발매일
    private int MHit;                       // 조회수
    private int MLike;                      // 좋아요
    private String MAlbumCoverName;         // 커버 사진 이름

    private String artistName;              // 조인 아티스트 이름

    private int ArtistId;                  // 아티스트 아이디

    public static MusicDetailDTO toDTO (MusicDetailEntity entity){
        MusicDetailDTO dto = new MusicDetailDTO();

        dto.setMId(entity.getMId());
        dto.setMTitle(entity.getMTitle());
        dto.setMGenre(entity.getMGenre());
        dto.setMAlbum(entity.getMAlbum());
        dto.setMReleaseDate(entity.getMReleaseDate());
        dto.setMHit(entity.getMHit());
        dto.setMLike(entity.getMLike());
        dto.setMAlbumCoverName(entity.getMAlbumCoverName());
        dto.setArtistId(entity.getArtist().getArtistId());

        return dto;

    }

}
