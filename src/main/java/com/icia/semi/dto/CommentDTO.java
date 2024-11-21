package com.icia.semi.dto;

// package 아래로 복붙
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private int CNum;                   // 댓글 번호
    private String CWriter;             // 작성자
    private int CBNum;                  // 게시글 번호
    private String CContent;            // 댓글 내용
    private LocalDateTime CDate;        // 작성일
    private int CLike;                  // 좋아요수

    public static CommentDTO toDTO (CommentEntity entity){
        CommentDTO dto = new CommentDTO();

        dto.setCNum(entity.getCNum());
        dto.setCContent(entity.getCContent());
        dto.setCBNum(entity.getBoard().getBNum());
        dto.setCDate(entity.getCDate());
        dto.setCLike(entity.getCLike());

        dto.setCWriter(entity.getUser().getUserId());

        return dto;
    }

}
