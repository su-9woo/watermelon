$(document).ready(() => {
    // 페이지 로딩 시 앨범 목록을 요청
    $.ajax({
        type: "POST",
        url: "/playListP",
        dataType: "json", // 서버에서 JSON 형식의 데이터를 수신하도록 설정
        success: function (result) {
            // $("#playList").text(result);
            console.log(result)
            PlayListP(result);
        },
        error: function (e) {
            console.log("플레이리스트 가져오기 실패", e);
            alert('플레이리스트 가져오기 실패');
        }
    });

    // 마우스 휠로 수평 스크롤 구현
    $('#pickPL').on('wheel', function (event) {
        event.preventDefault(); // 기본 스크롤 동작을 방지
        if (event.originalEvent.deltaY !== 0) {
            // 수평 스크롤
            this.scrollLeft += event.originalEvent.deltaY;
        }
    });
});


// 기존의 PlayListP 함수 및 페이지 이동 함수는 그대로 유지
function PlayListP(result) {
    let output = "";
    for (let i in result) {
        output += `<a id="p-playlistInfoMove" onclick="goToPlaylist(${result[i].pinfoNum})">
                        <div class="p-playlist-container">
                            <div class="p-playlist-card">
                                <img class="p-playlist-cover" src="/upload/${result[i].pinfoCoverName}" alt="Playlist Cover">
                                <div class="p-playlist-info">
                                    <div class="p-playlist-title">${result[i].pinfoTitle}</div>
                                    <div class="p-playlist-details">
                                        <span class="p-playlist-date">${result[i].pinfoDate.split('T')[0]}</span>
                                        <span class="p-playlist-hits">❤ ${result[i].pinfoHit}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                   </a>`;
    }
    $("#pickPL").html(output);
}

// 페이지 이동 함수 정의
function goToPlaylist(pinfoNum) {
    window.location.href = `/goToPlaylist/${pinfoNum}`;
}
