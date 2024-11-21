/* 메뉴 1 - 인기 플레이리스트 */

const menuContainer = document.querySelector('.menu-container');
const menu = document.querySelector('.menu');
const leftArrow = document.querySelector('.left-arrow');
const rightArrow = document.querySelector('.right-arrow');

const itemWidth = 100;
const itemsVisible = 3;
const scrollAmount = itemWidth * itemsVisible;

let currentScrollPosition = 0;

rightArrow.addEventListener('click', () => {
    // 오른쪽으로 이동
    const maxScroll = menu.scrollWidth - menuContainer.clientWidth;
    currentScrollPosition = Math.min(currentScrollPosition + scrollAmount, maxScroll);
    menu.style.transform = `translateX(-${currentScrollPosition}px)`;
});

leftArrow.addEventListener('click', () => {
    // 왼쪽으로 이동
    currentScrollPosition = Math.max(currentScrollPosition - scrollAmount, 0);
    menu.style.transform = `translateX(-${currentScrollPosition}px)`;
});


// 메뉴 2 - 장르별 플레이리스트

// const plusPlaylist = document.querySelector('#itemPlus');
// const menu2 = document.querySelector('.menu2');
//
// plusPlaylist.addEventListener('click', () => {
//     // 새로운 메뉴 항목 생성
//     const newItem = document.createElement('div');
//     newItem.classList.add('menu-item2');
//     newItem.textContent = '♡'; // 새로운 항목 내용 설정
//
//     // '-' 버튼 추가
//     const minusButton = document.createElement('button');
//     minusButton.textContent = '-';
//     minusButton.classList.add('minus-button');
//
//     // '-' 버튼 클릭 시 해당 항목 삭제
//     minusButton.addEventListener('click', () => {
//         menu2.removeChild(newItem); // 해당 항목 삭제
//     });
//
//     // 새 항목에 '-' 버튼 추가
//     newItem.appendChild(minusButton);
//
//     // 생성된 항목을 .menu2에 추가
//     menu2.appendChild(newItem);
// });

/* 메뉴 3 - 아티스트 */

const menuContainer3 = document.querySelector('.menu-container3');
const menu3 = document.querySelector('.menu3');
const leftArrow1 = document.querySelector('.left-arrow1');
const rightArrow1 = document.querySelector('.right-arrow1');

const itemWidth1 = 100; // 아이템 한 개의 너비
const itemsVisible1 = 3; // 한 번에 보여줄 아이템 수
const scrollAmount1 = itemWidth1 * itemsVisible1;

let currentScrollPosition1 = 0;

rightArrow1.addEventListener('click', () => {
    const maxScroll1 = menu3.scrollWidth - menuContainer3.clientWidth;
    currentScrollPosition1 = Math.min(currentScrollPosition1 + scrollAmount1, maxScroll1);
    menu3.style.transform = `translateX(-${currentScrollPosition1}px)`;
});

leftArrow1.addEventListener('click', () => {
    currentScrollPosition1 = Math.max(currentScrollPosition1 - scrollAmount1, 0);
    menu3.style.transform = `translateX(-${currentScrollPosition1}px)`;
});


// top5 게시판 작동 메소드



let list = [];
let page = 1;       // 페이지 번호
let limit = 10;      // 한페이지에 출력될 데이터 갯수
const block = 5;    // 한페이지에 출력될 페이지 갯수
let count = 0;      // 전체 데이터 갯수

// 페이지 로딩 후 ajax로 게시글 불러오기
$(() => {
    {
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
                    <td class="po-td po-tt">${list[i].btype}</td>
                    <td class="po-td po-tt"><a class="po-link" href="/bView/${list[i].bnum}">${list[i].btitle}</a></td>
                    <td class="po-td">${list[i].bwriter}</td>
                    <td class="po-td">조회수: ${list[i].bhit}</td>
                    <td class="po-td">♡: ${list[i].blike}</td>
                </tr>
            `;
        }

        $('tbody').empty();
        $('tbody').append(output);

        $(document).on('click', '#numbering a', function(e){
            page = parseInt($(this).data('page'));
            pagingList(page, list);
        });
    } else {
        $('tbody').empty();
    }

}



