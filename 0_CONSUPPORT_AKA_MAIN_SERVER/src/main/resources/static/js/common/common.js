 
 // 테스트시 SALES 코드 중첩이 발생하기 쉬우므로 덧붙이는 문자열 필요..
let f_append_id = 'USKJ_';
let b_append_id = '';



// 콘서트 상세보기 모달 띄우기
function concertDetailModalForReserve(concertCode){
	showModal(document.querySelector('#concertDetailModalForReserve'));
	
	//ajax start
	$.ajax({
		url: '/selectConcertDetail', //요청경로
		type: 'post',
		data: { 'concertCode': concertCode }, //필요한 데이터
		success: function(concert) {
			
			// 테이블 다시 그려야 한데....
			const concertDetailForReserveAjax = document.querySelector('#concertDetailForReserveAjax')
			concertDetailForReserveAjax.removeChild(concertDetailForReserveAjax.querySelector('#cardForConcertDetailForReserve'))
			
			let str = '';
			
			str +=	`<div id="cardForConcertDetailForReserve" class="card mb-3" style="max-width: 100%; background-image: linear-gradient(125deg,rgba(255,255,255,.1),rgba(255,255,255,.1) 70%);">`;
			
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
								  onclick="artistDetailModalForReserve('${concert.artist.userId}')"
								  >`
			
				
			str +=				`</div>`;
			str +=			`</div>`;
			str +=		`</div>`;
			
			//예매버튼 관련
			{
				//티켓팅 시작일이 되지 않았다면 예매 불가.
				const today = new Date();
				let todayY = today.getFullYear(); 	// 년도
				let todayM = today.getMonth() + 1;	// 월
				let todayD = today.getDate();  		// 날짜
				
				const ticketDate = concert.concertTicketingDate;
				var ticketY = parseInt(ticketDate.substring(0, 4));
				var ticketM = parseInt(ticketDate.substring(5, 7));
				var ticketD = parseInt(ticketDate.substring(8, 10));
	
				if(ticketY>=todayY && ticketM>=todayM && ticketD>todayD){
					str +=	`	<div style="margin: auto; margin-top: 30px">
									<button style="width: 1000px;" type="button" class="btn btn-dark" disabled>아직 티켓 예매일이 아닙니다.</button>
								</div>`
				}
				else{
					//sub ajax start  : 나이 정보 가져오기.
					let gotAge = 0;
					$.ajax({
						url: '/getAge', //요청경로
						type: 'post',
						data: {}, //필요한 데이터
						async: false,
						success: function(result) {
							gotAge = result
						},
						error: function() {
							alert('실패');
						}
					});
					
					// 어쩌구 저쩌구 해서 구해온 나이로 예매버튼 바꿔버리기
					let isUserAgeAvailable = false;
					if(concert.concertRated=='All') isUserAgeAvailable=true;
					if(concert.concertRated=='15+')
						if(gotAge >= 15) isUserAgeAvailable=true;
					if(concert.concertRated=='19+')
						if(gotAge >= 19) isUserAgeAvailable=true;
					
					if( isUserAgeAvailable )
						str +=	`	<div style="margin: auto; margin-top: 30px">
										<button style="width: 1000px;" type="button" class="btn btn-dark" onclick="openReserveSeatForm('${concert.concertCode}', '${concert.hallCode}')">예매하러 가기</button>
									</div>`
					else if( gotAge == 0 )
						str +=	`	<div style="margin: auto; margin-top: 30px">
										<button style="width: 1000px;" type="button" class="btn btn-dark" disabled>비회원 계정은 연령제한 공연 예매는 불가능합니다</button>
									</div>`
					else
						str +=	`	<div style="margin: auto; margin-top: 30px">
										<button style="width: 1000px;" type="button" class="btn btn-dark" disabled>연령제한 공연입니다</button>
									</div>`
				}
			}
			
			
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
			
	
			concertDetailForReserveAjax.insertAdjacentHTML('beforeend', str)
	
			},
		
		error: function() {
			alert('실패');
		}
	});
	//ajax end
} 


//=======================================================================================================================================================================================

// 아티스트 상세보기 모달 띄우기
function artistDetailModalForReserve(userId){
	showModal(document.querySelector('#artistDetailModalForReserve'));
	
	//ajax start
	$.ajax({
		url: '/selectArtistDetail', //요청경로
		type: 'post',
		data: { 'userId': userId }, //필요한 데이터
		success: function(artist) {
				
			// 테이블 다시 그려야 한데....
			const artistDetailForReserveAjax = document.querySelector('#artistDetailForReserveAjax')
			artistDetailForReserveAjax.removeChild(artistDetailForReserveAjax.querySelector('#cardForArtistDetailForReserve'))
			
			let str = '';
			
			str +=	`<div id="cardForArtistDetailForReserve" class="card mb-3" style="max-width: 100%; background-color: rgba(0,0,0,0.35);">`;
			str +=		`<div class="row g-0" style="padding: 0px 20px;">`	;
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
			str +=		`<a href="#!">`
			str +=			`<img src="/img/artist/${artistImg.artistImgNameAttached}">`
			str +=		`</a>`
			str +=	`</li>`
			
					}
				}
				
			str +=`</ul>`
			
			str +=`</div>`;
			
	
			artistDetailForReserveAjax.insertAdjacentHTML('beforeend', str)

		},
		
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}


function openReserveSeatForm(concertCode, hallCode){
	const option='top=10,left=200';
	const url=`/reserveSeatForm?concertCode=${concertCode}&hallCode=${hallCode}`;
	window.open(url, 'popup', option);
}
