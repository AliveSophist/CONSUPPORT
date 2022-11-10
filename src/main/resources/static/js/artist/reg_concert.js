
function regConcert(){
	
	var formTag = document.querySelector('#regConcert');
	
	//ajax start
	$.ajax({
		url: '/artist/regConcert', //요청경로
		type: 'post',
		enctype: 'multipart/form-data',
		
		//이런것도 가능하네
		data: new FormData( formTag ),
		
		/*============== FormData 쓰려면 필요함 ==============*/
		processData:false,
		contentType:false,
    	cache: false,
    	/*============== FormData 쓰려면 필요함 ==============*/
    	
		success: function(result) {
			
			if(result>0)
				alert('공연이 등록 되었습니다.');
			else
				alert('양식이 잘못되었습니다.');
			
			//href
		},
		error: function() { alert('실패'); }
	});
	//ajax end
}


//////////////////////////////////////////////////////////////////

document.querySelector('#loadHallInfoByAjax').replaceChildren();




function loadHallInfo(){
	
	var hallCode = document.querySelector('#selectHallCode').value;
	alert(hallCode)
	
	//ajax start
	$.ajax({
		url: '/artist/hallInfoAjax', //요청경로
		type: 'post',
		
		data: { 'hallCode':hallCode },
		
		success: function(hallInfo) {
			
			//테이블을 다시 그려주자
			
			const loadHallInfoByAjax = document.querySelector('#loadHallInfoByAjax');
			loadHallInfoByAjax.removeChild(  loadHallInfoByAjax.querySelector('#hallDetail')  );
			loadHallInfoByAjax.removeChild(  loadHallInfoByAjax.querySelector('#selectHallRentDate')  );
			
			//추가할 태그 생성
			let str = '';
			
			//상세정보 태그들.
			str += `	`;
			
			
			//DateList.
			str += `	<select class="form-select" id="selectHallRentDate">`;
			str += `		<option selected>콘서트 시간을 지정해 주세요</option>`;
			
			for(const hallDate of  hallInfo.hallDateList) {
				str += `	<option value="${hallDate.hallDateCode}">${hallDate.hallRentDate}</option>`
			}
			str += `	</select>`;
			
			
			loadHallInfoByAjax.insertAdjacentHTML('beforeend', str);
			
			
			
			
			//href
		},
		error: function() { alert('실패'); }
	});
	//ajax end	
	
	
}


