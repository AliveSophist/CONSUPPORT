function pwCheck() {
	
	//ajax start
	$.ajax({
		url: '/member/pwCheck', //요청경로
		type: 'post',
		
		//이런것도 가능하네
		data: { "userPw" : document.querySelector('#checkForm').value },

		type: 'POST',
		cache: false,

		success: function(result) {

			if (result){
				alert('비밀번호 인증이 완료 되었습니다.');
				location.href = `/member/editInfoForm`;
			}
			else
				alert('비밀번호가 틀렸습니다.');

		},
		error: function() { alert('오류가 발생했습니다.'); }
	});
	//ajax end

}



