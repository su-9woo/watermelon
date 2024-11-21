const audio = document.getElementById("audio");
const playPauseButton = document.getElementById("play-pause");
const playPauseImage = document.getElementById("playPauseImage");
const seekBar = document.getElementById("seek-bar");
const volumeBar = document.getElementById("volume-bar");
const muteButton = document.getElementById("mute");
const muteIcon = document.getElementById("muteIcon");
const currentTimePlay = document.getElementById("current-time");
const durationPlay = document.getElementById("duration");
const shufflePlay = document.getElementById("shuffle");
const repeatPlay = document.getElementById("repeat");
const skipStart = document.getElementById("skip-to-start");
const skipEnd = document.getElementById("skip-to-end");


$(document).ready(function () {
    const audio = new Audio(); // 오디오 객체 생성
    let interval;
    let currentTime = 0;
    let isPlaying = false;

    // 클릭 이벤트 위임 (동적으로 생성된 요소에 대한 클릭 이벤트)
    $('#test').on('click', '.playBtn', function () {
        const playButton = $(this);

        // 현재 버튼이 ▶ 상태인 경우에만 음악 정보를 출력
        if (playButton.text() === "▶") {
            const songTitle = playButton.data('song-title');
            const artistName = playButton.data('artist-name');
            const albumCover = playButton.data('album-cover');
            const songUrl = playButton.data('song-url');  // 곡의 URL

            // 음악 정보를 출력 (footer에 표시)
            $('#song-title').text(songTitle);
            $('#artist-name').text(artistName);
            $('#c-album').attr('src', albumCover).show();
            $('#song-title').show();
            $('#artist-name').show();

            // 음악 정보를 localStorage에 저장
            const currentSong = {
                songTitle: songTitle,
                artistName: artistName,
                albumCover: albumCover
            };
            localStorage.setItem('currentSong', JSON.stringify(currentSong));

            // 모든 버튼을 ▶로 설정한 후, 클릭한 버튼만 II로 변경
            $(".playBtn").text("▶").css("font-size", "20px"); // 모든 버튼을 ▶로 설정
            playButton.text("II").css("font-size", "20px");   // 클릭한 버튼만 II로 변경

            // 오디오 파일 소스 변경
            audio.src = songUrl;
            audio.load(); // 새로 변경된 오디오 파일 로드

            // 새로운 음악이 삽입되면 컨트롤바 초기화
            resetSeekBar();

            // 오디오 파일 소스 변경
            audio.src = songUrl;
            audio.load(); // 새로 변경된 오디오 파일 로드

            // 음악 재생 시작
            if (!isPlaying) {
                isPlaying = true;
                playPauseImage.src = "https://img.icons8.com/?size=30&id=q0nxNdfpbYVl&format=png&color=899853"; // 일시정지 아이콘
                playPauseImage.alt = "Pause";

                // 1초마다 currentTime을 증가시키고 seekBar 업데이트
                interval = setInterval(() => {
                    if (currentTime >= 40) { // 40초에 도달하면 멈추기
                        clearInterval(interval); // interval 종료
                        isPlaying = false; // 상태를 일시정지로 변경
                        playPauseImage.src = "https://img.icons8.com/?size=30&id=fjx0LfGCNuZb&format=png&color=899853"; // 재생 아이콘
                        playPauseImage.alt = "Play";
                    } else {
                        currentTime += 1; // 1초씩 증가
                        seekBar.value = (currentTime / 40) * 100; // seekBar 값 업데이트 (40초 기준)
                        updateSeekBarFill(); // seekBar 색상 업데이트
                        currentTimePlay.textContent = formatTime(currentTime); // 시간 텍스트 업데이트
                    }
                }, 1000); // 1초마다 실행

                // 음악 재생
                audio.play();
            }

        } else {
            // 현재 버튼이 II 상태일 때는 ▶로 변경만 수행하고 음악 정보는 변경하지 않음
            playButton.text("▶").css("font-size", "20px");
            clearInterval(interval); // interval 종료
            isPlaying = false; // 상태를 일시정지로 변경
            playPauseImage.src = "https://img.icons8.com/?size=30&id=fjx0LfGCNuZb&format=png&color=899853"; // 재생 아이콘
            playPauseImage.alt = "Play";

            // 음악 일시정지
            audio.pause();
        }
    });


    // 시간 형식 변경 함수 (초를 분:초 형식으로 변환)
    function formatTime(time) {
        const minutes = Math.floor(time / 60);
        const seconds = Math.floor(time % 60);
        return `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
    }

    // 컨트롤 바 진행 색상 업데이트
    function updateSeekBarFill() {
        const seekValue = (currentTime / 40) * 100; // 40초 기준으로 비율 계산
        seekBar.style.background = `linear-gradient(to right, #899853 ${seekValue}%, #ccc ${seekValue}%)`;
    }


    // 페이지 로드 시 localStorage에서 음악 정보 불러오기
    $(document).ready(function () {
        const savedSong = JSON.parse(localStorage.getItem('currentSong'));

        // 만약 localStorage에 저장된 노래 정보가 있다면
        if (savedSong) {
            $('#song-title').text(savedSong.songTitle);
            $('#artist-name').text(savedSong.artistName);
            $('#c-album').attr('src', savedSong.albumCover).show();
            $('#song-title').show();
            $('#artist-name').show();
        }
    });


    // 재생/일시정지 버튼 클릭 이벤트
    playPauseButton.addEventListener("click", () => {
        if (!isPlaying) {
            // 일시정지 상태에서 재생을 시작
            isPlaying = true;
            playPauseImage.src = "https://img.icons8.com/?size=30&id=q0nxNdfpbYVl&format=png&color=899853";  // 일시정지 아이콘
            playPauseImage.alt = "Pause";

            // 1초마다 currentTime을 증가시키고 seekBar 업데이트
            interval = setInterval(() => {
                if (currentTime >= 40) { // 40초에 도달하면 멈추기
                    clearInterval(interval); // interval 종료
                    isPlaying = false; // 상태를 일시정지로 변경
                    playPauseImage.src = "https://img.icons8.com/?size=30&id=fjx0LfGCNuZb&format=png&color=899853"; // 재생 아이콘
                    playPauseImage.alt = "Play";
                } else {
                    currentTime += 1; // 1초씩 증가
                    seekBar.value = (currentTime / 40) * 100; // seekBar 값 업데이트 (40초 기준)
                    updateSeekBarFill(); // seekBar 색상 업데이트
                    currentTimePlay.textContent = formatTime(currentTime); // 시간 텍스트 업데이트
                }
            }, 1000); // 1초마다 실행
        } else {
            // 현재 재생 중일 때 일시정지
            clearInterval(interval); // interval 종료
            isPlaying = false; // 상태를 일시정지로 변경
            playPauseImage.src = "https://img.icons8.com/?size=30&id=fjx0LfGCNuZb&format=png&color=899853"; // 재생 아이콘
            playPauseImage.alt = "Play";
        }
    });

    function resetSeekBar() {
        currentTime = 0; // 시간 초기화
        seekBar.value = 0; // seekBar 초기화
        updateSeekBarFill(); // seekBar 색상 업데이트
    }

    // 오디오 파일 변경 시 컨트롤바 초기화
    audio.addEventListener("play", () => {
        resetSeekBar(); // 새로운 음악이 재생되기 전에 seekBar 초기화
    });


    // 시간 형식 변경 함수 (초를 분:초 형식으로 변환)
    function formatTime(time) {
        const minutes = Math.floor(time / 60);
        const seconds = Math.floor(time % 60);
        return `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
    }

    // 컨트롤 바 진행 색상 업데이트
    function updateSeekBarFill() {
        const seekValue = (currentTime / 40) * 100; // 40초 기준으로 비율 계산
        seekBar.style.background = `linear-gradient(to right, #899853 ${seekValue}%, #ccc ${seekValue}%)`;
    }


    // 볼륨바에서 조정할 때마다 실행
    volumeBar.addEventListener("input", () => {
        if (volumeBar.value == 0) {
            // 볼륨이 0일 때 음소거 상태로 변경
            audio.muted = true;
            muteIcon.src = "https://img.icons8.com/?size=30&id=643&format=png&color=899853";
            muteIcon.alt = "Muted";
        } else {
            // 볼륨이 0이 아닐 때 음소거 해제하고 볼륨을 설정
            audio.muted = false;
            audio.volume = volumeBar.value / 100;
            muteIcon.src = "https://img.icons8.com/?size=30&id=pYdXfFiW0VGm&format=png&color=899853";
            muteIcon.alt = "Unmuted";
        }
    });

    // 음소거 버튼 기능
    muteButton.addEventListener("click", () => {
        audio.muted = !audio.muted;
        if (audio.muted) {
            muteIcon.src = "https://img.icons8.com/?size=30&id=643&format=png&color=899853";
            muteIcon.alt = "Muted";
            volumeBar.value = 0;
        } else {
            muteIcon.src = "https://img.icons8.com/?size=30&id=pYdXfFiW0VGm&format=png&color=899853";
            muteIcon.alt = "Unmuted";
            volumeBar.value = audio.volume * 100;
        }
    });

    // 컨트롤 바에서 직접 시간 이동
    seekBar.addEventListener("input", () => {
        currentTime = (seekBar.value / 100) * 40; // 40초 기준으로 현재 시간 설정
        currentTimePlay.textContent = formatTime(currentTime); // 시간 텍스트 업데이트
        updateSeekBarFill(); // seekBar 색상 업데이트
    });

    // 직접 컨트롤 바 조작 시 시간 이동
    seekBar.addEventListener("input", () => {
        audio.currentTime = (seekBar.value / 100) * audio.duration;
        updateSeekBarFill();
    });

    // 볼륨 바 조작 시 볼륨 조정
    volumeBar.addEventListener("input", () => {
        audio.volume = volumeBar.value;
        updateVolumeBarFill();
    });

    // 볼륨 바 진행 색상 업데이트
    function updateVolumeBarFill() {
        const volumeValue = volumeBar.value * 100;
        volumeBar.style.background = `linear-gradient(to right, #899853 ${volumeValue}%, #ccc ${volumeValue}%)`;
    }

    // 순서 셔플과 반복 상태를 위한 변수
    let isShuffleOn = false;
    let isRepeatOn = false;


    // 셔플 버튼 기능
    shufflePlay.addEventListener("click", () => {
        isShuffleOn = !isShuffleOn;
        if (isShuffleOn) {
            shufflePlay.querySelector("img").src = "https://img.icons8.com/?size=30&id=geFZKRWsOCJT&format=png&color=899853";
        } else {
            shufflePlay.querySelector("img").src = "https://img.icons8.com/?size=30&id=geFZKRWsOCJT&format=png&color=899853";
        }
    });

    // 반복 버튼 기능
    repeatPlay.addEventListener("click", () => {
        isRepeatOn = !isRepeatOn;
        audio.loop = isRepeatOn;
        if (isRepeatOn) {
            repeatPlay.querySelector("img").src = "https://img.icons8.com/?size=30&id=Fm2D9kfJS33u&format=png&color=899853";
        } else {
            repeatPlay.querySelector("img").src = "https://img.icons8.com/?size=30&id=Fm2D9kfJS33u&format=png&color=899853";
        }
    });

    // 곡이 끝났을 때 다음 곡 재생 (셔플/반복)
    audio.addEventListener("ended", () => {
        if (!isRepeatOn) {
            if (isShuffleOn) {

                let nextTrackIndex;

                do {
                    nextTrackIndex = Math.floor(Math.random() * playlist.length);
                } while (nextTrackIndex === currentTrackIndex);
                currentTrackIndex = nextTrackIndex;
            } else {
                currentTrackIndex = (currentTrackIndex + 1) % playlist.length;
            }
            audio.src = playlist[currentTrackIndex];
            audio.play();
        }
    });

    // 클릭 변수
    let skipToStartClicks = 0;
    let skipToEndClicks = 0;


    // skip-to-start 버튼 클릭 기능
    skipStart.addEventListener('click', function () {
        skipToStartClicks++;
        if (skipToStartClicks === 1) {
            audio.currentTime = 0;
        } else if (skipToStartClicks === 2) {
            currentSongIndex = (currentSongIndex - 1 + songs.length) % songs.length;
            audio.src = songs[currentSongIndex];
            audio.play();
            skipToStartClicks = 0;
        }
    });

    // skip-to-end 버튼 클릭 기능
    skipEnd.addEventListener('click', function () {
        skipToEndClicks++;
        if (skipToEndClicks === 1) {
            audio.currentTime = audio.duration;
        } else if (skipToEndClicks === 2) {
            currentSongIndex = (currentSongIndex + 1) % songs.length;
            audio.src = songs[currentSongIndex];
            audio.play();
            skipToEndClicks = 0;
        }
    });
});

// modal1 (모달1) js

const modal1 = document.querySelector('.modal1');
const btnOpenModal = document.querySelector('#pList'); // 모달을 열고 닫을 버튼
const btnCloseModal = document.querySelector('#modal_close');

// 페이지 로드 시 LocalStorage에서 모달 열림 상태 확인
document.addEventListener('DOMContentLoaded', function () {
    const modalOpen = localStorage.getItem('modalOpen');

    // 모달이 열려있던 상태라면 모달을 열기
    if (modalOpen === 'true') {
        modal1.style.display = 'flex';
        modal1.style.transform = 'translateX(0%)'; // 모달을 표시
    }
});

// 모달 열기 / 닫기
btnOpenModal.addEventListener("click", () => {
    if (modal1.style.transform === "translateX(0%)") {
        // 모달이 열려 있으면 닫기
        modal1.style.transform = "translateX(100%)"; // 오른쪽으로 숨기기
        localStorage.removeItem('modalOpen'); // 모달 닫기, 상태 초기화
    } else {
        // 모달이 닫혀 있으면 열기
        modal1.style.display = "flex";
        modal1.style.transform = "translateX(0%)"; // 모달이 오른쪽에서 나타나도록 설정
        localStorage.setItem('modalOpen', 'true'); // 모달 열림 상태 저장
    }
});

// 모달 닫기 (닫기 버튼 클릭 시)
btnCloseModal.addEventListener("click", () => {
    modal1.style.transform = "translateX(100%)"; // 모달을 오른쪽으로 숨기기
    localStorage.removeItem('modalOpen'); // 모달 닫기, 상태 초기화
});

// 모달 외부 클릭 시 닫기 (모달 영역 클릭 시)
window.addEventListener("click", (event) => {
    if (event.target === modal1) {
        modal1.style.transform = "translateX(100%)";  // 모달을 오른쪽으로 숨기기
        localStorage.removeItem('modalOpen'); // 모달 닫기, 상태 초기화
    }
});