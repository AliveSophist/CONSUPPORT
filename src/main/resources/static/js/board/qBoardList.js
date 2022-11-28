/**
 * 
 */
 
 function goDetail(qBoard, name){
	
	//작성자이거나, 관리자일 경우
	if(qBoard.userId == name || document.querySelector('#authorities').value == '[ROLE_ADMIN]'){
		location.href = `/board/detailQboard?num=${qBoard.qboardNum}`;
	}
	else{
		alert('해당 페이지에 접근 권한이 없습니다.');
	}
}