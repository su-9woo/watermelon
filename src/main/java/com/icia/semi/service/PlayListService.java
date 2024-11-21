
// PlaylistService
package com.icia.semi.service;

import com.icia.semi.dao.MusicDetailRepository;
import com.icia.semi.dao.PlaylistInfoRepository;
import com.icia.semi.dao.PlaylistRepository;
import com.icia.semi.dto.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayListService {

    private final PlaylistInfoRepository plirepo;

    private final PlaylistRepository prepo;

    private final MusicDetailRepository mrepo;



    private ModelAndView mav;

    private final  HttpSession session;

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/upload");


    public ModelAndView playList(PlaylistInfoDTO playlistInfoDTO, List<String> midList) {

        mav = new ModelAndView();

        MultipartFile pFile = playlistInfoDTO.getPInfoCover();
        String savePath = "";


        if (!pFile.isEmpty()) {
            String uuid = UUID.randomUUID().toString().substring(0, 8);
            String fileName = pFile.getOriginalFilename();
            String pFileName = uuid + "_" + fileName;

            playlistInfoDTO.setPInfoCoverName(pFileName);

            savePath = path + "\\" + pFileName;
            System.out.println("4 : " + savePath);
            try {
                pFile.transferTo(new File(savePath));
            }catch (IOException e){
                System.out.println("업로드 실패 : "+ e.getMessage());
            }
        } else {
            playlistInfoDTO.setPInfoCoverName("default.jpg");
        }

        System.out.println("PlayList DTO : " + playlistInfoDTO);
        System.out.println("midList : " + midList);

        // 1. PlaylistInfoDTO를 PlaylistInfoEntity로 변환 후 저장
        PlaylistInfoEntity playlistInfoEntity = PlaylistInfoEntity.toEntity(playlistInfoDTO);
        System.out.println("PlayList Entity : " + playlistInfoEntity);
        playlistInfoEntity = plirepo.save(playlistInfoEntity);  // 플레이리스트 정보(생성자, 제목, 커버이미지 등 저장하기 위한)

        // 저장 후 생성된 PK 값 가져오기
        int pInfoNum = playlistInfoEntity.getPInfoNum();
        System.out.println("PlayList Number : " + pInfoNum);

        // 2. MID 리스트를 활용하여 각 곡에 대한 PlaylistEntity 생성 후 저장
//        midList.remove(midList.size()-1);
        for (String mid : midList) {
            PlaylistDTO playlistDTO = new PlaylistDTO();
            playlistDTO.setPListInfoNum(pInfoNum);
            playlistDTO.setMId(mid);

            PlaylistEntity playlistEntity = PlaylistEntity.toEntity(playlistDTO);
            System.out.println("PlayList_Music ID : " + playlistEntity);
            prepo.save(playlistEntity);         // 플레이리스트에 저장될 음악 정보 (MID)
        }

        mav.setViewName("redirect:/playlistForm");

        // 3. 결과 반환
        return mav; // 저장 후 리다이렉트 경로 설정
    }


    public List<PlaylistInfoDTO> playList_bogi(String loginId) {
        List<PlaylistInfoDTO> dtoList = new ArrayList<>();

        List<PlaylistInfoEntity> entityList = plirepo.findByLoginId(loginId);
        // System.out.println("2 : " + entityList);

        for(PlaylistInfoEntity entity : entityList){
            dtoList.add(PlaylistInfoDTO.toDTO(entity));
        }
        System.out.println("3 : " + dtoList);

        return dtoList;
    }

    public ModelAndView goToPlaylist(int pNum) {
        mav = new ModelAndView();
        System.out.println("2 : " + pNum);

        String loginId = (String)session.getAttribute("loginId");

        if(loginId == null){
            loginId = "Guest";
        }

        // 현재 쿠키를 담을 배열 생성
        Cookie[] cookies = request.getCookies();

        Cookie viewCookie = null;

        // 쿠키가 존재한다면
        if(cookies != null && cookies.length > 0) {

            //쿠키만큼 반복문을 실행 => cookie : cookies[i]
            for(Cookie cookie : cookies) {

                // 쿠키 이름이 ex) cookie_Guest_1과 같다면
                if(cookie.getName().equals("cookie_" + loginId + "_" + pNum)) {
                    System.out.println(cookie.getName() + "존재!");

                    // viewCookie에 "cookie_Guest_1"를 담는다
                    viewCookie = cookie;
                }
            }
        }

        if(viewCookie == null) {
            String cookie = "cookie_" + loginId + "_" + pNum;
            Cookie newCookie = new Cookie(cookie, cookie);

            newCookie.setMaxAge(60 * 60 * 1);
            response.addCookie(newCookie);

            System.out.println("새로운 쿠키 생성 : " + newCookie.getName());

            // 방법(1) - 쿼리문을 작성해서 조회수 1증가
            plirepo.incrementPHit(pNum);

            // 방법(2) - 겟글 번호로 데이터 조회 후 조회수 1증가 후 다시 저장
            // Optional<BoardEntity> entity = brepo.findById(bNum);
            // entity.get().setBHit(entity.get().getBHit() + 1);

        }

        Optional<PlaylistInfoEntity> entity = plirepo.findById(pNum);
//        List<PlaylistEntity> entityList = prepo.findAllByMId(pNum);



        if(entity.isPresent()){
            PlaylistInfoDTO dto = PlaylistInfoDTO.toDTO(entity.get());
            mav.addObject("playlist", dto);
            mav.setViewName("music/playlistInfo");
        } else {
            mav.setViewName("redirect:/index");
        }

        return mav;
    }

    public List<MusicDetailDTO> PlaylistM(int pinfoNum) {
        List<PlaylistEntity> entityList = prepo.findAllByMId(pinfoNum);


        List<String> mIds = new ArrayList<>();
        for (PlaylistEntity entity : entityList) {
            String mId = entity.getMusic().getMId();
            mIds.add(mId); // 중복 제거
        }

        System.out.println("추출된 MId 목록: " + mIds);

        List<MusicDetailDTO> dtoList = new ArrayList<>();
        List<Object[]> musicList = mrepo.findAllByMId(mIds);

        for (Object[] obj : musicList) {
            ArtistDetailEntity artistEntity = (ArtistDetailEntity) obj[0];
            MusicDetailEntity musicEntity = (MusicDetailEntity) obj[1];

            // MusicDetailDTO로 변환
            MusicDetailDTO dto = new MusicDetailDTO();
            dto.setMId(musicEntity.getMId());
            dto.setMTitle(musicEntity.getMTitle());
            dto.setMGenre(musicEntity.getMGenre());
            dto.setMAlbum(musicEntity.getMAlbum());
            dto.setMReleaseDate(musicEntity.getMReleaseDate());
            dto.setMHit(musicEntity.getMHit());
            dto.setMLike(musicEntity.getMLike());
            dto.setMAlbumCoverName(musicEntity.getMAlbumCoverName());
            dto.setArtistName(artistEntity.getArtistName()); // 아티스트 이름 추가

            dtoList.add(dto);
        }

        return dtoList;
    }

    // 내 플레이리스트 목록
    public List<PlaylistInfoDTO> myPlaylistInfo(String UserId) {
        System.out.println(UserId);
        List<PlaylistInfoDTO> dtoList = new ArrayList<>();

        List<PlaylistInfoEntity> entityList = plirepo.findAllByUserId(UserId);

        for(PlaylistInfoEntity entity : entityList){
            dtoList.add(PlaylistInfoDTO.toDTO(entity));
        }
        System.out.println(dtoList);
        return dtoList;
    }

    public ModelAndView deletePlaylist(int pNum) {
        System.out.println("[2] controller → service");
        mav = new ModelAndView();

        Optional<PlaylistInfoEntity> playlistInfoEntity = plirepo.findById(pNum);
        if (playlistInfoEntity.isPresent()) {
            String playlistCoverName = playlistInfoEntity.get().getPInfoCoverName();
            // 기존 프로필 사진이 존재하거나 프로필 이름이 "default.jpg"가 아니라면
            if (playlistCoverName != null && !playlistCoverName.equals("default.jpg")) {
                String delPath = path + "\\" + playlistCoverName;
                File delFile = new File(delPath);
                if (delFile.exists()) {
                    delFile.delete();
                }
            }
            List<PlaylistEntity> playlistEntityList = prepo.findAllByplaylistInfo_PInfoNum(pNum);
            prepo.deleteAll(playlistEntityList);
            plirepo.delete(playlistInfoEntity.get());
        }

        mav.setViewName("redirect:/index");
        return mav;
    }

    public List<PlaylistInfoDTO> playListP() {
        List<PlaylistInfoDTO> dtoList = new ArrayList<>();

        List<PlaylistInfoEntity> entityList = plirepo.findOrderByHitDesc();
        // System.out.println("2 : " + entityList);

        for(PlaylistInfoEntity entity : entityList){
            dtoList.add(PlaylistInfoDTO.toDTO(entity));
        }
        System.out.println("3 : " + dtoList);

        return dtoList;
    }
}