
function submitForm(){
	
	Swal.fire({
		title: '작성을 완료하시겠습니까',
		showCancelButton: true,
		cancelButtonText: 'Back',
		confirmButtonText: 'Done',
	}).then((result) => {	// then start
		if (result.isConfirmed){
			
			
			$('#regConcert').find('input[type!="hidden"]').each(function(){
				
			    if(!$(this).val()) {
			    	
			    	Swal.fire({
						icon: 'error'
						, title: `비어있는 항목이 있습니다`
					});
			    	
			    	return;
			    }
			    else {
			    	
			    	Swal.fire({
						icon: 'success'
						, title: `등록 완료!`
						
						, timer: 1500
						, didOpen: () => { timerInterval = setInterval(() => { /* DO NOTHING :) */ }, 100); }
						, willClose: () => { clearInterval(timerInterval); }
						
					}).then(() => {
						
						regConcert();

					});
	
			    }
			    
			});
			
	
		}
	});		// then end
}

function regConcert() {

	var formTag = document.querySelector('#regConcert');

	//ajax start
	$.ajax({
		url: '/artist/regConcert', //요청경로
		type: 'post',
		enctype: 'multipart/form-data',

		//이런것도 가능하네
		data: new FormData(formTag),

		/*============== FormData 쓰려면 필요함 ==============*/
		processData: false,
		contentType: false,
		cache: false,
		/*============== FormData 쓰려면 필요함 ==============*/

		success: function() {
			
//			if (result > 0)
//				alert('공연이 등록 되었습니다.');
//			else
//				alert('양식이 잘못되었습니다.');

			//href
			location.href = '/';
			
		},
		error: function() { alert('실패'); }
	});
	//ajax end
}



//////////////////////////////////////////////////////////////////





function loadHallInfo() {
	var hallCode = document.querySelector('#selectHallCode').value;
	

	//ajax start
	$.ajax({
		url: '/artist/hallInfoAjax', //요청경로
		type: 'post',

		data: { 'hallCode': hallCode },

		success: function(hallInfo) {

			//테이블을 다시 그려주자
			const loadHallInfoByAjax = document.querySelector('#loadHallInfoByAjax');
			loadHallInfoByAjax.replaceChildren();


			//추가할 태그 생성
			let str = '';

			//상세정보 태그들.
			str += `<div id="hallDetail" class="card mb-3" style="max-width: 100%; ">`
			str += `	<div class="row g-0 text-center">`;
			str += `		<div class="col-5" style="margin-top:15px">`;
			for (const hallImg of hallInfo.hallImgList) {
				if (hallImg.hallImgIsMain == "Y") {
					str += `		<img src="/img/hall/${hallImg.hallImgNameAttached}"
									 style="width: 100%; height: 400px; object-fit: contain;"
									 class="img-fluid rounded-start">			`;
				}
			}
			str += `		</div>`;
		str += `			<div class="col-md-6" style="margin-top:20px">`;
			str += `			<h2 class="card-title text-start">${hallInfo.hallName}</h2>		`;
			str += `				<hr>`;
			str +=				`<table class="table text-center"style="margin-top:-30px">`
			str += `				<tr>`;
			str += `					<td>대여료</td>`;
			str += `					<td colspan='2'>좌석 정보</td>`;
		str += `					</tr>`;
			str += `				<tr>`;
			str += `					<td>${hallInfo.hallRentPrice} ￦</td>		`;
			str += `					<td>R석</td>		`;
			str += `					<td>${hallInfo.hallSeat.hallSeatRCnt}석</td>		`;
		str += `					</tr>`;
			str += `				<tr>`;
			str += `					<td>소유주</td>`;
			str += `					<td>S석</td>		`;
			str += `					<td>${hallInfo.hallSeat.hallSeatSCnt}석</td>		`;
		str += `					</tr>`;
			str += `				<tr>`;
			str += `					<td>${hallInfo.userId} 님</td>		`;
			str += `					<td>A석</td>		`;
			str += `					<td>${hallInfo.hallSeat.hallSeatACnt}석</td>		`;
		str += `					</tr>`;
		str += `				</table>					`
		str += `				<h4 class="card-title text-start">Hall 상세</h4>`;
			str += `				<hr>					`
			str += `			<div class="text-start">${hallInfo.hallDetail}</div>	`;
		str += `			</div>`;
			str += `	</div>`;
			str +=					`<hr>`;
			str += `	<div class="row mb-3">	`;
			str += `		<div class="col-12">`;
			str += `				<div class="col-12 text-center">`;
			for (const hallImg of hallInfo.hallImgList) {
				if (hallImg.hallImgIsMain == "N") {
					str += `		<img src="/img/hall/${hallImg.hallImgNameAttached}"  
									class="img-fluid rounded-start"
									style="width: 80%; height: 80%; object-fit: contain;">			`;
				}
			}
			str += `				</div>`;
			str += `			<div class="col-12"></div>`;
			str += `		</div>`;
			str += `	</div>`;
			str += `</div>`;
			str += `	<hr>`


			//DateList.

			str += `<div id="selectHallRentDate">`;
			str += `	<select id="selectHallDateCode" name="hallDateCode" class="form-select" onchange="changeBtnHallInfoValue()">`;
			str += `		<option value="1" selected>콘서트 시간을 지정해 주세요</option>`;
			for (const hallDate of hallInfo.hallDateList) {
				str += `	<option value="${hallDate.hallDateCode}">${hallDate.hallRentDate}</option>`
			}
			str += `	</select>`;

			str += `</div>`;



			loadHallInfoByAjax.insertAdjacentHTML('beforeend', str);




			//href
		},
		error: function() { alert('실패'); }
	});
	//ajax end	

}

function changeBtnHallInfoValue(){
	
	//alert( $('#selectHallDateCode option:checked').text().substring(0, $('#selectHallDateCode option:checked').text().indexOf(' ')) );
	//console.log( $('#selectHallDateCode option:checked').text().substring(0, $('#selectHallDateCode option:checked').text().indexOf(' ')) );
	
	
	
	str = $('#selectHallCode option:checked').text() + ' (' + $('#selectHallDateCode option:checked').text() + ')'

	document.querySelector('#btnHallInfo').value = str;
	
	
	
	document.querySelector('#btnHallInfo').classList.remove('btn-primary');
	document.querySelector('#btnHallInfo').classList.add('btn-outline-light');
	
	
	$('.datepicker').datepicker({
		format: 'yyyy-mm-dd',
		todayHighlight: true,
		startDate: '0d',
		endDate: $('#selectHallDateCode option:checked').text().substring(0, $('#selectHallDateCode option:checked').text().indexOf(' ')),
		autoclose: true
	});
}







	
//	$('#datepicker').daterangepicker({
//		"singleDatePicker": true,
//		//"endDate": $('#selectHallDateCode option:checked').text().substring(0, $('#selectHallDateCode option:checked').text().indexOf(' ')),
//		"opens": "center",
//		locale: {
//			format: 'YYYY-MM-DD'
//		}
//	}, function(start, end, label) {
//		console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
//	});

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