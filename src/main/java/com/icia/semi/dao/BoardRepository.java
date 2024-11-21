
package com.icia.semi.dao;

import com.icia.semi.dto.BoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    List<BoardEntity> findAllByOrderByBLikeDesc();

    List<BoardEntity> findByBTitleContainingOrderByBNumDesc(String keyword);

    List<BoardEntity> findByBContentContainingOrderByBNumDesc(String keyword);

    List<BoardEntity> findAllByOrderByBNumDesc();

    List<BoardEntity> findAllByOrderByBHitDesc();
    @Modifying
    @Transactional
    @Query("UPDATE BoardEntity b SET b.BHit = b.BHit + 1 WHERE b.BNum = :BNum")
    void incrementBHit(@Param("BNum") int BNum);

    @Modifying
    @Transactional
    @Query("UPDATE BoardEntity b SET b.BLike = b.BLike + 1 WHERE b.BNum = :BNum")
    void incrementBLike(@Param("BNum") int BNum);

    @Modifying
    @Transactional
    @Query("UPDATE BoardEntity b SET b.BDislike = b.BDislike + 1 WHERE b.BNum = :BNum")
    void incrementBDislike(@Param("BNum") int BNum);

    List<BoardEntity> findByUserUserIdContainingOrderByBNumDesc(String keyword);


    List<BoardEntity> findByBTypeContainingOrderByBNumDesc(String keyword);

    @Query("SELECT b FROM BoardEntity b WHERE b.user.userId = :userId")
    List<BoardEntity> findAllByUserId(String userId);

    void deleteByUser_userId(String userId);
}
