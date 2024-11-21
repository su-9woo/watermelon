package com.icia.test.dto;

import lombok.Data;

@Data
public class ArtistDetailDTO {

    private int ArtistId;                   // 아티스트 아이디
    private String ArtistName;              // 아티스트 이름
    private String ArtistDate;              // 데뷔일
    private String ArtistAgency;               // 소속사
    private String ArtistProfileName;       // 아티스트 프로필 파일명
    private String ArtistType;


    public static ArtistDetailDTO toDTO (ArtistDetailEntity entity){
        ArtistDetailDTO dto = new ArtistDetailDTO();

        dto.setArtistId(entity.getArtistId());
        dto.setArtistDate(entity.getArtistDate());
        dto.setArtistAgency(entity.getArtistAgency());
        dto.setArtistName(entity.getArtistName());
        dto.setArtistType(entity.getArtistType());
        dto.setArtistProfileName(entity.getArtistProfileName());

        return dto;
    }
}
