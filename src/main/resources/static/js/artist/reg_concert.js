
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

function loadHallDateList(){
	
	var hallCode = document.querySelector('#selectHallCode').value;
	
	//ajax start
	$.ajax({
		url: '/artist/regConcert', //요청경로
		type: 'post',
		enctype: 'multipart/form-data',
		
		//이런것도 가능하네
		data: { 'hallCode':hallCode },
		
		/*============== FormData 쓰려면 필요함 ==============*/
		processData:false,
		contentType:false,
    	cache: false,
    	/*============== FormData 쓰려면 필요함 ==============*/
    	
		success: function(hallInfo) {
			
			if(result>0)
				alert('성공.');
			else
				alert('오류.');
			
			//href
		},
		error: function() { alert('실패'); }
	});
	//ajax end	
	
	
}


