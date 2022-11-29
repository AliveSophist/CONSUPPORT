

function insertAboard(){
	
	var formTag = document.querySelector('#insertAboard');
	
	//ajax start
	$.ajax({
		url: '/board/aboardInsert', //요청경로
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