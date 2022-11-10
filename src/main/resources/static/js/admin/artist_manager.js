
// A버튼 클릭시 ARTIST로, M버튼 클릭시 MEMBER로
function userRoleSet(userId, userRole){
	
	const result = confirm('상태를 변경 하시겠습니까?');
	
	if(result){
		//ajax start
		$.ajax({
			url: '/admin/updateUserRoleToArtist', //요청경로
			type: 'post',
			data: { 'userId': userId, 'userRole': userRole }, //필요한 데이터
			success: function(result) {
				
				alert('상태를 변경했습니다.');
				
			},
			error: function() {
				alert('실패');
			}
		});
		//ajax end
	}
	else {
		event.preventDefault();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}






























































