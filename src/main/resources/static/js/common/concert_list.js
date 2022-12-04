
//=======================================================================================================================================================================================

//스페셜 콘서트 클릭이벤트 할당
const specialCardList = document.querySelectorAll('.specialCard');
for(specialCard of specialCardList){
	specialCard.addEventListener("click", function() {
		
		// 여기서 this를 쓸 경우. addEventListener function을 실행시킨 '주체'(해당 태그) 그 자체가 바로 반환!
		if(this.classList.contains('active')){
			
			concertDetailModalForReserve(this.dataset.concertCode);
			//alert(this.dataset.concertCode)
			
		}
		
	})
	
}

const lenCards = specialCardList.length;
const mainArrowL = document.querySelector('.mainArrowL');
const mainArrowR = document.querySelector('.mainArrowR');

// 현재 몇번째 카드 바라보고 있니?
function getNowCard(){
	let num = 1;
	for(specialCard of specialCardList){
		if( specialCard.classList.contains('active') )
			break;
		
		num++;
	}
	return num;
}
function specialCard_MoveL(){
	const numNowCard = getNowCard();
	const index = numNowCard-1;
	
	//오른화살표 감춰져있었다면 보여줘
	mainArrowR.style.visibility ='visible';
	
	// 왼쪽 끝 카드 아니면 클릭이벤트 발동시켜 = 한칸 이동시켜
	if(numNowCard != 1)
		specialCardList[index-1].click();
		
	//왼쪽 끝 카드되면 화살표 감춰
	if(index-1 == 0)
		mainArrowL.style.visibility ='hidden';
}
function specialCard_MoveR(){
	const numNowCard = getNowCard();
	const index = numNowCard-1;
	
	//왼화살표 감춰져있었다면 보여줘
	mainArrowL.style.visibility ='visible';
	
	// 오른쪽 끝 카드 아니면 클릭이벤트 발동시켜 = 한칸 이동시켜
	if(numNowCard != lenCards)
		specialCardList[index+1].click();
		
	//오른쪽 끝 카드되면 화살표 감춰
	if(index == (lenCards-2))
		mainArrowR.style.visibility ='hidden';
}












let pageNum = 1;
let isPageEnd = false;

infinityScroll();
function infinityScroll(){
	
	if(isPageEnd)
		return;
	
	//ajax start
	$.ajax({
		url: '/loadPage', //요청경로
		type: 'post',
		data: {"pageNum" : pageNum}, //필요한 데이터
		async: false,
		success: function(concertList) {
			
			//alert(concertList.length)
			
			if(concertList.length <= 0){
				isPageEnd = true;
				return;
			}
			
			// boxSon 추가
			const boxMom = document.querySelector('.boxMom')
			
			let str = '';
			
			str +=`<div class="boxSon row gx-5" style="padding-left: 2%;">`
			
			for(const concert of concertList){
				str += `
					<div class="col-lg-3 mb-3" style="padding: 0 0;">
						<div class="card h-100 shadow border-0" style="width: 350px; height: 450px;"
							 onclick="concertDetailModalForReserve('${concert.concertCode}')">
						
							<img class="card-img-top"
								src="/img/concert/${concert.concertImgList[0].concertImgNameAttached}"
								style="width: 350px; height: 430px; object-fit: cover;">
								
							<div class="card-body" style="padding-top: 20px; padding-bottom: 20px;">
								<!-- <div class="badge bg-primary bg-gradient rounded-pill mt-1">NEW</div> -->
								
								<div>
									<small>${concert.artist.artistName} | ${concert.concertRated}</small>
								</div>
								
								<h5 class="card-title">
									<a class="text-decoration-none link-dark stretched-link" href="#!">
										${concert.concertName}
									</a>
								</h5>
								<div class="text-center">
									<small>공연시작 | ${concert.concertDate}</small>
								</div>
								 
							</div>
							
						</div>
					</div>
				`
			}
			str += `</div>`
			
			
			boxMom.insertAdjacentHTML('beforeend', str)
			
			concertList = null;
			pageNum++;
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}

// 무한스크롤 구현 (개빡셌다.... 아직 이해 못 함)
$(document).scroll(function() {
	var maxHeight = $(document).height();
	var currentScroll = $(window).scrollTop() + $(window).height();

	if (maxHeight <= currentScroll + 100) {
		infinityScroll();
	}
})

