function edit_Info() {
	
	const updateForm = $("form[name=editInfoForm]");
	
	//비밀번호 체크
	if(updateForm.find("input[name=userPw]").val() != updateForm.find("input[name=userPwChecker]").val()){
		
		Swal.fire('비밀번호가 일치하지 않습니다.', '', 'error').then((result) => {});
		
		return;
		
	}
	
	 
	//ajax start
	$.ajax({
		url: '/member/editInfo', //요청경로
		type: 'post',
		enctype: 'multipart/form-data',

		//이런것도 가능하네
		data: updateForm.serialize(),

		success: function(result) {
			
			if (result > 0){
				alert('내 정보가 변경 되었습니다.');
				location.href = `/member/myPage`;
			}
			else
				alert('양식이 잘못 되었습니다.');			
			
		},
		
		error: function() { alert('오류가 발생했습니다.'); }
	});
}

