package com.icia.test.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ARTISTDETAIL")
public class ArtistDetailEntity {

    @Id
    private int ArtistId;                   // 아티스트 아이디

    @Column
    private String ArtistName;              // 아티스트 이름

    @Column
    private String ArtistDate;              // 데뷔일

    @Column
    private String ArtistAgency;               // 소속사

    @Column
    private String ArtistProfileName;       // 아티스트 프로필 파일명

    @Column
    private String ArtistType;

    public static ArtistDetailEntity toEntity (ArtistDetailDTO dto){
        ArtistDetailEntity entity = new ArtistDetailEntity();

        entity.setArtistId(dto.getArtistId());
        entity.setArtistDate(dto.getArtistDate());
        entity.setArtistAgency(dto.getArtistAgency());
        entity.setArtistName(dto.getArtistName());
        entity.setArtistType(dto.getArtistType());
        entity.setArtistProfileName(dto.getArtistProfileName());

        return entity;
    }
}
