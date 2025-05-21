<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.0/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.0/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.0.0/index.global.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.0/js/bootstrap.bundle.min.js"></script>
<style>
/* 스타일 정의 */
.memberInfo {
    display:flex;
    margin-top:40px;
}

.leftInfo, .rightInfo {
    flex:1;
    padding:0 10px;
}

.leftInfo li, .rightInfo li {
    list-style-type:none;
    margin-bottom:10px;
}

.leftInfo li:first-child, .rightInfo li:first-child {
    font-size:25px;
    font-weight:bold;
}

.leftInfo li:not(:first-child), .rightInfo li:not(:first-child) {
    font-size:15px;
}

.collapsible {
    cursor:pointer;
    user-select:none;
    background-color:#f0f0f0;
    padding:10px;
    border:none;
    text-align:left;
    outline:none;
    width:100%;
}

.collapsible:hover {
    background-color:#ccc;
}

.content {
    display:none;
    padding:0 18px;
    overflow:hidden;
    background-color:#f9f9f9;
}

@media (max-width: 768px) {
    .memberInfo {
        flex-direction:column;
    }
}

.dropdown {
    position:relative;
    display:inline-block;
    float:right;
}

.dropbtn {
    border:none;
    border-radius:4px;
    background-color:rgba(249, 247, 242, 0.7);
    font-weight:400;
    color:rgb(37, 37, 37);
    width:100px;
    text-align:left;
    cursor:pointer;
    font-size:15px;
}

.dropdown-content {
    display:none;
    position:absolute;
    z-index:1;
    font-weight:400;
    background-color:#fff;
    min-width:100px;
}

.dropdown-content a {
    display:block;
    text-decoration:none;
    color:rgb(37, 37, 37);
    font-size:12px;
    padding:12px;
}

.dropdown-content a:hover {
    background-color:#ececec;
}

.dropdown:hover .dropdown-content {
    display:block;
}

#calendar {
    padding:30px;
    width:650px;
}

.fc .fc-button-primary {
    background-color:#fff;
    border-color:#fff;
    color:#000;
    width:80px;
}

.fc .fc-button-primary:hover {
    background-color:#d8f3dc;
    border-color:#fff;
    color:#fff;
}

.fc .fc-button-primary:focus, .fc .fc-button-primary:active {
    outline:none;
    box-shadow:none;
}

.fc .fc-button-primary:active {
    background-color:transparent !important;
    outline:none !important;
    box-shadow:none !important;
    border-color:transparent !important;
}

.fc .fc-button-primary:disabled {
    background-color:#fff;
    border-color:#fff;
    color:#000;
}

.fc-day-sun a {
    color:red;
    text-decoration:none;
}

.fc-day-sat a {
    color:blue;
    text-decoration:none;
}

.fc-day-mon a, .fc-day-tue a, .fc-day-wed a, .fc-day-thu a, .fc-day-fri a {
    color:black;
    text-decoration:none;
}

.fc .fc-daygrid-day.fc-day-today {
    background-color:#fafffb;
}

.circle {
    height:20px;
    width:20px;
    background-color:#ff9f89;
    border-radius:50%;
    display:inline-block;
    position:absolute;
    bottom:60px;
    right:7px;
}
</style>
</head>
<body>
    <div class="memberInfo">
        <div class="leftInfo">
            <ul>
                <li style="font-size:17px; font-weight:bold;">이름</li>
                <li style="font-size:13px;">${member.mem_name}</li>
                <li style="font-size:17px; font-weight:bold;">전화번호</li>
                <li style="font-size:13px;">${member.mem_phone}</li>
                <li style="font-size:17px; font-weight:bold;">생년월일</li>
                <li style="font-size:13px;">${member.mem_birth}</li>
                <li style="font-size:17px; font-weight:bold;">이메일</li>
                <li style="font-size:13px;">${member.mem_email}</li>
            </ul>
        </div>
        <div class="rightInfo">
            <ul>
                <li style="font-size:17px; font-weight:bold;">우편번호</li>
                <li style="font-size:13px;">${member.mem_zipcode}</li>
                <li style="font-size:17px; font-weight:bold;">주소</li>
                <li style="font-size:13px;">${member.mem_address1} ${member.mem_address2}</li>
                <li style="font-size:17px; font-weight:bold;">가입일</li>
                <li style="font-size:13px;">${member.mem_reg}</li>
                <c:if test="${!empty member.mem_modify}">
                    <li style="font-size:17px; font-weight:bold;">정보 수정일</li>
                    <li style="font-size:13px;">${member.mem_modify}</li>
                </c:if>
            </ul>
        </div>
    </div>
    <!-- FullCalendar -->
    <div class="container mt-4">
        <div id="calendar"></div>
    </div>

    <script>
    document.addEventListener('DOMContentLoaded', function() {
        $.ajax({
            url: '${pageContext.request.contextPath}/reservation/myPageCalendar',
            type: 'GET',
            success: function(param) {
                if (param.result === 'logout') {
                    alert("로그인 후 이용해주세요.");
                    location.href = '/member/login';
                } else if (param.result === 'success') {
                    initializeCalendar(param.reservations);
                }
            }
        });

        function initializeCalendar(reservations) {
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
                height: 'auto',
                width: 'auto',
                headerToolbar: {
                    left: 'title',
                    right: 'today prev,next'
                },
                initialView: 'dayGridMonth',
                locale: 'ko',
                dayCellContent: function(e) {
                    var dayNumber = e.date.getDate();
                    var html = '<div class="fc-daygrid-day-number">' + dayNumber + '</div>';
                    return { html: html };
                },
                fixedWeekCount: false,
                events: reservations.map(function(date) {
                    return {
                        start: date,
                        display: 'background',
                        backgroundColor: 'transparent',
                        classNames: ['circle-event']
                    };
                }),
                eventContent: function(arg) {
                    var dotEl = document.createElement('div');
                    dotEl.classList.add('circle');
                    return { domNodes: [dotEl] };
                }
            });

            calendar.render();
        }
    });
    </script>
</body>
</html>
