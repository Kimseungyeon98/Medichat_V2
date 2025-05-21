<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/shg.css" type="text/css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link rel="stylesheet" href="styles.css">
<style>
.tooltip-container {
     position: relative;
    display: flex;
    margin-left: auto;
    margin-right: 100px; /* 아이콘을 약간 오른쪽으로 이동 */
    align-items: center;
}

.info-icon {
    font-size: 18px; /* 아이콘 크기 조정 */
    padding: 5px; /* 아이콘 주위의 여백 */
    display: inline-block;
}

.tooltip-content {
    position: absolute;
    top: 100%;
    left: 50%;
    transform: translateX(-50%);
    display: none; /* 기본적으로 숨김 */
    z-index: 1;
    background-color: white;
    padding: 5px;
    border-radius: 5px;
    box-shadow: 0px 0px 5px rgba(0,0,0,0.1);
    min-width: 200px;
}

/* 아이콘에만 호버했을 때 툴팁 표시 */
.info-icon:hover + .tooltip-content {
    display: block; 
}

.info-icon:hover {
    background-color: #fff;
    cursor: pointer;
    transition: background-color 0.3s ease;
}
#calendar {
    margin: 0 auto;
    width: 80%;
    max-width: 800px;
}
.fc .fc-button-primary{
    background-color: #fff;
    border-color: #fff;
    color: #000;
    width: 80px;
}
.fc .fc-button-primary:hover{
    background-color: #d8f3dc;
    border-color: #fff;
    color: #fff;
}
.fc .fc-button-primary:focus, .fc .fc-button-primary:active {
    outline: none;
    box-shadow: none;
}
.fc .fc-button-primary:active{
    background-color: transparent !important;
    outline: none !important;
    box-shadow: none !important;
    border-color: transparent !important;
}
.fc .fc-button-primary:disabled{
    background-color: #fff;
    border-color: #fff;
    color: #000;
}
.fc-day-sun a {
    color: red;
    text-decoration: none;
}
.fc-day-sat a {
    color: blue;
    text-decoration: none;
}
.fc-day-mon a, .fc-day-tue a, .fc-day-wed a, .fc-day-thu a, .fc-day-fri a {
    color: black;
    text-decoration: none;
}
.fc .fc-daygrid-day.fc-day-today {
    background-color: #fafffb;
}
.fc .fc-highlight{
    background-color: #e4f8e5;
}
.fc-daygrid-day-selected {
    background-color: #e4f8e5;
}
</style>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script src="${pageContext.request.contextPath}/js/schedule.list.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    // 세션에서 가져온 doc_num과 regularDayOff를 초기화
    const doc_num = ${doc_num};
    const regularDayOffStr = '${regularDayOff}';
    const regularDayOff = regularDayOffStr ? regularDayOffStr.split(',').map(Number) : [];

    let holidays = []; // 휴일 정보를 저장할 배열
    let modifiedTimes = {}; // 수정된 시간 데이터를 저장할 객체

    // FullCalendar 초기화
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        selectable: true, // 사용자가 날짜를 선택할 수 있게 설정
        height: 'auto',
        width: 'auto',
        headerToolbar: {
            left: 'title',
            right: 'today prev,next'
        },
        initialView: 'dayGridMonth', // 초기 보여줄 뷰 설정
        locale: 'ko', // 로케일 설정
        dayCellContent: function(e) {
            // 날짜 셀 내용 설정 (1일, 2일 -> '일' 빼고 1, 2만 보이게)
            var dayNumber = e.date.getDate();
            var html = '<div class="fc-daygrid-day-number">' + dayNumber + '</div>';
            return { html: html };
        },
        fixedWeekCount: false, // 다음 달의 날짜가 보여지지 않게 설정
        dateClick: function(info) {
            // 날짜 클릭 시 호출될 함수
            displayTimes(info.dateStr);
            $('td.fc-daygrid-day').removeClass('fc-daygrid-day-selected');
            $(info.dayEl).addClass('fc-daygrid-day-selected').attr('data-date', info.dateStr);
        },
        dayCellDidMount: function(info) {
            // 특정 날짜 셀에 클래스를 적용하는 함수 호출
            applyClassesToCell(info.el, info.date);
        },
        datesSet: function(info) {
            // datesSet 이벤트는 달력이 새로 렌더링될 때마다 호출됨
            const days = document.querySelectorAll('.fc-daygrid-day');
            days.forEach(dayEl => {
                const date = new Date(dayEl.getAttribute('data-date'));
                applyClassesToCell(dayEl, date);
            });
        }
    });
    calendar.render();
    
 	// 특정 날짜 셀에 클래스를 적용하는 함수
    function applyClassesToCell(cell, date) {
        cell.classList.remove('regular-day-off', 'holiday-off', 'working-day');
        const day = date.getDay();
        const dateString = date.toISOString().split('T')[0];

        if (regularDayOff.includes(day)) {
            cell.classList.add('regular-day-off');
        }

        if (Array.isArray(holidays)) {
            holidays.forEach(function(holi) {
                if (holi.holi_date == dateString) {
                    if (holi.holi_status == 1) {
                        cell.classList.add('holiday-off');
                    } else if (holi.holi_status == 2) {
                        cell.classList.remove('regular-day-off');
                        cell.classList.add('working-day');
                    }
                }
            });
        }
    }

    // 선택한 날짜의 근무/휴무 시간을 표시하는 함수
    function displayTimes(date) {
    $('#time-buttons').empty();
    // 초기화된 modifiedTimes
    modifiedTimes = {};
    // AJAX 요청을 통해 근무 시간 정보를 가져옴
    $.ajax({
        url: '/schedule/workingTimes',
        method: 'get',
        data: { doc_num: doc_num, res_date: date },
        dataType: 'json',
        success: function(param) {
            if (param.result == 'success') {
                const doc_stime = param.workingHours.DOC_STIME;
                const doc_etime = param.workingHours.DOC_ETIME;
                const reservedTimes = param.reservedTimes;
                const allTimes = generateTimesForDay(doc_stime, doc_etime);
                let output = '';
                output += '<div class="tooltip-container">';
                output += '<i class="fas fa-question-circle info-icon"></i>';
                output += '<div class="tooltip-content">';
                output += '<div class="legend-item"><div class="green-circle"></div><span>근무하는 시간입니다.</span></div>';
                output += '<div class="legend-item"><div class="white-circle"></div><span>근무하지 않는 시간입니다.</span></div>';
                output += '<div class="legend-item"><div class="gray-circle"></div><span>예약되어 있는 시간입니다.<br> 수정할 수 없습니다.</span></div>';
                output += '<div class="legend-item"><div class="btn-circle-info"></div><span>※ 근무수정하기 버튼을 클릭해야만 근무를 수정할 수 있습니다.</span></div>';
                output += '</div>';
                output += '</div>';
                output += '<div class="time-section">';
                output += '<h5 class="time-header mt-3">오전</h5><div class="time-row">';
                
                let arr = [];
                let arr2 = [];
                allTimes.forEach((time, index) => {
                	if (time < "12:00") {
						arr.push(time);
					}
				});
				allTimes.forEach((time, index) => {
                	if (time >= "12:00") {
						arr2.push(time);
					}
				});
				
                arr.forEach((time, index) => {
                    if (index > 0 && index % 4 == 0) {
                        output += '</div><div class="time-row">';
                    }
                    if (time < "12:00") {
                        if (index > 0 && index % 4 == 0) {
                            output += '</div><div class="time-row">';
                        }
                        output += '<button id="schedule_mobtn" class="' + getButtonClass(time, date, reservedTimes) + '" data-time="' + time + '">' + time + '</button>';
                    }
                });
                output += '</div></div>';

                output += '<div class="time-section">';
                output += '<h5 class="time-header">오후</h5><div class="time-row">';
                arr2.forEach((time, index) => {
                    if (time >= "12:00") {
                        if (index > 0 && index % 4 == 0) {
                            output += '</div><div class="time-row">';
                        }
                        output += '<button id="schedule_afbtn" class="' + getButtonClass(time, date, reservedTimes) + '" data-time="' + time + '">' + time + '</button>';
                    }
                });
                output += '</div></div>';
                output += getButtonRowHtml();
                $('#time-buttons').html(output);

                handleButtonEvents(date, reservedTimes);
                // 선택된 날짜 셀 유지
                $('td.fc-daygrid-day[data-date="' + date + '"]').addClass('fc-daygrid-day-selected');
            } else if (param.result == 'logout') {
                alert('로그인이 필요합니다.');
            } else if (param.result == 'wrongAccess') {
                alert('잘못된 접근입니다.');
            }
        },
        error: function() {
            alert('근무 시간을 가져오는 데 실패했습니다.');
        }
    });
}

    // 시간 버튼의 CSS 클래스를 반환하는 함수
    function getButtonClass(time, date, reservedTimes) {
    let buttonClass = 'working-time';
    const selectedDate = new Date(date);
    const selectedDay = selectedDate.getDay();
    if (regularDayOff.includes(selectedDay)) {
        buttonClass = 'time-off';
    }
    holidays.forEach(function(holi) {
        if (holi.holi_status == 1 && holi.holi_date == date && holi.holi_time == time) {
            buttonClass = 'time-off';
        }
        if (holi.holi_status == 2 && holi.holi_date == date && holi.holi_time == time) {
            buttonClass = 'working-time-red';
        }
    });
    if (reservedTimes.includes(time)) {
        buttonClass = 'reserved-time'; // 예약된 시간은 비활성화
    }
    if (modifiedTimes[time] !== undefined) {
        buttonClass = modifiedTimes[time] === 1 ? 'time-off' : 'working-time';
    }
    return buttonClass;
}


    // 시간 버튼의 클릭 이벤트를 처리하는 함수
    function handleButtonEvents(date, reservedTimes) {
    $('.modify-btn').click(function() {
        modifiedTimes = {}; // 근무수정버튼을 클릭할 때마다 수정된 시간을 초기화
        $('.working-time, .time-off, .working-time-red').prop('disabled', false).addClass('active');
        $(this).hide();
        $('.complete-modify-btn, .cancel-btn').show();
        
        // 이미 예약된 시간은 비활성화 상태 유지
        reservedTimes.forEach(function(time) {
            $('button[data-time="' + time + '"]').prop('disabled', true).removeClass('active');
        });
    });

    $('.cancel-btn').click(function() {
        displayTimes(date);
    });

    $(document).off('click', '.active').on('click', '.active', function() {
        const time = $(this).data('time');
        if ($(this).hasClass('working-time')) {
            $(this).removeClass('working-time').addClass('time-off');
            modifiedTimes[time] = 1; // 수정된 상태를 저장
        } else if ($(this).hasClass('time-off')) {
            $(this).removeClass('time-off').addClass('working-time');
            modifiedTimes[time] = 2; // 수정된 상태를 저장
        } else if ($(this).hasClass('working-time-red')) {
            $(this).removeClass('working-time-red').addClass('time-off');
            modifiedTimes[time] = 1; // 수정된 상태를 저장
        }
    });

    $('.complete-modify-btn').click(function() {
        let dataToSend = [];
        Object.keys(modifiedTimes).forEach(function(time) {
            dataToSend.push({
                doc_num: doc_num,
                holi_date: date,
                holi_time: time,
                holi_status: modifiedTimes[time]
            });
        });
        $.ajax({
            url: '/schedule/updateTimes',
            method: 'post',
            contentType: 'application/json',
            data: JSON.stringify(dataToSend),
            success: function(response) {
                if (response.result == 'success') {
                    alert('근무 시간이 성공적으로 업데이트되었습니다.');
                    // 서버에서 업데이트된 데이터를 다시 받아와야 합니다.
                    $.ajax({
                        url: '/schedule/holidays',
                        method: 'get',
                        data: { doc_num: doc_num },
                        dataType: 'json',
                        success: function(param) {
                            holidays = param.holidays || [];
                            // 수정된 데이터가 반영된 새로운 데이터를 화면에 표시
                            displayTimes(date);
                        },
                        error: function() {
                            alert('업데이트된 데이터를 가져오는 데 실패했습니다.');
                        }
                    });
                } else {
                    alert('근무 시간 업데이트에 실패했습니다.');
                    console.error(response.message);
                }
            },
            error: function(xhr, status, error) {
                alert('근무 시간 업데이트 중 오류가 발생했습니다.');
                console.error(error);
            }
        });
    });
}

    // 시간 범위를 생성하는 함수
    function generateTimesForDay(doc_stime, doc_etime) {
        let times = [];
        let [startHour, startMinute] = doc_stime.split(':').map(Number);
        let [endHour, endMinute] = doc_etime.split(':').map(Number);

        for (let hour = startHour; hour <= endHour; hour++) {
            for (let minute = (hour == startHour ? startMinute : 0); minute < 60; minute += 30) {
                if (hour == endHour && minute >= endMinute) break;
                let time = hour.toString().padStart(2, '0') + ':' + minute.toString().padStart(2, '0');
                times.push(time);
            }
        }

        return times;
    }

    // 버튼 행 HTML을 반환하는 함수
    function getButtonRowHtml() {
        return `
        	
            <div class="button-row">
                <input type="button" value="근무수정하기" class="modify-btn">
                <input type="button" value="근무수정완료" class="complete-modify-btn" style="display:none;">
                <input type="button" value="취소" class="cancel-btn" style="display:none;">
            </div>
        `;
    }

    // Ajax로 개별 휴무 데이터를 가져옴
    $.ajax({
        url: '/schedule/holidays',
        method: 'get',
        data: { doc_num: doc_num },
        dataType: 'json',
        success: function(param) {
            holidays = param.holidays || [];
            calendar.render();
        },
        error: function() {
            alert('개별 휴무 데이터를 가져오는 데 실패했습니다.');
        }
    });
});
</script>

<div id="calendar"></div>
<div id="time-buttons"></div>