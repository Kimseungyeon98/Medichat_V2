$(function(){
    const $headerNotification = $('#header-notification');
    const $headerNotificationUnreaded = $('#header-notification-unreaded');
    const $notiBox = $('#noti_box');
    const $profile = $('#header-profile');
    const $loginText = $('#header-login-text');
    const $registerText = $('#header-register-text');
    const $statusDiv = $('#header-status-div');
    const $loginDiv = $('#header-login-div');
    const $registerDiv = $('#header-register-div');
    const $noti = $('#header-notification, #header-notification-unreaded');
    const $notiDiv = $('#noti_div');
    const $overlay = $('#overlay');
    const $body = $('body');

    $headerNotification.add($headerNotificationUnreaded).on('click', () => {
        initNotification();
    });

    if(loginUser){
        /* 로딩 직후 init */
        ring();
        initNotification();

        const eventSource = new EventSource("/notifications/subscribe");

        eventSource.onmessage = (event) => {
            //const data = JSON.parse(event.data); // 당장 data를 활용할 건 없음
            // 또는 화면에 알림 표시용 DOM 조작
            ring();
            initNotification();
        };

        eventSource.onerror = (event) => {
            console.error("SSE 오류 발생", event);
            eventSource.close();
            // 재연결 로직 필요 시 구현
        };
    }

    $notiBox.on('click', '.noti-item', function () {
        const notiNum = $(this).attr('data-notiNum');
        $.ajax({
            url: "/notifications/read",
            data: { code: notiNum },
            dataType: 'json',
            error: () => alert('네트워크 오류')
        });
        initNotification();
    });

    // 마우스 오버/아웃 - 로그인 영역
    $loginText.on('click', () => $loginDiv.toggle());
    $loginDiv.on('click', () => $loginDiv.toggle());

    // 마우스 오버/아웃 - 회원가입 영역
    $registerText.on('click', () => $registerDiv.toggle());
    $registerDiv.on('click', () => $registerDiv.toggle());

    // 마우스 오버/아웃 - 프로필 영역
    $profile.on('click', () => $statusDiv.toggle());
    $statusDiv.on('click', () => $statusDiv.toggle());

    // 알림 클릭 시
    $noti.on('click', () => {
        $notiDiv.show();
        $overlay.show();
        $body.css('overflow', 'hidden');
    });

    // 오버레이 클릭 시
    $overlay.on('click', () => {
        $notiDiv.hide();
        $overlay.hide();
        $body.css('overflow', 'auto');
    });


    function ring() {
        const img = $headerNotification.get(0);
        img.src = "/images/icon/notification-bell.png";
        img.classList.add('bell-shake');

        img.addEventListener('animationend', () => {
            img.classList.remove('bell-shake');
            img.src = "/images/icon/notification.png";
        }, { once: true });
    }

    function buildNotificationItem(item) {
        let classes = 'noti-item';
        if (item.isRead) {
            classes += ' noti-item-readed bg-gray-2';
        } else classes += item.priority === 0 ? ' noti-item-unreaded' : ' noti-item-priority';

        let categoryText;
        switch (item.category.label) {
            case '공통' : categoryText = '진료 관련 알림'; break;
            case '병원' : categoryText = '커뮤니티 관련 알림'; break;
            case '약국' : categoryText = '정보 관련 알림'; break;
            case '' : categoryText = '상담 관련 알림'; break;
            default: categoryText = '일반 관련 알림'; break;
        }

        return `
                <div data-notiNum="${item.code}" class="${classes}">
                    <div class="d-flex align-items-center">
                        ${item.priority === 1 ? '<div class="red-circle"></div><div class="text-dange fw-7 fs-13 me-1">중요</div>' : ''}
                        <div class="noti-item-message fs-15">
                            <div class="noti-item-message-wrap">${item.message}</div>
                        </div>
                    </div>
                    <div class="noti-item-category fs-13 fw-7">${categoryText}</div>
                    <div class="noti-item-createdDate fs-11 text-black-5">${item.sentDate ? item.sentDate : 9999-99-99}</div>
                    <div class="d-flex align-items-center">
                        ${item.link ? `<botton class="noti-item-linkBtn btn btn-sm btn-outline-primary me-1" data-link="${item.link}">링크</botton>` : ''}
                        <botton class="noti-item-delBtn btn btn-sm btn-outline-danger ms-1">삭제</botton>
                    </div>
                </div>
                `;
    }


    function initNotificationList(){
        $.ajax({
            url: "/notifications/get",
            dataType: 'json',
            success: function (notifications) {
                if (notifications.length > 0) {
                    const html = notifications.map(buildNotificationItem).join('');
                    $notiBox.html(html);
                } else {
                    $notiBox.html('<div id="noti_noneNoti" class="fw-7 fs-17 text-dark-7 text-center">알림이 없습니다!</div>');
                }
            },
            error: () => alert('initNotificationList 오류')
        });
    }
    function initNotificationCount(){
        $.ajax({
            url: "/notifications/getCount",
            dataType: 'json',
            success: function (count) {
                $headerNotificationUnreaded.text(count);
            },
            error: () => alert('initNotificationCount 오류')
        });
    }
    function initNotification(){
        initNotificationList();
        initNotificationCount();
    }
    function notificationDelBtn(code){
        $.ajax({
            url:"/notifications/"+code,
            success: initNotification(),
            error: () => alert('deleteNotificationBtn 오류')
        });
    }
    function notificationLinkBtn(link){
        location.href=link;
    }
});