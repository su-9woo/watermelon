package com.icia.test.dto;

// package 아래로 복붙
// 플레이리스트에 음원 저장하기 위한 Entity
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="PLAYLIST")
@SequenceGenerator(name="PLAYLIST_SEQ_GEN", sequenceName="PLAYLIST_SEQ", allocationSize = 1)

public class PlaylistEntity {

    // 플레이리스트 내용 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAYLIST_SEQ_GEN")
    private int PListNum;

    // 음원이 저장될 플레이리스트 번호
    @ManyToOne
    @JoinColumn(name="PInfoNum")
    private PlaylistInfoEntity playlistInfo;

    // 저장될 음원의 아이디
    @ManyToOne
    @JoinColumn(name="MId")
    private MusicDetailEntity music;

    public static PlaylistEntity toEntity (PlaylistDTO dto) {
        PlaylistEntity entity = new PlaylistEntity();
        entity.setPListNum(dto.getPListNum());

        PlaylistInfoEntity playlistInfo = new PlaylistInfoEntity();
        playlistInfo.setPInfoNum(dto.getPListInfoNum());
        entity.setPlaylistInfo(playlistInfo);

        MusicDetailEntity music = new MusicDetailEntity();
        music.setMId(dto.getMId());
        entity.setMusic(music);

        return entity;
    }

}
