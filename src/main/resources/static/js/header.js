$(function(){
    const $headerNotification = $('#header-notification');
    const $headerNotificationUnreaded = $('#header-notification-unreaded');
    const $notiBox = $('#noti_box');

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
                <div class="noti-item-createdDate fs-11 text-black-5">${item.sentDate ? 9999-99-99 : item.sentDate}</div>
                ${item.link ? `<div class="noti-item-link">${item.link}</div>` : ''}
            </div>`;
    }

    function fetchNotifications(notifications) {
        $headerNotificationUnreaded.text(notifications.length);

        if (notifications.length > 0) {
            const html = notifications.map(buildNotificationItem).join('');
            $notiBox.html(html);
        } else {
            $notiBox.html('<div id="noti_noneNoti" class="fw-7 fs-17 text-dark-7 text-center">알림이 없습니다!</div>');
        }
    }
    function fetchNotificationsCount(count) {
        $headerNotificationUnreaded.text(count);
    }

    $headerNotification.add($headerNotificationUnreaded).on('click', () => {
        ring();
        initNotificationList();
    });
    if(loginUser){
        /* 로딩 직후 init */
        $.ajax({
            url: "/notifications/getCount",
            dataType: 'json',
            success: function(count){
                if(count>0){
                    ring();
                    fetchNotificationsCount(count);
                }
            },
            error: () => alert('ajax 오류')
        });
    }

    function initNotificationList(){
        $.ajax({
            url: "/notifications/get",
            dataType: 'json',
            success: fetchNotifications,
            error: () => alert('ajax 오류')
        });
    }

    $notiBox.on('click', '.noti-item', function () {
        const notiNum = $(this).attr('data-notiNum');
        $.ajax({
            url: "/notifications/read",
            data: { code: notiNum },
            dataType: 'json',
            success: fetchNotifications,
            error: () => alert('네트워크 오류')
        });
    });

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

});