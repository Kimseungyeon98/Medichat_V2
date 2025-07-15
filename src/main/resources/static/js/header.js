$(function () {
    // === 공통 DOM 캐싱 ===
    const $body = $('body');
    const $overlay = $('#overlay');

    const $headerNotification = $('#header-notification');
    const $headerNotificationUnreaded = $('#header-notification-unreaded');
    const $noti = $headerNotification.add($headerNotificationUnreaded);
    const $notiBox = $('#noti_box');
    const $notiDiv = $('#noti_div');

    const $profile = $('#header-profile');
    const $loginText = $('#header-login-text');
    const $registerText = $('#header-register-text');
    const $statusDiv = $('#header-status-div');
    const $loginDiv = $('#header-login-div');
    const $registerDiv = $('#header-register-div');

    // === 공통 toggle 함수 ===
    function toggleDiv($el) {
        $el.toggle();
    }

    // === 알림 벨 애니메이션 ===
    function ring() {
        const img = $headerNotification.get(0);
        img.src = "/images/icon/notification-bell.png";
        img.classList.add('bell-shake');
        img.addEventListener('animationend', () => {
            img.classList.remove('bell-shake');
            img.src = "/images/icon/notification.png";
        }, { once: true });
    }

    // === 알림 리스트 생성 ===
    function buildNotificationItem(item) {
        const isRead = item.isRead ? 'noti-item-readed bg-gray-2' : (item.priority === 0 ? 'noti-item-unreaded' : 'noti-item-priority');
        const categoryText = {
            '공통': '진료 관련 알림',
            '병원': '커뮤니티 관련 알림',
            '약국': '정보 관련 알림',
            '': '상담 관련 알림'
        }[item.category.label] || '일반 관련 알림';

        return `
            <div data-notiNum="${item.code}" class="noti-item ${isRead}">
                <div class="d-flex align-items-center">
                    ${item.priority === 1 ? '<div class="red-circle"></div><div class="text-dange fw-7 fs-13 me-1">중요</div>' : ''}
                    <div class="noti-item-message fs-15">
                        <div class="noti-item-message-wrap">${item.message}</div>
                    </div>
                </div>
                <div class="noti-item-category fs-13 fw-7">${categoryText}</div>
                <div class="noti-item-createdDate fs-11 text-black-5">${item.sentDate || '9999-99-99'}</div>
                <div class="d-flex align-items-center">
                    ${item.link ? `<button class="noti-item-linkBtn btn btn-sm btn-outline-primary me-1" data-link="${item.link}">링크</button>` : ''}
                    <button class="noti-item-delBtn btn btn-sm btn-outline-danger ms-1" data-code="${item.code}">삭제</button>
                </div>
            </div>
        `;
    }

    // === 알림 목록 초기화 ===
    function initNotificationList() {
        $.ajax({
            url: "/notifications",
            method: "GET",
            dataType: 'json',
            success: function (notifications) {
                const html = notifications.length > 0
                    ? notifications.map(buildNotificationItem).join('')
                    : '<div id="noti_noneNoti" class="fw-7 fs-17 text-dark-7 text-center">알림이 없습니다!</div>';
                $notiBox.html(html);
            },
            error: () => alert('initNotificationList 오류')
        });
    }

    // === 안 읽은 알림 수 초기화 ===
    function initNotificationCount() {
        $.ajax({
            url: "/notifications/count",
            method: "GET",
            dataType: 'json',
            success: (count) => $headerNotificationUnreaded.text(count),
            error: () => alert('initNotificationCount 오류')
        });
    }

    // === 전체 알림 초기화 ===
    function initNotification() {
        initNotificationList();
        initNotificationCount();
    }

    // === 알림 읽음 처리 ===
    function notificationReadBtn(code) {
        $.ajax({
            url: `/notifications/${code}`,
            method: "PATCH",
            success: () => initNotification(),
            error: () => alert('notificationReadBtn 오류')
        });
    }

    // === 알림 삭제 처리 ===
    function notificationDelBtn(code) {
        $.ajax({
            url: `/notifications/${code}`,
            method: "DELETE",
            success: initNotification,
            error: () => alert('deleteNotificationBtn 오류')
        });
    }

    // === 알림 링크 이동 ===
    function notificationLinkBtn(link) {
        location.href = link;
    }

    // === 이벤트 바인딩 ===
    $noti.on('click', () => {
        $notiDiv.show();
        $overlay.show();
        $body.css('overflow', 'hidden');
        initNotification();
    });

    $overlay.on('click', () => {
        $notiDiv.hide();
        $overlay.hide();
        $body.css('overflow', 'auto');
    });

    $notiBox.on('click', '.noti-item', function () {
        notificationReadBtn($(this).attr('data-notiNum'));
        initNotification();
    });

    $notiBox.on('click', '.noti-item-linkBtn', function (e) {
        e.stopPropagation();
        notificationLinkBtn($(this).attr('data-link'));
    });

    $notiBox.on('click', '.noti-item-delBtn', function (e) {
        e.stopPropagation();
        notificationDelBtn($(this).attr('data-code'));
    });

    $loginText.on('click', () => toggleDiv($loginDiv));
    $loginDiv.on('click', () => toggleDiv($loginDiv));

    $registerText.on('click', () => toggleDiv($registerDiv));
    $registerDiv.on('click', () => toggleDiv($registerDiv));

    $profile.on('click', () => toggleDiv($statusDiv));
    $statusDiv.on('click', () => toggleDiv($statusDiv));

    // === SSE 알림 연결 ===
    if (loginUser) {
        ring();
        initNotification();

        const eventSource = new EventSource("/notifications/subscribe");

        eventSource.onmessage = () => {
            ring();
            initNotification();
        };

        eventSource.onerror = (event) => {
            console.error("SSE 오류 발생", event);
            eventSource.close();
        };
    }
});
