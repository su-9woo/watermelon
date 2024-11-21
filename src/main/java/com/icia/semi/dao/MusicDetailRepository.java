package com.icia.semi.dao;

import com.icia.semi.dto.MusicDetailEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MusicDetailRepository extends JpaRepository<MusicDetailEntity, String> {
    @Query("SELECT a, m FROM ArtistDetailEntity a JOIN MusicDetailEntity m ON a.ArtistId = m.artist.ArtistId ORDER BY m.MLike DESC")
    List<Object[]> findAlbum();

    @Query("SELECT a, m FROM ArtistDetailEntity a JOIN MusicDetailEntity m ON a.ArtistId = m.artist.ArtistId WHERE m.MId IN :mIds ORDER BY m.MHit DESC")
    List<Object[]> findAllByMId(@Param("mIds") List<String> mIds);

    @Query("SELECT a, m FROM ArtistDetailEntity a JOIN MusicDetailEntity m ON a.ArtistId = m.artist.ArtistId WHERE m.MGenre LIKE %:category% ORDER BY m.MHit DESC")
    List<Object[]> findMusicTop1(@Param("category") String category, Pageable pageable);

    @Query("SELECT a, m FROM ArtistDetailEntity a JOIN MusicDetailEntity m ON a.ArtistId = m.artist.ArtistId WHERE a.ArtistId = :artistId ORDER BY m.MHit DESC")
    List<Object[]> findMusicTopByArtistId(@Param("artistId") int artistId);

    Optional<Object> findByMTitle(String mtitle);

    @Modifying
    @Query("UPDATE MusicDetailEntity m SET m.MHit = m.MHit + 1 WHERE m.MId = :mId")
    void updateLike(@Param("mId") int mId);

    @Query("SELECT m FROM MusicDetailEntity m WHERE m.MTitle LIKE %:keyword%")
    MusicDetailEntity findMusicInfo(String keyword);

    @Query("SELECT a, m FROM ArtistDetailEntity a JOIN MusicDetailEntity m ON a.ArtistId = m.artist.ArtistId WHERE m.MTitle LIKE %:searchMusic%")
    List<Object[]> findSearchMusic(@Param("searchMusic") String searchMusic);
}
