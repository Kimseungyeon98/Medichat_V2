$(function(){
    /*const $headerNotification = $('#header-notification');
    const $headerNotificationUnreaded = $('#header-notification-unreaded');
    const $notiBox = $('#noti_box');

    function ring() {
        const img = $headerNotification.get(0);
        img.src = "/images/icon/notification-bell.png";
        img.classList.add('bell-shake');

        img.addEventListener('animationend', () => {
            img.classList.remove('bell-shake');
            img.src = "/images/notification.png";
        }, { once: true });
    }

    function buildNotificationItem(item) {
        let classes = 'noti-item';
        if (item.noti_isRead === 1) classes += ' noti-item-readed bg-gray-2';
        else classes += item.noti_priority === 0 ? ' noti-item-unreaded' : ' noti-item-priority';

        let categoryText;
        switch (item.noti_category) {
            case 1: categoryText = '진료 관련 알림'; break;
            case 2: categoryText = '커뮤니티 관련 알림'; break;
            case 3: categoryText = '정보 관련 알림'; break;
            case 5: categoryText = '상담 관련 알림'; break;
            default: categoryText = '일반 관련 알림'; break;
        }

        return `
            <div data-notiNum="${item.noti_num}" class="${classes}">
                <div class="d-flex align-items-center">
                    ${item.noti_priority === 1 ? '<div class="red-circle"></div><div class="text-dange fw-7 fs-13 me-1">중요</div>' : ''}
                    <div class="noti-item-message fs-15">
                        <div class="noti-item-message-wrap">${item.noti_message}</div>
                    </div>
                </div>
                <div class="noti-item-category fs-13 fw-7">${categoryText}</div>
                <div class="noti-item-createdDate fs-11 text-black-5">${item.noti_createdDate}</div>
                ${item.noti_link ? `<div class="noti-item-link">${item.noti_link}</div>` : ''}
            </div>`;
    }

    function fetchNotifications(param) {
        if (param.result === 'success') {
            $headerNotificationUnreaded.text(param.cnt);

            if (param.list.length > 0) {
                const html = param.list.map(buildNotificationItem).join('');
                $notiBox.html(html);
            } else {
                $notiBox.html('<div id="noti_noneNoti" class="fw-7 fs-17 text-dark-7 text-center">알림이 없습니다!</div>');
            }
        } else {
            alert(param.result === 'fail' ? 'ajax 오류' : '네트워크 오류');
        }
    }

    $headerNotification.add($headerNotificationUnreaded).on('click', () => {
        ring();
        $.ajax({
            url: "/notification-json",
            dataType: 'json',
            success: fetchNotifications,
            error: () => alert('ajax 오류')
        });
    });

    $notiBox.on('click', '.noti-item', function () {
        const notiNum = $(this).attr('data-notiNum');
        $.ajax({
            url: "/notificationReaded",
            data: { noti_num: notiNum },
            dataType: 'json',
            success: fetchNotifications,
            error: () => alert('네트워크 오류')
        });
    });*/

    if (loginUser) {
        const $profile = $('#header-profile');
        const $statusDiv = $('#header-status-div');
        const $noti = $('#header-notification, #header-notification-unreaded');
        const $notiDiv = $('#noti_div');
        const $overlay = $('#overlay');
        const $body = $('body');

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
    }
});