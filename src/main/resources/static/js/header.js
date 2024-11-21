$(document).ready(function () {
    var userId = $('#userId').val();

    if (userId) {
        $('#join').hide();
        $('#login').hide();
        $('#view').show();
        $('#logout').show();
        $('#board').show();
    } else {
        $('#view').hide();
        $('#logout').hide();
        $('#board').hide();
    }

    $('#join').click(function () {
        location.href = "/uJoinForm";
    });

    $('#login').click(function () {
        location.href = "/uLoginForm";
    });

    $('#board').click(function () {
        location.href = "/bList";
    });

    $('#view').click(function () {
        const userId = $('#userId').val();
        if (userId) {
            location.href = "/uView/" + userId;
        } else {
            alert("아이디를 입력하세요");
        }
    });

    $('#logout').click(function () {
        location.href = "/uLogout";
    });
});

function performSearch() {
    const query = $('#searchInput').val();
    if (!query.trim()) {
        alert("검색어를 입력해주세요!");
        return;
    }

    // 아티스트 이름을 먼저 검색
    $.ajax({
        url: '/search',  // 엔드포인트
        method: 'GET',  // GET 방식으로 변경
        data: {
            keyword: query  // URL 쿼리 파라미터로 검색어 전달
        },
        beforeSend: function () {
            // 이곳에서 'query' 값이 제대로 설정되었는지 확인
            console.log('전송되는 검색어 (query):', query);  // 'query' 값 확인
        },
        success: function (data) {
            console.log(data)
            displayArtistResults(data);  // 아티스트 검색 결과 처리

        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            return null;

            // alert('검색 중 오류가 발생했습니다.');
        }
    });
}


function performSearchM() {
    const query = $('#searchInput').val();
    if (!query.trim()) {
        alert("검색어를 입력해주세요!");
        return;
    }
// 음악 제목으로 검색
    $.ajax({
        url: '/searchMusic',  // 새 엔드포인트 설정
        method: 'GET',
        data: {
            keyword: query  // URL 쿼리 파라미터로 검색어 전달
        },
        beforeSend: function () {
            console.log('전송되는 검색어 (query):', query);
        },
        success: function (data) {
            console.log(data);
            displayMusicResults(data);  // 검색 결과를 표시하는 함수 호출
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            return null;
            // alert('검색 중 오류가 발생했습니다.');
        }
    });
}

function displayArtistResults(data) {
    let output1 = "";
    if (data) {
        output1 += `<div class="artistBox">
                    <div class="artist-info">
                        <div class="artist-cover">
                            <img src="${data.artistProfileName}" width="100" height="100">
                        </div>
                        <p class="aName">${data.artistName}</p>
                        <p class="aType">${data.artistType}</p>
                        <p class="aDate">${data.artistDate}</p>
                        <p class="aAgency">${data.artistAgency}</p>
                    </div>
                </div>`;

        // 모달 내용에 결과 추가
        $("#searchResult").html(output1);

        // 모달 보이기
        $("#modal").css("display", "block");
    } else {
        console.error(`data is null or undefined`);
    }
}

function displayMusicResults(data) {
    let output2 = "";
    if (data) {
        output2 += `<div class="musicBox">
                    <div class="music-info">
                        <div class="album-cover">
                            <img src="${data.malbumCoverName}" width="100" height="100">
                        </div>
                        <p class="mTitle">${data.mtitle}</p>
                        <p class="mGenre">${data.mgenre}</p>
                        <p class="mAlbum">${data.malbum}</p>
                        <p class="mReleaseDate">${data.mreleaseDate}</p>
                        <p class="mHit">조회수: ${data.mhit}</p>
                        <p class="mLike">좋아요: ${data.mlike}</p>
                    </div>
                </div>`;

        $("#searchResult").html(output2);
        $("#modal").css("display", "block");
    } else {
        console.error(`data is null or undefined`);
    }
}

function performSearchResult() {
    const query = $('#searchInput').val().trim();
    if (!query) {
        alert("검색어를 입력해주세요!");
        return;
    }

        // 아티스트와 음악을 동시에 검색하고, 결과를 처리하는 Promise.all 사용
        Promise.all([performSearch(query), performSearchM(query)])
            .then(results => {
                let artistResults = results[0]; // 아티스트 검색 결과
                let musicResults = results[1]; // 음악 제목 검색 결과

                // 결과가 있으면 모달을 열고, 결과를 표시
                let output = "";

                // 아티스트 결과가 있으면
                if (artistResults && artistResults.length > 0) {
                    // 아티스트 결과만 표시하고, 음악 결과는 숨김
                    output += displayArtistResults(artistResults[0]); // 아티스트 결과 표시 함수 호출
                    $("#searchResult").html(output);
                    $("#modal").css("display", "block");
                } else if (musicResults && musicResults.length > 0) {
                    // 음악 제목 결과만 표시하고, 아티스트 결과는 숨김
                    output += displayMusicResults(musicResults[0]); // 음악 결과 표시 함수 호출
                    $("#searchResult").html(output);
                    $("#modal").css("display", "block");
                } else {
                    // 결과가 없으면 알림
                    // alert("검색 결과가 없습니다.");
                }
            })
            .catch(error => {
                console.error("검색 중 오류 발생:", error);
                // alert("검색 중 오류가 발생했습니다.");
            });
    }

function performSearchResult() {
    const query = $('#searchInput').val().trim();
    if (!query) {
        alert("검색어를 입력해주세요!");
        return;
    }

    // 아티스트와 음악을 동시에 검색하고, 결과를 처리하는 Promise.all 사용
    Promise.all([performSearch(query), performSearchM(query)])
        .then(results => {
            let artistResults = results[0]; // 아티스트 검색 결과
            let musicResults = results[1]; // 음악 제목 검색 결과

            // 결과가 있으면 모달을 열고, 결과를 표시
            let output = "";

            // 아티스트 결과가 있으면
            if (artistResults && artistResults.length > 0) {
                // 아티스트 결과만 표시하고, 음악 결과는 숨김
                output += displayArtistResults(artistResults[0]); // 아티스트 결과 표시 함수 호출
                $("#searchResult").html(output);
                $("#modal").css("display", "block");
            } else if (musicResults && musicResults.length > 0) {
                // 음악 제목 결과만 표시하고, 아티스트 결과는 숨김
                output += displayMusicResults(musicResults[0]); // 음악 결과 표시 함수 호출
                $("#searchResult").html(output);
                $("#modal").css("display", "block");
            } else {
                // 결과가 없으면 알림
                // alert("검색 결과가 없습니다.");
            }
        })
        .catch(error => {
            console.error("검색 중 오류 발생:", error);
            // alert("검색 중 오류가 발생했습니다.");
        });
}

// 모달 닫기 함수
function closeModal() {
    $("#modal").css("display", "none");
}

// 모달 외부 클릭 시 닫히도록 설정 (옵션)
$(document).click(function (event) {
    if ($(event.target).is("#modal")) {
        closeModal();
    }
});




