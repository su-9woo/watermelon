// PlaylistRepository
package com.icia.semi.dao;

import com.icia.semi.dto.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.expression.spel.ast.Selection;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Integer> {

    @Query("SELECT p FROM PlaylistEntity p WHERE p.playlistInfo.PInfoNum = :pNum")
    List<PlaylistEntity> findAllByMId(@Param("pNum") int pNum);

    List<PlaylistEntity> findAllByplaylistInfo_PInfoNum(int pNum);

    void deleteByplaylistInfo_PInfoNum(int pInfoNum);
}