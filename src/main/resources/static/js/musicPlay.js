// 페이지 로드 시 LocalStorage에 저장된 노래 목록을 불러와서 표시
document.addEventListener('DOMContentLoaded', function () {
    const listeningHistoryContainer = document.getElementById('listening-history');

    // LocalStorage에서 저장된 노래 목록을 불러옴
    const savedSongs = JSON.parse(localStorage.getItem('listenedSongs')) || [];

    // 저장된 노래 목록이 있으면 화면에 표시
    savedSongs.forEach(song => {
        const songElement = createSongElement(song);
        listeningHistoryContainer.appendChild(songElement);
    });
});

// 노래 정보가 클릭되면 그 노래를 'listenedSongs'에 추가하고 모달에 표시
$('#test').on('click', '.playBtn', function () {

    const playButton = $(this);

    // 현재 버튼이 ▶ 상태인 경우에만 음악 정보를 출력
    if (playButton.text() === "▶") {
        const songTitle = $(this).data('song-title');
        const artistName = $(this).data('artist-name');
        const albumCover = $(this).data('album-cover');

        // 새로운 노래 데이터 객체
        const song = {
            title: songTitle,
            artist: artistName,
            albumCover: albumCover
        };

        // 'listenedSongs' LocalStorage에 저장된 노래 목록을 가져오고 새로운 노래 추가
        let listenedSongs = JSON.parse(localStorage.getItem('listenedSongs')) || [];
        listenedSongs.push(song);

        // LocalStorage에 저장된 노래 목록 갱신
        localStorage.setItem('listenedSongs', JSON.stringify(listenedSongs));

        // 음악 정보를 모달에 출력
        $('#m-music-title').text(songTitle);
        $('#m-artist-name').text(artistName);
        $('#m-music-cover').attr('src', albumCover).show();

        // 추가된 노래를 'listening-history'에 동적으로 추가
        const songElement = createSongElement(song);
        $('#listening-history').append(songElement);
    }
});


// 노래 데이터를 HTML 요소로 변환
function createSongElement(song) {
    const songElement = document.createElement('div');
    songElement.classList.add('listened-song');
    songElement.innerHTML = `
                                <div style="display: flex; background-color: rgba(255, 111, 111, 0.3); border: 2px solid #f09995; border-radius: 8px; margin-top: 5px; margin-bottom: 5px; padding: 5px;">
                                    <img src="${song.albumCover}" alt="Album Cover" class="m-song-cover" width="50px" height="50px" style="border-radius: 8px;" />
                                        <div class="m-song-info">
                                            <p class="m-song-title" ">${song.title}</p>
                                            <p class="m-song-artist" >${song.artist}</p>
                                        </div>
                                </div>
                            `;
    return songElement;
}


// 전체 삭제 버튼 클릭 시
document.getElementById('clear-history-btn').addEventListener('click', function () {
    // LocalStorage에서 'listenedSongs' 항목을 삭제
    localStorage.removeItem('listenedSongs');

    // 화면에서 모든 노래 목록을 삭제
    document.getElementById('listening-history').innerHTML = '';
});
