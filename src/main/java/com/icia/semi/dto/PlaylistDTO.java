package com.icia.test.dto;

// package 아래로 복붙
// 음원을 플레이리스테에 담기 위한 DTO

import lombok.Data;

@Data
public class PlaylistDTO {

    private int PListNum;           // 플레이리스트 내용 고유번호
    private String MId;        // 플레이리스트에 담긴 음원 아이디
    private int PListInfoNum;       // 담겨진 플레이리스트 아이디

    public static PlaylistDTO toDTO (PlaylistEntity entity) {
        PlaylistDTO dto = new PlaylistDTO();

        dto.setPListNum(entity.getPListNum());
        dto.setMId(entity.getMusic().getMId());
        dto.setPListInfoNum(entity.getPlaylistInfo().getPInfoNum() );

        return dto;
    }

}
