
function updateUserRole(formTagId, str){
	
	const result = confirm(`권한을 정말로 ${str}하시겠습니까?`);
	
	// 그때그때 다른 폼태그 들고가
	var formTag = document.getElementById(formTagId);
	
	if(result){
		//ajax start
		$.ajax({
			url: '/admin/updateUserRoleAboutOwner', //요청경로
			type: 'post',

			data: new FormData(formTag),
			processData: false,
			contentType: false,
			cache: false,
			
			success: function(isSuccess) {
				
				if(isSuccess){
					alert('권한 변경 완료');
				}
				else{
					alert('존재하지 않는 아이디거나\n변경할 수 없는 권한을 소지중입니다.');
				}
				
				
				
				location.href = '/admin/ownerManager';
				
			},
			error: function() {
				alert('실패');
			}
		});
		//ajax end
	}
}

