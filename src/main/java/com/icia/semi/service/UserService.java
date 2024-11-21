package com.icia.semi.service;


import com.icia.semi.dao.*;
import com.icia.semi.dto.*;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserService {

    private final UserRepository urepo;

    private final MusicDetailRepository mrepo;

    private final PlaylistInfoRepository pirepo;

    private final CommentRepository crepo;

    private final BoardRepository brepo;

    private final PlaylistRepository prepo;

    private ModelAndView mav;

    // 메일 인증
    private final JavaMailSender mailSender;

    // 프로필 사진 저장 경로
    Path pathProfile = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");

    // 암호화
    private BCryptPasswordEncoder pwEnc = new BCryptPasswordEncoder();

    // 로그인 session
    private final HttpSession session;

    ////////////////////////////////////////////////////////////////////////////////////////

    // 아이디 존재여부 확인
    public String idCheck(String UserId) {
        String result = "";
        Optional<UserEntity> entity = urepo.findById(UserId);

        if (entity.isPresent()) {
            result = "NO";
        } else {
            result = "OK";
        }
        return result;
    }

    // 이메일 인증
    public String emailCheck(String UserEmail) {
        String uuid = null;

        // 인증번호
        uuid = UUID.randomUUID().toString().substring(0, 8);

        // 이메일 발송
        MimeMessage mail = mailSender.createMimeMessage();

        String message = "<h2>안녕하세요. 인천일보 아카데미 입니다.</h2>"
                + "<p>인증번호는 <b>" + uuid + " 입니다.</p>";

        try {
            mail.setSubject("인천일보 아카데미 인증번호");
            mail.setText(message, "UTF-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(UserEmail));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return uuid;
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 프로필 사진 경로 저장 메소드
    public void saveProfile(UserDTO user) {
        String savePath = "";
        MultipartFile UserProfile = user.getUserProfile();
        // (1) 파일 업로드
        if (!UserProfile.isEmpty()) {
            // uProfile이 비어있지 않다면
            String uuid = UUID.randomUUID().toString().substring(0, 8);     // 랜덤 uuid 생성, String 타입으로 변환하고 앞 8글자만 사용
            String fileName = UserProfile.getOriginalFilename();               // 업로드된 파일의 이름을 가져오기
            String UserProfileName = uuid + "_" + fileName;                    // 생성한 uuid+_+파일명으로 프로필이름 만들기

            user.setUserProfileName(UserProfileName);                        // member 에 파일명 저장
            savePath = pathProfile + "\\" + UserProfileName;                   // 파일 경로 지정

            try {
                UserProfile.transferTo(new File(savePath));                    // 지정된 파일 경로로 저장
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            user.setUserProfileName("default.jpg");
        }
    }

    // 기존 프로필 파일 삭제 메소드
    public void deleteProfile(String UserProfileName) {
        // 기존 프로필 사진이 존재하거나 프로필 이름이 "default.jpg"가 아니라면
        if (UserProfileName != null && !UserProfileName.equals("default.jpg")) {
            String delPath = pathProfile + "\\" + UserProfileName;
            File delFile = new File(delPath);
            if (delFile.exists()) {
                delFile.delete();
            }
        }
    }

//////////////////////////////////////////////////////////추가//////////////////////////////////////////////////////////

    // 현재비밀번호 확인
    public String checkCurrentPw(String UserPw) {
        System.out.println("[2] controller → service");
        return matchPw(UserPw);
    }

    // 비밀번호 일치 확인 (현재 비밀번호, 새로운 비밀번호)
    public String checkNewPw(String UserPw) {
        System.out.println("[2] controller → service");
        return matchPw(UserPw);
    }

    // 비밀번호 일치 확인 메소드
    public String matchPw(String UserPw) {
        System.out.println("비밀번호 일치 확인 메소드");
        String result = "";
        Optional<UserEntity> entity = urepo.findById(session.getAttribute("loginId").toString());
        if (entity.isPresent()) {
            if (pwEnc.matches(UserPw, entity.get().getUserPw())) {
                result = "OK";
            } else {
                result = "NOPE";
            }
        }
        return result;
    }


    ////////////////////////////////////////////////////////////////////////////////////////

    // 회원가입
    public ModelAndView uJoin(UserDTO user) {
        System.out.println("[2] controller → service || user : " + user);
        mav = new ModelAndView();

        // (1) 파일 저장
        saveProfile(user);

        // (2) 비밀번호 암호화
        user.setUserPw(pwEnc.encode(user.getUserPw()));
        System.out.println("암호화 이후 user : " + user);

        // (3) dto→ entity
        UserEntity entity = UserEntity.toEntity(user);

        // (4) db 저장
        try {
            urepo.save(entity);
            mav.setViewName("redirect:/uLoginForm");                 // 로그인 페이지로
        } catch (Exception e) {
            mav.setViewName("redirect:/uJoinForm");
            throw new RuntimeException(e);
        }
        return mav;
    }

    // 로그인
    public ModelAndView uLogin(UserDTO user) {
        System.out.println("[2] controller → service || user : " + user);
        mav = new ModelAndView();

        Optional<UserEntity> entity = urepo.findById(user.getUserId());
        if (entity.isPresent()) {
            if (pwEnc.matches(user.getUserPw(), entity.get().getUserPw())) {
                UserDTO login = UserDTO.toDTO(entity.get());
                session.setAttribute("loginId", login.getUserId());
                session.setAttribute("loginProfile", login.getUserProfileName());
            } else {
                System.out.println("비밀번호가 틀렸습니다");
            }
        } else {
            System.out.println("아이디가 존재하지 않습니다");
        }
        mav.setViewName("redirect:/index");
        return mav;
    }

    // 회원탈퇴
    @Transactional
    public ModelAndView uDelete(UserDTO user) {
        System.out.println("[2] controller → service || user : " + user);
        mav = new ModelAndView();

        // 업로드 파일 삭제를 위한 path 객체
        Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/upload");

        deleteProfile(user.getUserProfileName());

        // 댓글 삭제
        crepo.deleteByUser_userId(user.getUserId());

        // 게시글에서 업로드한 파일 삭제
        List<BoardEntity> boardEntityList = brepo.findAllByUserId(user.getUserId());
        for (BoardEntity entity : boardEntityList) {
            String boardFileName = entity.getBFileName();
            if (boardFileName != null && !boardFileName.equals("default.jpg")) {
                String delPath = path + "\\" + boardFileName;
                File delFile = new File(delPath);
                if (delFile.exists()) {
                    delFile.delete();
                }
            }
        }
        // 게시글 삭제
        brepo.deleteByUser_userId(user.getUserId());

        // 플레이리스트 내용 삭제
        List<PlaylistInfoEntity> pliEntityList = pirepo.findAllByUserId(user.getUserId());
        for (PlaylistInfoEntity entity : pliEntityList) {
            //플레이리스트 커버 사진 삭제
            String playlistCoverName = entity.getPInfoCoverName();
            if (playlistCoverName != null && !playlistCoverName.equals("default.jpg")) {
                String delPath = path + "\\" + playlistCoverName;
                File delFile = new File(delPath);
                if (delFile.exists()) {
                    delFile.delete();
                }
            }
            // 플리 내용 삭제
            prepo.deleteByplaylistInfo_PInfoNum(entity.getPInfoNum());
        }

        // 플레이리스트 삭제
        pirepo.deleteByUser_userId(user.getUserId());

        // 유저 삭제
        urepo.deleteById(user.getUserId());
        session.invalidate();

        mav.setViewName("redirect:/index");

        return mav;
    }

//////////////////////////////////////////////////////////추가//////////////////////////////////////////////////////////

    // 내 정보 보기
    public ModelAndView uView(String userId) {
        System.out.println("[2] controller → service || userId : " + userId);
        mav = new ModelAndView();
        Optional<UserEntity> entity = urepo.findById(userId);
        if (entity.isPresent()) {
            UserDTO user = UserDTO.toDTO(entity.get());

            mav.addObject("view", user);
            mav.setViewName("user/myPage");
        } else {
            mav.setViewName("redirect:/uList");
        }
        return mav;
    }


    // 내 정보 (이메일, 프로필사진, 연락처) 수정
    public ModelAndView uModify(UserDTO user) {
        System.out.println("[2] controller → service || user : " + user);
        mav = new ModelAndView();
        Optional<UserEntity> entity = urepo.findById(user.getUserId());
        if (entity.isPresent()) {
            UserDTO currentUser = UserDTO.toDTO(entity.get());

            if (user.getUserPhone() != null && !user.getUserPhone().equals("")) { // 입력받은 값이 있다면 저장
                currentUser.setUserPhone(user.getUserPhone());
            }
            if (user.getUserEmail() != null && !user.getUserEmail().equals("")) { // 입력받은 값이 있다면 저장
                currentUser.setUserEmail(user.getUserEmail());
            }
            if (user.getUserProfile() == null || user.getUserProfile().isEmpty()) { // 업로드된 파일이 없다면 기존 파일 저장
                currentUser.setUserProfileName(entity.get().getUserProfileName());
            } else {
                // (0) 기존 파일 삭제
                deleteProfile(entity.get().getUserProfileName());
                // (1) 파일 업로드
                saveProfile(user);
                currentUser.setUserProfileName(user.getUserProfileName());
            }
            try {
                urepo.save(UserEntity.toEntity(currentUser));// db에 저장
                mav.setViewName("redirect:/uView/" + user.getUserId());          // view 페이지로 가기 (controller 거쳐서)
            } catch (Exception e) {
                mav.setViewName("redirect:/index");
                throw new RuntimeException(e);
            }
        }
        return mav;
    }

    // 비밀번호 수정
    public ModelAndView modifyLogin(String UserPw) {
        System.out.println("[2] controller → service || user : " + UserPw);
        mav = new ModelAndView();

        UserDTO user = UserDTO.toDTO(urepo.findById(session.getAttribute("loginId").toString()).get());
        user.setUserPw(pwEnc.encode(UserPw));

        urepo.save(UserEntity.toEntity(user));

        session.invalidate();
        mav.setViewName("redirect:/uLoginForm");

        return mav;
    }

}
