package com.icia.semi.service;

import com.icia.semi.dao.CommentRepository;
import com.icia.semi.dto.BoardDTO;
import com.icia.semi.dto.BoardEntity;
import com.icia.semi.dto.CommentDTO;
import com.icia.semi.dto.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository crepo;

    public List<CommentDTO> cList(int CBNum) {
        List<CommentDTO> dtoList = new ArrayList<>();
        List<CommentEntity> entityList = crepo.findAllByBoard_BNum(CBNum);

        for(CommentEntity entity : entityList ){
            dtoList.add(CommentDTO.toDTO(entity));
        }

        return dtoList;
    }

    public List<CommentDTO> cWrite(CommentDTO comment) {

        // 댓글 입력
        CommentEntity entity = CommentEntity.toEntity(comment);
        crepo.save(entity);

        // 댓글 입력 후 목록 불러오기
        List<CommentDTO> dtoList = cList(comment.getCBNum());
        return dtoList;

    }


    public List<CommentDTO> cDelete(CommentDTO comment) {

        // cnum을 dto.cnum을 통해가져오고 where cnum 을해서 cnum에 해당되는 행의 값만  삭제 하여 댓글 삭제
        crepo.deleteById(comment.getCNum());

        // 댓글 삭제 후 목록 불러오기
        List<CommentDTO> dtoList = cList(comment.getCBNum());
        return dtoList;

    }

    // 내 댓글 보기
    public List<CommentDTO> myCommentList(String UserId) {
        List<CommentDTO> dtoList = new ArrayList<>();

        List<CommentEntity> entityList = crepo.findAllByUserId(UserId);

        for(CommentEntity entity : entityList) {
            dtoList.add(CommentDTO.toDTO(entity));
        }
        return dtoList;
    }
}