function musicList(result) {
    let output = "";
    for (let i in result) {
        output += `<div class="chartBox">
                        <div class="rank">
                            <span>${parseInt(i) + 1}</span>
                        </div>
                        <div class="album-cover">
                            <img src="${result[i].malbumCoverName}" width="60" height="60">
                        </div>
                        <div class="song-info">
                            <span class="musicInfo" data-mid-id="${result[i].mid}">
                                <p class="title">${result[i].mtitle}</p>
                            </span>
                            <span class="artistInfo" data-artist-id="${result[i].artistId}">
                                <p class="artist">${result[i].artistName}</p>
                            </span>
                        </div>
                        <div class="album">
                            <p>${result[i].malbum}</p>
                        </div>
                        <div class="likes">
                            <p class="like-count" data-mtitle="${result[i].mtitle}">
                                ❤ ${result[i].mhit}
                            </p>
                        </div>
                        <div class="actions">
                            <button class="playBtn" 
                                data-song-title="${result[i].mtitle}" 
                                data-artist-name="${result[i].artistName}" 
                                data-album-cover="${result[i].malbumCoverName}">
                                ▶
                            </button>
                            <button id="plusPy">+</button>
                            <button>
                               <img src="/img/updown_G.png" width="20px" alt="위아래 드래그">
                            </button>
                        </div>
                    </div>`;
    }
    // 업데이트된 HTML을 반영
    $("#test").html(output);
}

// 좋아요 <p> 태그 클릭 이벤트 처리
$(document).on('click', '.like-count', function () {
    const mtitle = $(this).data('mtitle');
    const $likeCount = $(this); // 클릭한 요소 참조

    console.log(`좋아요 클릭: ${mtitle}`);
    $.ajax({
        type: 'POST',
        url: '/increaseMHit', // 서버 API 엔드포인트
        data: { mtitle: mtitle }, // 서버로 전달할 데이터
        success: function () {
            alert('좋아요가 반영되었습니다!');
            // UI 업데이트 (좋아요 수 증가)
            let currentCount = parseInt($likeCount.text().replace('❤', '').trim());
            $likeCount.text(`❤ ${currentCount + 1}`);
        },
        error: function (error) {
            console.error('좋아요 반영 실패:', error);
            alert('이미 좋아요를 누르셨습니다!');
        }
    });
});