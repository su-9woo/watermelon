package com.icia.test.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MUSICDETAIL")
public class MusicDetailEntity {

    @Id
    private String MId;                     // 음원 아이디

    @Column
    private String MTitle;                  // 제목

    @Column
    private String MGenre;                  // 장르

    @Column
    private String MAlbum;                  // 앨범

    @Column
    private String MReleaseDate;            // 발매일

    @Column
    private int MHit;                       // 조회수

    @Column
    private int MLike;                      // 좋아요

    @Column
    private String MAlbumCoverName;         // 커버 사진 이름

    @ManyToOne
    @JoinColumn(name = "ArtistId")
    private ArtistDetailEntity artist;

    public static MusicDetailEntity toEntity (MusicDetailDTO dto){
        MusicDetailEntity entity = new MusicDetailEntity();

        entity.setMId(dto.getMId());
        entity.setMTitle(dto.getMTitle());
        entity.setMGenre(dto.getMGenre());
        entity.setMAlbum(dto.getMAlbum());
        entity.setMReleaseDate(dto.getMReleaseDate());
        entity.setMHit(dto.getMHit());
        entity.setMLike(dto.getMLike());
        entity.setMAlbumCoverName(dto.getMAlbumCoverName());

        ArtistDetailEntity artist = new ArtistDetailEntity();
        artist.setArtistId(dto.getArtistId());
        entity.setArtist(artist);

        return entity;
    }
}
