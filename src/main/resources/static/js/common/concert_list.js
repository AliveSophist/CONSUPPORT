// 콘서트 상세보기 모달 띄우기
function concertDetailModal(concertCode){
	showModal(document.querySelector('#concertDetailModal'));
	
	//ajax start
	$.ajax({
		url: '/selectConcertDetail', //요청경로
		type: 'post',
		data: { 'concertCode': concertCode }, //필요한 데이터
		success: function(concert) {
			
		// 테이블 다시 그려야 한데....
		const concertDetailAjax = document.querySelector('#concertDetailAjax')
		concertDetailAjax.removeChild(concertDetailAjax.querySelector('#cardForConcertDetail'))
		
		let str = '';
		
		str +=	`<div id="cardForConcertDetail" class="card mb-3" style="max-width: 100%; background-image: linear-gradient(125deg,rgba(255,255,255,.1),rgba(255,255,255,.1) 70%);">`;
		
		str +=		`<div class="row g-0">`	;
		str +=			`<div class="col-md-4" style="margin-top:15px">`;
		
		for(const concertImg of concert.concertImgList){
			if(concertImg.concertImgIsMain == 'Y'){
		str +=			`<img src="/img/concert/${concertImg.concertImgNameAttached}" class="img-fluid rounded-start" style="width: 100%; height: 400px; object-fit: contain;">`;
			}
		}
			
		str +=			`</div>`;		
		str +=			`<div class="col-md-8">`;		
		str +=				`<div class="card-body">`;			
		str +=				`<small>연령제한 | (${concert.concertRated})</small>`
		str +=					`<h2 class="card-title">${concert.concertName}</h2>`;
		str +=					`<hr>`;
		
		
		
		
		str +=				`<table class="table text-center"style="margin-top:-30px">`
		str +=						`<tr>`
		str +=							`<th scope="col" class="col-4">티켓가격</th>`
		str +=							`<th scope="col" class="col-4">공연날짜</th>`
		str +=							`<th scope="col" class="col-4">티켓팅날짜</th>`
		str +=						`</tr>`
		
		
		str +=						`<tr>`
		str +=							`<td>R석 : ${concert.concertPrice.concertPriceR}</td>`
		str +=						`</tr>`
		str +=						`<tr>`
		str +=							`<td>S석 : ${concert.concertPrice.concertPriceR}</td>`
		str +=							`<td>${concert.concertDate}</td>`
		str +=							`<td>${concert.concertTicketingDate}</td>`
		str +=						`</tr>`
		str +=						`<tr>`
		str +=							`<td>A석 : ${concert.concertPrice.concertPriceA}</td>`
		str +=						`</tr>`
		
		
		
		str +=				`</table>`
		
			
		str +=			`<img class="rounded-circle me-3"
							  src="/img/artist/${concert.artist.artistImg.artistImgNameAttached}"
							  style="width: 80px; height: 80px; margin-top: 10px; cursor: pointer;"
							  onclick="artistDetailModal('${concert.artist.userId}')"
							  >`
		
			
		str +=				`</div>`;
		str +=			`</div>`;
		str +=		`</div>`;
		str +=		`<div style="margin: auto; margin-top: 30px">`
		str +=			`<button style="width: 1000px;" type="button" class="btn btn-dark" onclick="openReserveSeatForm('${concert.concertCode}', '${concert.hallCode}')">예매하러 가기</button>`
		str +=		`</div>`
		
		str +=		`<hr>`;
		
//=======================================================================================================================================================================================


		for (const concertImg of concert.concertImgList) {
			if (concertImg.concertImgIsMain == 'N') {
		str += 		`<div class="row g-0">`;
		str += 			`<div class="col-md-4 text-center" style="margin-top:15px; margin: auto; width: 1000px; height: 100%;" >`;
		str += 				`<img src="/img/concert/${concertImg.concertImgNameAttached}" class="img-fluid rounded-start" 
									style="width: 80%; height: 80%; object-fit: contain;">`;
		str += 			`</div>`;
		str += 		`</div>`;
			}
		}
		str += `<div class="row g-0" style="height:50px;"></div>`;


//=======================================================================================================================================================================================

				
		
		str +=`</div>`;
		

		concertDetailAjax.insertAdjacentHTML('beforeend', str)

		},
		
		error: function() {
			alert('실패');
		}
	});
	//ajax end
} 


//=======================================================================================================================================================================================

// 아티스트 상세보기 모달 띄우기
function artistDetailModal(userId){
	showModal(document.querySelector('#artistDetailModal'));
	
	
	//ajax start
	$.ajax({
		url: '/selectArtistDetail', //요청경로
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
		str +=					`<p class="card-text">`;
		str +=						`<small class="text-muted">Last updated 3 mins ago</small>`;
		str +=					`</p>`;
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


function openReserveSeatForm(concertCode, hallCode){
	const option='top=10,left=200,width=900,height=900,resizable=0,';
	const url=`/reserveSeatForm?concertCode=${concertCode}&hallCode=${hallCode}`;
	window.open(url, 'popup', option);
}



