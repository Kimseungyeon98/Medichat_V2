<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.all.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.min.css" rel="stylesheet">

<script type="text/javascript">
    window.onload = function() {
        //알림 타입 변수 저장
        var alertType = '${alertType}';

        Swal.fire({
        	title: '<div style="font-weight:700; font-size: 17px; color: #4a4a4a;">${message}</div>',
            text: '${message2}',
            icon: alertType,// 알림 아이콘 (success, error, warning, info)
            confirmButtonText: '확인',
            confirmButtonColor: "#41A652",
            cancelButtonColor: "#E60634"
        }).then((result) => {
            if (result.isConfirmed) {
                // 확인 버튼 클릭 시 리다이렉트
                window.location.href = '${url}';
            }
        });
    };
</script>
