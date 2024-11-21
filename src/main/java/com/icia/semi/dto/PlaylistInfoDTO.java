package com.icia.test.dto;

// package 아래로 복붙
// 생성될 플레이리스트의 정보
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class PlaylistInfoDTO {
    private int PInfoNum;                   // 플레이리스트 고유번호
    // private String PInfoOwner;              // 플레이리스트 만든 회원 아이디
    private String PInfoTitle;              // 플레이리스트 제목
    private MultipartFile PInfoCover;       // 커버 사진
    private String PInfoCoverName;          // 커버 사진 파일명
    private LocalDateTime PInfoDate;        // 생성일
    private int PInfoHit;                   // 플레이리스트 조회수

    public static PlaylistInfoDTO toDTO(PlaylistInfoEntity entity){
        PlaylistInfoDTO dto = new PlaylistInfoDTO();

        dto.setPInfoNum(entity.getPInfoNum());
        dto.setPInfoTitle(entity.getPInfoTitle());  // 주석
        dto.setPInfoCoverName(entity.getPInfoCoverName());
        dto.setPInfoDate(entity.getPInfoDate());
        dto.setPInfoHit(entity.getPInfoHit());

        //dto.setPInfoOwner(entity.getUser().getUserId());

        return dto;
    }

}
