
var refreshByForce = false;
window.addEventListener('beforeunload', (event) => {
	if(refreshByForce)	// 스크립트에 의한 강제 새로고침은 발동 가능하도록.
		return;
	
	if(salesCode.length < 4)	// 결제 진행중인 상태 아니면 그냥 보내줄게.
		return;
	
	const salesCode = document.querySelector('#salesCode').value;
	$.ajax({
		url: '/cancelWhenPaying', //요청경로
		type: 'post',
		data: {	"salesCode" : salesCode },
		success: function(result)
		{ console.log(result); },
		error: function()
		{ console.log('실패'); }
	});
	
    
    event.preventDefault(); 								// 명세에 따라 preventDefault는 호출해야하며, 기본 동작을 방지합니다.
    event.returnValue = ''; 								// 대표적으로 Chrome에서는 returnValue 설정이 필요합니다.
});

$(document).keypress(function(e) {
    if (e.keyCode == 13)
        e.preventDefault();	//엔터 이벤트 막기
});





function tryPay() {
	
	// 좌석 고르지도 않아놓고 누르지마.
	if(document.querySelector('#salesAmount').value == 0)
		return;	// 너 결제 못감 ㅅㄱ
	
	// 익명유저라면 이메일 입력 '해줘'
	if(document.querySelector('#authorities').value.indexOf('ROLE_ANONYMOUS')>0) {
		
		//이거 Swal 비동기화라서 어쩔 수 없음1
		if(document.querySelector('#anonymousEmail').value.length < 5){
			
			(async () => {
				const { value: email } = await Swal.fire({ heightAuto: false , backdrop: "rgba(0,0,0,0.4)"	// swal 뜰 때 화면 위로 올라감 방지
					, title: '이메일을 입력하세요'
					, html: '<input type="text" style="display:none;">'
					, inputLabel: '티켓 발송을 위한 이메일을 입력해 주세요.'
					, input: 'email'
					, inputPlaceholder: 'Enter your email address'
				})
				if (email) {
					document.querySelector('#anonymousEmail').value = email;
					
					//이거  Swal 비동기화라서 어쩔 수 없음2
					tryPay();
				}
			})()
			
			//이거  Swal 비동기화라서 어쩔 수 없음3
			return;
		}
		
	}
	
	
	
	console.log('hallSeatRCnt : ' + document.querySelector('#hallSeatRCnt').value);
	
	$.ajax({
		url: '/getSalesCode', //요청경로
		type: 'post',
		data: new FormData( document.querySelector('#reserveSeatForm') ),
		
		processData:false,
		contentType:false,
		
		//동기 방식으로 사용하기
		async: false,
		
		success: function(result) {
			// 생성 가능하다면 salesCode. = 'SALES_000001'
			// 생성 불가능하면 null.
			document.querySelector('#salesCode').value = result;
			
			var merchant_uid = (f_append_id + document.querySelector('#salesCode').value + b_append_id)
			document.querySelector('#uidForDebugging').innerHTML = `merchant_uid : ${merchant_uid}`;
		},
		error: function() {
			alert('실패');
		}
	});
	
	if(document.querySelector('#salesCode').value.length < 4){
		Swal.fire({ heightAuto: false , backdrop: "rgba(0,0,0,0.4)"	// swal 뜰 때 화면 위로 올라감 방지
			, icon: 'warning'
			, title: '해당 좌석은 이미 구매되었거나 결제 진행중입니다.'
			//, text: ``
		}).then((result) => {
				refreshByForce = true;
				history.go(0);
		});
		
		return;
	}
	
	
	
	IMP.init('imp60175080');				//아임포트 관리자 콘솔에서 확인한 '가맹점 식별코드' 입력
	IMP.request_pay({
		pg : "kakaopay.TC0ONETIME"			//pg사명 or pg사명.CID (잘못 입력할 경우, 기본 PG사가 띄워짐)
		, pay_method : "card"				//지불 방법
		, merchant_uid : (f_append_id + document.querySelector('#salesCode').value + b_append_id)			//+"iamport_test_ids", 	//가맹점 주문번호 (아임포트를 사용하는 가맹점에서 중복되지 않은 임의의 문자열을 입력)
		, name : "티켓"						//결제창에 노출될 상품명
		, amount : document.querySelector('#salesTotalPrice').value	//금액
		//, amount : 100	//금액
		, buyer_email : "testiamport@naver.com"
		, buyer_name : "테스트"
		, buyer_tel : "01012341234"
	}, function(rsp) { // callback
		if (rsp.success) {
			
			$.ajax({
				url: '/reserveSeat', //요청경로
				type: 'post',
				data: new FormData( document.querySelector('#reserveSeatForm') ),
				
				//얘네 둘은 뭐하는 애들이지?? 얘네 없으면 FormData 안됨
				processData:false,
				contentType:false,
				
				//동기 방식으로 사용하기
				async: false,
				
				success: function(result){
					if(result == 'PAID')
					{
						refreshByForce = true;
						window.close();
						
						// myPage로 가게 할거임..
						//refreshByForce = true;
						//history.go(0);
						
					}
				},
				error: function()
				{ alert('실패'); }
			});
			
		} else {
			alert('결제를 취소하셨습니다.\ncancelWhenPaying_' + document.querySelector('#salesCode').value);
			
			$.ajax({
				url: '/cancelWhenPaying', //요청경로
				type: 'post',
				data: {	"salesCode" : document.querySelector('#salesCode').value },
				
				//동기 방식으로 사용하기
				async: false,
				
				success: function(result) {
					refreshByForce = true;
					history.go(0);
				},
				error: function()
				{ console.log('실패'); }
			});
		}
	});
}










// 환불... 포기... 는 씨발 구현했다.
function cancelPay() {
	//ajax start
	$.ajax({
		url: '/canclePay', //요청경로
		type: 'post',
		data: {	"salesCode" : document.querySelector('#salesCode').value
				, "merchant_uid" : (f_append_id + document.querySelector('#salesCode').value + b_append_id)
				, "cancel_request_amount" : document.querySelector('#salesTotalPrice').value	//금액
//				, "cancel_request_amount" : 100
		},
		success: function(result) {
			alert(result)
			
			refreshByForce = true;
			window.close();
		},
		error: function() {
			alert('ㅠㅠ');
		}
	});
	//ajax end
}


/*
// 이걸로하면 CORS 오류... 나더라...
// https://evan-moon.github.io/2020/05/21/about-cors/
// 솔까 뭔지 모름 ㅈㅈ

function cancelPay() {
	jQuery.ajax({
		// 예: http://www.myservice.com/payments/cancel
		url : "https://api.iamport.kr/payments/cancel",
		type : "POST",
		contentType : "application/json",
		data : JSON.stringify({
			"merchant_uid" : 'mer_SALES_000002'			//document.querySelector('#salesCode').value
//			, "amount" : document.querySelector('#salesTotalPrice').value	//금액
			, "cancel_request_amount" : 100
			, "reason": '테스트 결제 환불'
			//"merchant_uid": "{결제건의 주문번호}", // 예: ORD20180131-0000011
			//"cancel_request_amount": 2000, // 환불금액
		}),
		dataType : "json"
	});
}
*/





function loadCoupon() {
	const couponCode = document.querySelector('#couponCode').value;
	
	//ajax start
	$.ajax({
		url: '/selectCoupon', //요청경로
		type: 'post',
		data: {	"couponCode" : couponCode },
		async: false,
		success: function(coupon) {
			if(coupon.couponCode == null){
				Swal.fire({ heightAuto: false , backdrop: "rgba(0,0,0,0.4)"	// swal 뜰 때 화면 위로 올라감 방지
					, icon: 'error'
					, title: `유효하지 않은 쿠폰입니다`
					//, text: ``
				}).then((result) => {
					document.querySelector('#isExistCoupon').innerHTML = '활성화된 쿠폰이 없습니다.';
					
					document.querySelector('#couponValue').value = 0;
					updateView();
				});
				return;
			}
			else{
				Swal.fire({ heightAuto: false , backdrop: "rgba(0,0,0,0.4)"	// swal 뜰 때 화면 위로 올라감 방지
					, icon: 'success'
					, title: `사용 가능한 쿠폰입니다`
					, text: `할인율 : ${coupon.couponValue}%`
				}).then((result) => {
					document.querySelector('#isExistCoupon').innerHTML = '　';
					
					document.querySelector('#couponValue').value = coupon.couponValue;
					updateView();
				});
			}
		},
		error: function() {
			alert('ㅠㅠ');
		}
	});
	//ajax end
	
	
}
