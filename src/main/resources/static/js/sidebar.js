// 사이드바 열기/닫기 함수
function toggleSidebar() {
    const sidebar = document.getElementById("sidebar");
    const content = document.querySelector(".content");
    const isOpen = sidebar.classList.contains("open");

    sidebar.classList.toggle("open");  // open 클래스를 토글하여 사이드바 열기/닫기
    content.classList.toggle("shift"); // 본문 내용 이동 효과 추가

    // 사이드바 상태를 로컬 스토리지에 저장
    if (isOpen) {
        localStorage.setItem("sidebarOpen", "false");  // 사이드바가 닫히면 'false' 저장
    } else {
        localStorage.setItem("sidebarOpen", "true");   // 사이드바가 열리면 'true' 저장
    }
}

// 페이지 로드 시 사이드바 상태 복원
document.addEventListener("DOMContentLoaded", function() {
    const sidebar = document.getElementById("sidebar");
    const sidebarState = localStorage.getItem("sidebarOpen");

    if (sidebarState === "true") {
        // 로컬 스토리지에서 사이드바가 열려있던 상태로 저장되어 있으면 열기
        sidebar.classList.add("open");
        document.querySelector(".content").classList.add("shift");
    }
});

// 사이드바 영역 외부 클릭 시 사이드바 닫기
document.addEventListener("click", function(event) {
    const sidebar = document.getElementById("sidebar");
    const sidebarToggle = document.getElementById("sidebar-toggle");
    const content = document.querySelector(".content");

    // 클릭한 곳이 사이드바나 토글 버튼이 아니면 사이드바 닫기
    if (!sidebar.contains(event.target) && !sidebarToggle.contains(event.target)) {
        if (sidebar.classList.contains("open")) {
            toggleSidebar();  // 사이드바 닫기
        }
    }
});

$(document).ready(function () {
    var userId = $('#userId').val();

    if (userId) {
        $('#aside').show();
    } else {
        $('#aside').hide();
    }
});

$('#playlistForm').click(() => {
    location.href = "/playlistForm";
});
