$(document).ready(() => {
    // 페이지 로딩 시 앨범 목록을 요청
    $.ajax({
        type: "POST",
        url: "/ChartList",
        dataType: "json", // 서버에서 JSON 형식의 데이터를 수신하도록 설정
        success: function (result) {
            // $("#test").text(result);
            console.log(result)
            musicList(result);
        },
        error: function (e) {
            console.log("앨범 가져오기 실패", e);
            alert('앨범 가져오기 실패');
        }
    });
});


