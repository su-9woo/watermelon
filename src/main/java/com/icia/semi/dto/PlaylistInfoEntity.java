package com.icia.test.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "PLAYLISTINFO")
@SequenceGenerator(name = "PLINFO_SEQ_GEN", sequenceName = "PLINFO_SEQ", allocationSize = 1)

public class PlaylistInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLINFO_SEQ_GEN")
    private int PInfoNum;                       // 플레이리스트 고유번호

    @Column
    private String PInfoTitle;                  // 플레이리스트 제목

    @Column
    private String PInfoCoverName;              // 플레이리스트 커버사진 파일명

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime PInfoDate;            // 플레이리스트 생성일

    @Column
    private int PInfoHit;                        // 플레이리스트 조회수

//    // USERT 참조
//    @ManyToOne
//    @JoinColumn(name="PInfoOwner")
//    private UserEntity user;

    public static PlaylistInfoEntity toEntity(PlaylistInfoDTO dto) {
        PlaylistInfoEntity entity = new PlaylistInfoEntity();

        entity.setPInfoNum(dto.getPInfoNum());
        entity.setPInfoTitle(dto.getPInfoTitle());
        entity.setPInfoCoverName(dto.getPInfoCoverName());
        entity.setPInfoDate(dto.getPInfoDate());
        entity.setPInfoHit(dto.getPInfoHit());

//        UserEntity user = new UserEntity();
//        user.setUserId(dto.getPInfoOwner());
//        entity.setUser(user);

        return entity;
    }

}
