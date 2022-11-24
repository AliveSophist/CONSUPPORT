
function regHall(){
	
	var formTag = document.querySelector('#regHallForm');
	
	//ajax start
	$.ajax({
		url: '/owner/regHall', //요청경로
		type: 'post',
		enctype: 'multipart/form-data',
		
		data: new FormData( formTag ),
		
		/*============== FormData 쓰려면 필요함 ==============*/
		processData:false,
		contentType:false,
    	cache: false,
    	/*============== FormData 쓰려면 필요함 ==============*/
    	
		success: function(result) {
			
			if(result>0){
				alert('일단 성공?');
				formTag.reset();
				
				
				//href
				//어디로 날릴까
			}
			else
				alert('작성 하지 않은 항목이 있습니다.');
		},
		error: function() { alert('실패'); }
	});
	//ajax end
}




$(document).ready(function() {
	$('.input-daterange').datepicker({
		format: 'yyyy-mm-dd',
		todayHighlight: true,
		startDate: '+1d',
		autoclose: true
	});

//		format: "yyyy-mm-dd",	//데이터 포맷 형식(yyyy : 년 mm : 월 dd : 일 )
//		startDate: '-10d',	//달력에서 선택 할 수 있는 가장 빠른 날짜. 이전으로는 선택 불가능 ( d : 일 m : 달 y : 년 w : 주)
//		endDate: '+10d',	//달력에서 선택 할 수 있는 가장 느린 날짜. 이후로 선택 불가 ( d : 일 m : 달 y : 년 w : 주)
//		autoclose: true,	//사용자가 날짜를 클릭하면 자동 캘린더가 닫히는 옵션
//		calendarWeeks : false, //캘린더 옆에 몇 주차인지 보여주는 옵션 기본값 false 보여주려면 true
//		clearBtn: true, //날짜 선택한 값 초기화 해주는 버튼 보여주는 옵션 기본값 false
//		datesDisabled: ['2019-06-24', '2019-06-26'],//선택 불가능한 일 설정 하는 배열 위에 있는 format 과 형식이 같아야함.
//		daysOfWeekDisabled: [0, 6],	//선택 불가능한 요일 설정 0 : 일요일 ~ 6 : 토요일
//		daysOfWeekHighlighted: [3], //강조 되어야 하는 요일 설정
//		disableTouchKeyboard: false,	//모바일에서 플러그인 작동 여부 기본값 false 가 작동 true가 작동 안함.
//		immediateUpdates: false,	//사용자가 보는 화면으로 바로바로 날짜를 변경할지 여부 기본값 :false 
//		multidate: false, //여러 날짜 선택할 수 있게 하는 옵션 기본값 :false 
//		multidateSeparator: ",", //여러 날짜를 선택했을 때 사이에 나타나는 글짜 2019-05-01,2019-06-01
//		templates: {
//			leftArrow: '&laquo;',
//			rightArrow: '&raquo;'
//		}, //다음달 이전달로 넘어가는 화살표 모양 커스텀 마이징 
//		showWeekDays: false,// 위에 요일 보여주는 옵션 기본값 : true
//		title: "테스트",	//캘린더 상단에 보여주는 타이틀
//		todayHighlight: true,	//오늘 날짜에 하이라이팅 기능 기본값 :false 
//		toggleActive: true,	//이미 선택된 날짜 선택하면 기본값 : false인경우 그대로 유지 true인 경우 날짜 삭제
//		weekStart: 0,//달력 시작 요일 선택하는 것 기본값은 0인 일요일 
//		language: "ko"	//달력의 언어 선택, 그에 맞는 js로 교체해줘야한다.

















//    locale: {
//    "separator": " ~ ",                     // 시작일시와 종료일시 구분자
//    "format": 'YYYY-MM-DD HH:mm:ss',     // 일시 노출 포맷
//    "applyLabel": "확인",                    // 확인 버튼 텍스트
//    "cancelLabel": "취소",                   // 취소 버튼 텍스트
//    "daysOfWeek": ["일", "월", "화", "수", "목", "금", "토"],
//    "monthNames": ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
//    },
//    timePicker: true,                        // 시간 노출 여부
//    showDropdowns: true,                     // 년월 수동 설정 여부
//    autoApply: true,                         // 확인/취소 버튼 사용여부
//    timePicker24Hour: true,                  // 24시간 노출 여부(ex> true : 23:50, false : PM 11:50)
//    timePickerSeconds: true,                 // 초 노출 여부
//    singleDatePicker: true                   // 하나의 달력 사용 여부
//});
// 
//$("#txtDate").on('show.daterangepicker', function (ev, picker) {
//    $(".yearselect").css("float", "left");
//    $(".monthselect").css("float", "right");
//    $(".cancelBtn").css("float", "right");
//});

/** 날짜 설정과 관련된 옵션 **/
//:minDate [String, Date], // 최소
//:maxDate [String, Date], // 최대
//:localeData Object,  // 로케일 데이터 포함한 객체
//:dateRange [Object], // v-model prop에서 사용함 startDate와 endDate 를 포함하는 객체여야며 Date로 파싱할 수 있는 문자열이어야 함
//:dateFormat Function, 
// function(classes, date) - 2 개의 매개 변수를받는 특수한 prop 유형 함수:
// "classes" - 컴포넌트의 로직이 정의한 클래스,
// "date" - 현재 처리 된 날짜.
// 렌더링 된 날짜에 적용 할 Vue 클래스 객체를 반환해야 함.


///** input 상태 관련 **/
//:disabled Boolean, // 비활성화 상태
//:readonly Boolean, // readonly 여부

//
///** input class명 관련 **/
//:controlContainerClass [Object, String], // class명
//
///** 달력 모양 관련 **/
//:ranges [Object, Boolean], // 오늘, 어제, 이번달, 올해, 지날달 범위 선택으로 뜨는것. 숨기려면 false로 설정할 수 있음
//:opens String, // :ranges 목록의 위치를 지정하는 것임 "center", "left", "right", "inline"라는데 제대로 동작 안한다
//
//:showWeekNumbers Boolean, // 주수표시
//:showDropdowns Boolean, // 달력 위에 월 및 연도 선택에 대한 드롭 다운을 표시
//:alwaysShowCalendars Boolean, // false로 설정하고 미리 정의 된 범위 중 하나를 선택하면 달력이 숨겨진다고 함
//:singleDatePicker [Boolean, String], // 단일 캘린더만 표시
//
//:timePicker Boolean, // 달력 아래에 시간 (시간 / 분) 선택에 대한 드롭 다운을 표시
//:timePickerIncrement Number, // 분 드롭 다운에서 분 단위
//:timePicker24Hour Boolean, // 24시간제 여부
//:timePickerSeconds Boolean, // 시/분을 제외한 초를 선택
//
//:appendToBody Boolean, // 드롭 다운 요소를 본문 끝에 추가하고 동적으로 크기 / 위치를 지정할지 여부
//:calculatePosition Function, // appendToBody true일 경우 설정함
//
//
///** 달력 이벤트 관련 **/
//:linkedCalendars Boolean, // 달력선택 연동
//:autoApply Boolean, // 선택한 범위를 자동으로 적용
//:closeOnEsc Boolean, // esc키로 드롭다운을 닫을 지 여부


});