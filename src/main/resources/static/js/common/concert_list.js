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
		
			for(const artistImg of concert.artist.artistImgList){
				if(artistImg.artistImgIsMain == 'Y'){
		str +=			`<img class="rounded-circle me-3"
							  src="/img/artist/${artistImg.artistImgNameAttached}"
							  style="width: 80px; height: 80px; margin-top: 10px">`
				}
			}
		str +=				`</div>`;
		str +=			`</div>`;
		str +=		`</div>`;
		str +=		`<div style="margin: auto; margin-top: 30px">`
		str +=			`<button style="width: 1000px;" type="button" class="btn btn-dark">예매하러 가기</button>`
		str +=		`</div>`
		
		str +=		`<hr>`;
		
//=======================================================================================================================================================================================

		str += `<div class="row g-0">`;
		str += `<div class="col-md-4" style="margin-top:15px; margin: auto; width: 1000px; height: 1000px;" >`;

		for (const concertImg of concert.concertImgList) {
			if (concertImg.concertImgIsMain == 'N') {
				str += `<img src="/img/concert/${concertImg.concertImgNameAttached}" class="img-fluid rounded-start" 
						 	 style="width: 100%; height: 100%; object-fit: contain; ">`;
			}
		}

		str += `</div>`;
		str += `</div>`;

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