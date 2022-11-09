
function regConcert() {
	
	const formTag = document.querySelector('#regConcert')
	
	//ajax start
	$.ajax({
		url: '/artist/regConcert', //요청경로
		type: 'post',

		data: $("form[name=regConcert]").serialize(),

		success: function(result) {

			location.href = result.Referer;
		},
		error: function() {
			alert('등록되지 않았습니다.');
		}
	});
	//ajax end
}




