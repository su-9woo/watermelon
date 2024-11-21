package com.icia.semi.dao;

import com.icia.semi.dto.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findAllByBoard_BNum(int cbNum);

    @Query("SELECT c FROM CommentEntity c WHERE c.user.userId = :userId")
    List<CommentEntity> findAllByUserId(String userId);

    void deleteByUser_userId(String userId);
}