package com.icia.semi.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USERCOMMENT")
@SequenceGenerator(name = "COMMENT_SEQ_GENERATOR", sequenceName = "COMMENT_SEQ", allocationSize = 1)
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_SEQ_GENERATOR")
    private int CNum;                   // 댓글 번호

    @Column
    private String CContent;            // 댓글 내용

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime CDate;        // 작성일

    @Column
    private int CLike;                  // 좋아요수

    // 여러 데이터가 USERT 참조. 컬럼 값은 USERT 의 USERID 와 같으며 CWriter로 사용한다
    @ManyToOne
    @JoinColumn(name = "CWriter")
    private UserEntity user;

    // 여러 데이터가 USERBOARD 참조. 컬럼 값은 USERBOARD 의 BNUM 과 같으며 CBNum으로 사용한다
    @ManyToOne
    @JoinColumn(name="CBNum")
    private BoardEntity board;

    public static CommentEntity toEntity(CommentDTO dto) {
        CommentEntity entity = new CommentEntity();

        entity.setCNum(dto.getCNum());
        entity.setCContent(dto.getCContent());
        entity.setCLike(dto.getCLike());

        // 댓글 작성자 id를 사이트유저 id로 저장
        UserEntity user = new UserEntity();
        user.setUserId(dto.getCWriter());
        entity.setUser(user);

        // 게시글 번호를 현재 있는 게시글로 저장
        BoardEntity board = new BoardEntity();
        board.setBNum(dto.getCBNum());
        entity.setBoard(board);

        return entity;
    }

}
