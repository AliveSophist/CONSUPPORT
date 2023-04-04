// admin 전용 콘서트 상세보기 모달 띄우기
function concertDetailModal(concertCode){
	
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
		
			
		str +=				`</div>`;
		str +=			`</div>`;
		str +=		`</div>`;
		
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

function cancelPay(salesCode, cancel_request_amount) {

	const resultConfirm = confirm('정말 환불 하시겠습니까?')
	
	if(resultConfirm){
	
	const merchant_uid = f_append_id + salesCode + b_append_id;
	
	//ajax start
	$.ajax({
		url: '/canclePay', //요청경로
		type: 'post',
		data: {	"salesCode" : salesCode
				, "merchant_uid" : merchant_uid
				, "cancel_request_amount" : cancel_request_amount
				//, "cancel_request_amount" : 100
		},
		success: function(result) {
			alert('환불이 완료 되었습니다.')
			location.href = '/member/myPage';
		},
		error: function() {
			alert('ㅠㅠ');
		}
	});
	//ajax end
	}
	
	else{
		alert('취소 되었습니다.')
	}
}







































