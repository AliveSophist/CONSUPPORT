
// radio클릭시 맴버or아티스트로 변경하는 기능
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
	
//--------------------------------------------------------------------------------------------------------------------------------------------
	
// 아티스트 상세보기 모달 띄우기
function artistDetailModal(userId){
	
	//ajax start
	$.ajax({
		url: '/admin/selectArtistDetail', //요청경로
		type: 'post',
		data: { 'userId': userId }, //필요한 데이터
		success: function(artist) {
			
		// 테이블 다시 그려야 한데....
		const artistDetailAjax = document.querySelector('#artistDetailAjax')
		artistDetailAjax.removeChild(artistDetailAjax.querySelector('#cardForArtistDetail'))
		
		let str = '';
		
		str +=	`<div id="cardForArtistDetail" class="card mb-3" style="max-width: 100%; ">`;
		str +=		`<div class="row g-0">`	;
		str +=			`<div class="col-md-4" style="margin-top:15px">`;
		
			for(const artistImg of artist.artistImgList){
				if(artistImg.artistImgIsMain == 'Y'){
		str +=			`<img src="/img/artist/${artistImg.artistImgNameAttached}" class="img-fluid rounded-start" style="width: 100%; height: 400px; object-fit: contain;">`;
				}
			}
			
		str +=			`</div>`;		
		str +=			`<div class="col-md-8">`;		
		str +=				`<div class="card-body">`;			
		str +=					`<h3 class="card-title">${artist.artistName}</h3>`;
		str +=					`<hr>`;
		str +=					`<p class="card-text">${artist.artistDetail}</p>`;
		str +=				`</div>`;
		str +=			`</div>`;
		str +=		`</div>`;
		str +=		`<hr>`;
		
		str +=`<ul class="gallery zoom">`
		
			for(const artistImg of artist.artistImgList){
				if(artistImg.artistImgIsMain == 'N'){
					
		str +=	`<li>`
		str +=		`<a href="#">`
		str +=			`<img src="/img/artist/${artistImg.artistImgNameAttached}">`
		str +=		`</a>`
		str +=	`</li>`
		
				}
			}
			
		str +=`</ul>`
		
		str +=`</div>`;
		

		artistDetailAjax.insertAdjacentHTML('beforeend', str)

		},
		
		error: function() {
			alert('실패');
		}
	});
	//ajax end
} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	






























































