package com.icia.semi.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="USERBOARD")
@SequenceGenerator(name="UB_SEQ_GEN", sequenceName = "UB_SEQ", allocationSize = 1)
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UB_SEQ_GEN")
    private int BNum;                       // 게시글 번호, PK; 시퀀스 사용해서 지정

    @Column
    private String BType;                   // 게시글 유형 (자유, 아티스트 추천, 플레이리스트/노래 추천)

    @Column
    private String BTitle;                  // 제목

    @Column(length = 2000)
    private String BContent;                // 내용

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime BDate;            // 작성일, 수정X

    @Column(insertable = false)
    @CreationTimestamp
    private LocalDateTime BUpdateDate;      // 수정일, 새로 추가X

    @Column
    private int BHit;                       // 조회수

    @Column
    private String BFileName;               // 파일명

    @Column
    private int BLike;                      // 추천/좋아요

    @Column
    private int BDislike;                   // 비추천/싫어요

    // USERBOARD의 여러 데이터가 USERT의 한 USERID를 참조한다. 컬럼값은 USERT의 USERID와 같으며 컬럼명칭은 BWriter다.
    // ex) bnum1, bnum2, bnum3 => user1, bnum4, bnum5 => user2, etc...
    @ManyToOne
    @JoinColumn(name="BWriter")
    private UserEntity user;

    // 한 id가 여러 USERCOMMENT 데이터의 레퍼런스로 사용된다
    // ex) id = bnum1 => bnum1.usercomment1, bnum1.usercomment2, bnum1.usercomment3, etc...
    @OneToMany(mappedBy = "board")
    private List<CommentEntity> comments;

    public static BoardEntity toEntity(BoardDTO dto) {
        BoardEntity entity = new BoardEntity();
        UserEntity user = new UserEntity();

        entity.setBNum(dto.getBNum());
        entity.setBType(dto.getBType());
        entity.setBTitle(dto.getBTitle());
        entity.setBContent(dto.getBContent());
        entity.setBDate(dto.getBDate());
        entity.setBUpdateDate(dto.getBUpdateDate());
        entity.setBHit(dto.getBHit());
        entity.setBFileName(dto.getBFileName());
        entity.setBLike(dto.getBLike());
        entity.setBDislike(dto.getBDislike());

        user.setUserId(dto.getBWriter());

        entity.setUser(user);

        return entity;
    }
}
