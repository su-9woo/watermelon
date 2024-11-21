
function myPlayLists(list) {
    console.log('myPlayLists test');
    let output = "";
    output += `
            <table>
                            <tr>
                                <th class="playlist-cover"></th>
                                <th class="playlist-name">제목</th>
                                <th class="playlist-date">날짜</th>
                                <th class="playlist-hits">조회수</th>
                             </tr>
        `;
    for (let i in list) {
        output += `
                            <tr>
                                <td class="playlist-cover"><img src="/upload/${list[i].pinfoCoverName}" width="50" height="50" style="border-radius: 8px;"></td>
                                <td class="playlist-name"><a href="/goToPlaylist/${list[i].pinfoNum}">${list[i].pinfoTitle}</a></td>
                                <td class="playlist-date">${list[i].pinfoDate.substring(0, 4)} / ${list[i].pinfoDate.substring(5, 7)} / ${list[i].pinfoDate.substring(8, 10)}</td>
                                <td class="playlist-hits">${list[i].pinfoHit}</td>
                            </tr>
                `;
    }
    output += `
       </table>
       `;
    $('#myPlaylists').html(output);
}

function myBoardEntries(list) {
    console.log('myBoardEntries test');
    let output = "";
    output += `
        <table>
            <tr>
                <th class="boardNum">No.</th>
                <th class="boardType">게시판 유형</th>
                <th class="boardTitle">제목</th>
                <th class="boardDate">작성일</th>
                <th class="boardLike">좋아요</th>
                <th class="boardHit">조회수</th>
            </tr>
    `
    /*
bcontent , bdate, bdislike, bfile, bfileName, bhit, blike, bnum, btitle,
btype, bupdateDate, bwriter

*/
    for (let i in list) {
        output += `
                            <tr>
                                <td class="boardNum"><a href="/bView/${list[i].bnum}">${list[i].bnum}</a></td>
                                <td class="boardType">${list[i].btype}</td>
                                <td class="boardTitle"><a href="/bView/${list[i].bnum}">${list[i].btitle}</a></td>
                                <td class="boardDate">${list[i].bdate.substring(0, 4)} / ${list[i].bdate.substring(5, 7)} / ${list[i].bdate.substring(8,10)}</td>
                                <td class="boardLike">${list[i].blike}</td>
                                <td class="boardHit">${list[i].bhit}</td>
                            </tr>
                          
                `;
    }
    output += `
       </table>
       `;
    $('#myBoardEntries').html(output);
}

function myCommentEntries(list) {
    console.log('myCommentEntries test');
    let output = "";
    output += `
        <table>
            <tr>
                <th class="boardNum">게시글 번호</th>
                <th class="cContent">내용</th>
                <th class="cDate">작성일</th>
                <th class="cLike">좋아요</th>
            </tr>
    `

    for (let i in list) {
        output += `
                            <tr>
                               <td class="boardNum"><a href="/bView/${list[i].cbnum}">${list[i].cbnum}</a></td>
                               <td class="cContent">${list[i].ccontent}</td>
                               <td class="cDate">${list[i].cdate}</td>
                               <td class="cLike">${list[i].clike}</td>
                            </tr> 
                `;
    }
    output += `
       </table>
       `;
    $('#myCommentEntries').html(output);
}

$('#myInfo').hide();

$('#myInfoTab').click(() => {
    $('#myInfo').show();
    $('#myPage').hide();
});

$('#myPageTab').click(() => {
    $('#myPage').show();
    $('#myInfo').hide();
});

let modify = $('#modify');
$('.modifyBtn').hide();
$('#modPassId').hide();


modify.click(() => {
    modify.hide();
    $('.currentProfile').hide();
    $('#modPassId').show();
    $('.modifyBtn').show();
    $('form').css("height", 'auto');
});


let modifyEmail = $('#modifyEmail');
let modifyPhone = $('#modifyPhone');
let check4 = $('#check4');
let userEmail = $('#userEmail');
let userPhone = $('#userPhone');

check4.hide();

modifyEmail.click(() => {
    modifyEmail.hide();

    if (modifyPhone.hide()) {
        modifyPhone.show();
    }

    userEmail.focus();
    userEmail.css("border-bottom", "1px solid black");
    userEmail.attr("disabled", false);

    check4.show();

    let checkEmail = $('#checkEmail');
    let check_email = false;

    checkEmail.click(() => {
        $.ajax({
            type: "POST",
            url: "/emailCheck",
            data: {"UserEmail": userEmail.val()},
            dataType: "text",
            success: (uuid) => {
                console.log(uuid);
                check4.empty();
                check4.append(`<input type="text" id="uuid1" size="25"/>`);
                check4.append(`<input type="button" value="인증" id="btnUUID" data-value="${uuid}"/>`);
            },
            error: () => {
                alert('emailCheck 통신 실패');
            }
        });
    });

    $(document).on('click', '#btnUUID', function () {
        let uuid = $(this).data("value");
        let uuid1 = $('#uuid1').val();

        if (uuid == uuid1) {
            alert('이메일이 인증되었습니다');
            check4.hide();
            userEmail.attr('readonly', true);
            check_email = true;
        } else {
            alert('이메일 인증 실패했습니다. 인증번호를 확인해주세요.');
            $('#uuid').val("");
            check_email = false;
        }
    });
});

modifyPhone.click(() => {
    modifyPhone.hide();
    if (modifyEmail.hide()) {
        modifyEmail.show();
    }
    userPhone.focus();
    userPhone.css("border-bottom", "1px solid black");
    userPhone.attr("disabled", false);

    userPhone.keyup(function (e) {
        if (e.keyCode == 13) {
            checkPhone();
        }
    });
});

