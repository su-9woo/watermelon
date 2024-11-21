
// 아티스트 클릭시 실행 될
$('#test').on('click', '.artistInfo', function () {
    event.preventDefault();  // 기본 링크 동작 막기
    const artistId = $(this).data('artist-id');

    $.ajax({
        type: "POST",
        url: `/artistInfo/${artistId}`,
        dataType: "json",
        success: function (data) {
            if (data) {
                $('#modal-artist .artist-img').attr("src", data.artistProfileName);
                $('#modal-artist .artist-name').text(data.artistName);
                $('#modal-artist .artist-date').text(data.artistDate);
                $('#modal-artist .artist-agency').text(data.artistAgency);
                $('#modal-artist .artist-type').text(data.artistType);

                $("#modal-artist").show();  // 모달 표시
                $(".modal-overlay").show();
            } else {
                alert("아티스트 정보를 불러오지 못했습니다.");
            }
        },
        error: function (e) {
            console.log("아티스트 정보 조회 실패", e);
            alert("아티스트 정보를 불러오지 못했습니다.");
        }
    });
});

// 음악 타이틀 클릭 시 실행 될
$('#test').on('click', '.musicInfo', function () {
    event.preventDefault();  // 기본 링크 동작 막기
    const mId = $(this).data('mid-id');

    $.ajax({
        type: "POST",
        url: `/musicInfo/${mId}`,
        dataType: "json",
        success: function (data) {
            if (data) {
                // 모달에 JSON 데이터를 표시
                $('#modal-music .album-cover').attr("src", data.malbumCoverName);
                $('#modal-music .m-title').text(data.mtitle);
                $('#modal-music .artist-name').text(data.artistName);
                $('#modal-music .music-album').text(data.malbum);
                $('#modal-music .music-releaseDate').text(data.mreleaseDate);
                $('#modal-music .music-genre').text(data.mgenre);


                // 모달 표시
                $("#modal-music").show();
                $(".modal-overlay").show();
            } else {
                alert("음악 정보를 불러오지 못했습니다.");
            }
        },
        error: function (e) {
            console.log("음악 정보 조회 실패", e);
            alert("음악 정보를 불러오지 못했습니다.");
        }
    });
});


// 모달 닫기
$(".modal-close").on("click", function () {
    $(".modal").hide();  // 모달 숨기기
    $(".modal-overlay").hide();  // 오버레이 숨기기
});
