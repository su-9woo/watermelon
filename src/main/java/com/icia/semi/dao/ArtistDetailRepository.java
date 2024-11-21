package com.icia.semi.dao;

import com.icia.semi.dto.ArtistDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArtistDetailRepository extends JpaRepository<ArtistDetailEntity, Integer> {

    @Query("SELECT a FROM ArtistDetailEntity a WHERE a.ArtistName LIKE %:keyword%")
    ArtistDetailEntity findArtistInfo(String keyword);

}

