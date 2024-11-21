//RestfulController
package com.icia.semi.controller;

import com.icia.semi.dao.BoardRepository;
import com.icia.semi.dto.*;
import com.icia.semi.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class RestfulController {

    private final MusicService msvc;

    private final PlayListService psvc;

    private final UserService usvc;

    private final ArtistService asvc;

    private final BoardService bsvc;

    private final BoardRepository brepo;


    private BoardRepository boardRepository;

    @PostMapping("/ChartList")
    public List<MusicDetailDTO> ChartList(){
        return msvc.ChartList();
    }

    @PostMapping("/PlaylistM")
    public List<MusicDetailDTO> PlaylistM(@RequestParam("pinfoNum") int pinfoNum){
        System.out.println("이놈이 1 : " + pinfoNum);
        return psvc.PlaylistM(pinfoNum);
    }

    @PostMapping("/playList")
    public ModelAndView playList(@ModelAttribute PlaylistInfoDTO playlistInfo, @RequestParam("mid[]") List<String> midList) {
        System.out.println("Playlist Info: " + playlistInfo);
        System.out.println("MID List: " + midList);
        return psvc.playList(playlistInfo, midList);
    }

    @PostMapping("/searchM")
    public List<MusicDetailDTO> searchM(@RequestParam("searchMusic") String searchMusic){
        System.out.println("검색 이름 : " + searchMusic);
        return msvc.searchMusic(searchMusic);
    }



    @PostMapping("/idCheck")
    public String idCheck(@RequestParam("UserId") String UserId) {

        String result = usvc.idCheck(UserId);
        return result;
    }

    // emailCheck : 이메일 인증번호 받아오기
    @PostMapping("/emailCheck")
    public String emailCheck(@RequestParam("UserEmail") String UserEmail) {

        String uuid = usvc.emailCheck(UserEmail);

        return uuid;
    }

    // my Page 플레이리스트 불러오기
    @PostMapping("/myPlaylistInfo/{UserId}")
    public List<PlaylistInfoDTO> myPlaylistInfo(@PathVariable String UserId) {
        System.out.println("json 확인");
        System.out.println(UserId);
        return psvc.myPlaylistInfo(UserId);
    }

    // 비밀번호 변경 이전에 현재 비밀번호 확인
    @PostMapping("/checkCurrentPw")
    public String checkCurrentPw(@RequestParam("UserPw") String UserPw) {
        System.out.println("\n 현재비밀번호 확인 메소드 || UserPw : " + UserPw );
        return usvc.checkCurrentPw(UserPw);
    }

    // 새로 입력한 비밀번호가 이전 비밀번호와 동일한지 확인
    @PostMapping("/checkNewPw")
    public String checkNewPw (@RequestParam("UserPw") String UserPw){
        System.out.println("\n 이미 사용된 비밀번호인지 확인하는 메소드 || UserPw : " + UserPw );
        return usvc.checkNewPw(UserPw);
    }

    // 내 게시글 불러오기
    @PostMapping("/myBoardInfo/{UserId}")
    public List<BoardDTO> myBoardList(@PathVariable String UserId) {
        System.out.println("\n내 게시글 불러오기 메소드 || UserId : " + UserId);;
        return bsvc.myBoardList(UserId);
    }

    @PostMapping("playListBox")
    public List<PlaylistInfoDTO> playListBox(@RequestParam("loginId") String loginId){
        System.out.println("로그인 아이디 : " + loginId);
        return psvc.playList_bogi(loginId);
    }



    // getLoginId
    @GetMapping("/getLoginId")
    public ResponseEntity<Map<String, Object>> getLoginId(HttpSession session) {
        String loginId = (String) session.getAttribute("loginId"); // HttpSession에서 loginId 가져오기

        if (loginId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "로그인되지 않았습니다."));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("loginId", loginId); // loginId 반환

        return ResponseEntity.ok(response);
    }


    // playListP
    @PostMapping("playListP")
    public List<PlaylistInfoDTO> playListP(){
        System.out.println("1 : ");
        return psvc.playListP();
    }

    @PostMapping("/artistList")
    public List<ArtistDetailDTO> artistList(){
        return asvc.artistList();
    }

    // searchList : 검색 목록
    @PostMapping("/searchList")
    public List<BoardDTO> searchList(@ModelAttribute SearchDTO search) {
        System.out.println("search : " + search);   // category, keyword
        return bsvc.searchList(search);
    }

    // 추천 수 증가
    @PostMapping("/increaseLike/{bNum}")
    public ResponseEntity<Integer> increaseLike(@PathVariable int bNum) {

        System.out.println(bNum+" : bnum"+brepo);

        brepo.incrementBLike(bNum); // 추천 수 증가
        BoardEntity boardEntity = brepo.findById(bNum).orElseThrow(); // 게시글 조회
        System.out.println(bNum+" : bnum"+brepo);
        return ResponseEntity.ok(boardEntity.getBLike()); // 현재 추천 수 반환
    }

    // 비추천 수 증가
    @PostMapping("/increaseDislike/{bNum}")
    public ResponseEntity<Integer> increaseDislike(@PathVariable int bNum) {
        brepo.incrementBDislike(bNum); // 비추천 수 증가
        BoardEntity boardEntity = brepo.findById(bNum).orElseThrow(); // 게시글 조회
        return ResponseEntity.ok(boardEntity.getBDislike()); // 현재 비추천 수 반환
    }

    // boardList : 게시글 목록
    @PostMapping("/boardList")
    public List<BoardDTO> boardList() {
        return bsvc.boardList();
    }


    // buttonList : 좋아요순 게시글  목록
    @PostMapping("/buttonList")
    public List<BoardDTO> buttonList() {
        return bsvc.buttonList();
    }

    // bhitList 조회수순 게시글 목록
    @PostMapping("/bhitList")
    public List<BoardDTO> bhitList() {
        return bsvc.bhitList();
    }

    @PostMapping("/increaseMHit")
    @ResponseBody
    public ResponseEntity<String> increaseMHit(@RequestParam("mtitle") String mtitle, HttpSession session) {
        try {
            String userId = (String) session.getAttribute("loginId"); // 세션에서 사용자 아이디 가져오기
            System.out.println("/increaseMHit 컨트롤러 아이디채크+타이틀채크"+userId+mtitle);

            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
            }

            @SuppressWarnings("unchecked")
            Map<String, Set<String>> userLikes = (Map<String, Set<String>>) session.getAttribute("userLikes");
            if (userLikes == null) {
                userLikes = new HashMap<>();
                session.setAttribute("userLikes", userLikes);
            }

            Set<String> likedMusic = userLikes.getOrDefault(userId, new HashSet<>());
            if (likedMusic.contains(mtitle)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already liked");
            }

            boolean success = msvc.increaseHit(mtitle);
            if (success) {
                likedMusic.add(mtitle);
                userLikes.put(userId, likedMusic);
                return ResponseEntity.ok("Like added");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to increase hit");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

}
