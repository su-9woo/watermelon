$(document).ready(function () {

    var userId = $('#userId').val(); // 히든 필드에서 userId 가져오기


    $('#join').click(function () {
        location.href = "/uJoinForm";
    });

    $('#login').click(function () {
        location.href = "/uLoginForm";
    });

    $('#logout').click(function () {
        location.href = "/uLogout";
    });

    $('#mlist').click(function () {
        location.href = "/uList";
    });

    $('#mview').click(function () {
        location.href = "/uView/" + userId;
    });

    $('#blist').click(function () {
        location.href = "/bList";
    });

    $('#write').click(function () {
        location.href = "/bWriteForm";
    });
    $('#index').click(function () {
        location.href = "/index";
    });
    $('#close').click(function () {
        $('#myModal').hide(); // 모달 숨기기
    });
});
let list = [];
let page = 1;       // 페이지 번호
let limit = 5;      // 한페이지에 출력될 데이터 갯수
const block = 5;    // 한페이지에 출력될 페이지 갯수
let count = 0;      // 전체 데이터 갯수

// 페이지 로딩 후 ajax로 게시글 불러오기
$(() => {
    if (window.location.href === 'http://localhost:9091/bList') {
        $.ajax({
            type: "post",
            url: "/boardList",
            dataType: "json",
            success: (result) => {
                list = result;
                console.log(list);
                // result : 게시글 목록
                pagingList(page, list);
            },
            error: () => {
                alert('boardList 통신 실패!');
            }
        });
    }
});

function pagingList(page, list){
    count = list.length;        // 전체 게시글 갯수

    if(count > 0){
        let maxPage = Math.ceil(count/limit);
        if(page > maxPage){
            page = maxPage;
        }

        let startRow = (page - 1) * limit;      // 0 5 10 15 ...
        let endRow = page * limit - 1;          // 4 9 14 19 ...
        if(endRow >= count){
            endRow = count - 1;
        }

        let startPage = (Math.ceil(page/block)-1) * block + 1;  // 1 1 1 1 1  6  6  6  6  6 ..
        let endPage = startPage + block - 1;                    // 5 5 5 5 5 10 10 10 10 10 ..
        if(endPage > maxPage){
            endPage = maxPage;
        }

        // <th>번호</th>
        // <th>제목</th>
        // <th>작성자</th>
        // <th>작성일</th>
        // <th>조회수</th>

        let output = "";
        for(let i=startRow; i<=endRow; i++){
            output += `
                <tr>
                    <td>${list[i].bnum}</td>
                    <td><a href="/bView/${list[i].bnum}">${list[i].btitle}</a></td>
                    <td>${list[i].bwriter}</td>
                    <td>조회수: ${list[i].bhit}</td> 
                    <td>♡: ${list[i].blike}</td>
                </tr>
            `;
        }

        $('tbody').empty();
        $('#myModal').show(); // 테이블 보이기
        $('tbody').append(output);

        // numbering 페이징 처리
        let pageNum = "";

        // 현재 페이지에서 -1을 했을때 1보다 작으면 "1" 값을,
        // 1보다 크거나 같으면 "page-1" 값을 prev 변수에 담는다
        let prev = (page - 1 < 1 ? 1 : page - 1);
        /*
            if(page-1 < 1){
                prev = 1;
            } else {
                prev = page - 1;
            }
         */

        // 현재 페이지에서 +1을 했을때 maxPage보다 크거나 같으면 "maxPage" 값을,
        // maxPage 값보다 작으면 "page+1" 값을 next 변수에 담는다.
        let next = (page + 1 >= maxPage ? maxPage : page + 1);

        // [이전]
        if(page <= 1){
            pageNum += `<span class="prev"> 이전 </span>`;
        } else {
            pageNum += `<a href="#" class="prev" data-page="${prev}"> 이전 </a>`;
        }

        // [번호]
        for(let i=startPage; i<=endPage; i++){
            if(page == i){
                pageNum += `<span class="iNum"> ${i} </span>`;
            } else {
                pageNum += `<a href="#" class="iNum" data-page="${i}">  </a>`;
            }
        }

        // [다음]
        if(page >= maxPage){
            pageNum += `<span class="next"> 다음 </span>`;
        } else {
            pageNum += `<a href="#" class="next" data-page="${next}"> 다음 </a>`;
        }

        $('#numbering').empty();

        $('#numbering').append(pageNum);


        $(document).on('click', '#numbering a', function(e){
            page = parseInt($(this).data('page'));
            pagingList(page, list);
        });
    } else {
        $('tbody').empty();
    }

}

// 게시글 갯수 변경
$('#limit').change(()=>{
    page = 1;
    limit = parseInt($('#limit').val());
    pagingList(page, list);
});

// 게시글 검색 버튼 클릭 이벤트
$('#searchBtn').click(()=>{
    let category = $('#category').val();
    let keyword = $('#keyword').val();

    // AJAX 요청을 통해 검색 결과 가져오기
    $.ajax({
        type: "POST",
        url: "/searchList",
        data: {
            "category": category,
            "keyword": keyword
        },
        dataType: "json",
        success: (result) => {
            list = result;
            console.log(list);
            pagingList(page, list);
        },
        error: () => {
            alert('searchList 통신 실패!');
        }
    });
});

// 자유 게시글 검색 버튼 클릭 이벤트
$('#freeBtn').click(()=>{
    let category = "BType";
    let keyword = "자유";

    // AJAX 요청을 통해 검색 결과 가져오기
    $.ajax({
        type: "POST",
        url: "/searchList",
        data: {
            "category": category,
            "keyword": keyword
        },
        dataType: "json",
        success: (result) => {
            list = result;
            console.log(list);
            pagingList(page, list);
        },
        error: () => {
            alert('searchList 통신 실패!');
        }
    });
});


// 아티스트 게시글 검색 버튼 클릭 이벤트
$('#artistRecommendBtn').click(()=>{
    let category = "BType";
    let keyword = "아티스트 추천";

    // AJAX 요청을 통해 검색 결과 가져오기
    $.ajax({
        type: "POST",
        url: "/searchList",
        data: {
            "category": category,
            "keyword": keyword
        },
        dataType: "json",
        success: (result) => {
            list = result;
            console.log(list);
            pagingList(page, list);
        },
        error: () => {
            alert('searchList 통신 실패!');
        }
    });
});


// 플레이리스트 게시글 검색 버튼 클릭 이벤트
$('#playlistRecommendBtn').click(()=>{
    let category = "BType";
    let keyword = "플레이리스트/노래 추천";

    // AJAX 요청을 통해 검색 결과 가져오기
    $.ajax({
        type: "POST",
        url: "/searchList",
        data: {
            "category": category,
            "keyword": keyword
        },
        dataType: "json",
        success: (result) => {
            list = result;
            console.log(list);
            pagingList(page, list);
        },
        error: () => {
            alert('searchList 통신 실패!');
        }
    });
});



// 추천 수 많은 순으로 게시글 검색 버튼 클릭 이벤트
$('#trendingBtn').click(() => {
    // AJAX 요청을 통해 검색 결과 가져오기
    $.ajax({
        type: "post",
        url: "/buttonList",
        dataType: "json",
        success: (result) => {
            list = result;
            console.log(list);
            // result : 게시글 목록
            pagingList(page, list);
        },
        error: () => {
            alert('boardList 통신 실패!');
        }
    });
});

// 추천 수 많은 순으로 게시글 검색 버튼 클릭 이벤트
$('#BHitBtn').click(() => {
    // AJAX 요청을 통해 검색 결과 가져오기
    $.ajax({
        type: "post",
        url: "/bhitList",
        dataType: "json",
        success: (result) => {
            list = result;
            console.log(list);
            // result : 게시글 목록
            pagingList(page, list);
        },
        error: () => {
            alert('boardList 통신 실패!');
        }
    });
});


