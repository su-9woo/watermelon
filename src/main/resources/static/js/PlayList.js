$(()=>{
    $.ajax({
        type : "GET",
        url : "/getLoginId",
        dataType: "json",
        success: function (result) {
            if(result.error){
                alert(result.error);
            } else {
                const  loginId = result.loginId;
                console.log("로그인 ID:", loginId);

                $.ajax({
                    type: "POST",
                    url: "/playListBox",
                    data: {
                        loginId: loginId // 서버에서 받은 loginId를 사용
                    },
                    dataType: "json",
                    success: function (result) {
                        console.log(result); // 플레이리스트 처리
                        PlayList(result); // 받은 플레이리스트로 함수 호출
                    },
                    error: function (e) {
                        console.log("플레이리스트 가져오기 실패", e);
                        alert('플레이리스트 가져오기 실패');
                    }
                });
            }
        },
        error: function (e) {
            console.log("로그인 ID 가져오기 실패", e);
        }
    });
});

function PlayList(result) {
    let output = "";
    for (let i in result) {
        output += `<a id="playlistInfoMove" onclick="goToPlaylist(${result[i].pinfoNum})"><div class="playList-container">
                        <div class="s-playlist">
                            <img src="/upload/${result[i].pinfoCoverName}">
                            <div class="s-playlist-info">
                                <div class="s-playlist-title">${result[i].pinfoTitle}</div>
                                <div style="display: flex;">
                                    <div class="s-playlist-subtitle">${result[i].pinfoDate.split('T')[0]}</div>    
                                    <div class="s-playlist-subtitle" style="margin-left: 10px;">❤ ${result[i].pinfoHit}</div>
                                </div>
                           </div>
                    </div></a>`;
    }
    $("#playList").html(output);
}

// 페이지 이동 함수 정의
function goToPlaylist(pinfoNum) {
    window.location.href = `/goToPlaylist/${pinfoNum}`;
}
