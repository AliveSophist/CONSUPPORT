$("#qboardTitle").hide();

//셀렉트 박스 작동 문법
$('#selbox').change(function(){
   $("#selbox option:selected").each(function () {
		
		if($(this).val()== '1'){ //직접입력일 경우
			$("#qboardTitle").show();
			 $("#qboardTitle").val(``);                        //값 초기화
			 $("#qboardTitle").attr("readonly",false); //활성화
		}else{ //직접입력이 아닐경우
			 $("#qboardTitle").val($(this).text());      //선택값 입력
			 $("#qboardTitle").attr("readonly",true); //비활성화
		}
   });
});


// 문의 등록시 검사
function regQboard() {
	const regQboardForm = $("form[name=regQboardForm]");
		
	const title = $('#qboardTitle').val();
	const content = $('#qboardContent').val();
	
	if(title == null || title == ''){
		alert('제목은 필수입력입니다.');
		return ; 
	}	
	
	if(content == null || content == ''){
		$('#errorSpan').remove();
		const str = '<span style="color:red; font-size:0.8rem;" id="errorSpan">문의 내용은 필수입력합니다.</span>';
		$('#qboardContent').after(str);
		return ;
	}
	
	$.ajax({
		url : '/board/regQboard', //요청경로
		type : 'post',
		
		data : regQboardForm.serialize(),
		success : function(result) {
			
			if(result > 0) {
				
				alert('문의가 등록 되었습니다.');
				location.href = '/board/qboardList';
			}
	
		},
		error : function() {
			alert('등록이 실패했습니다.')
		}
		
	})
	
}



