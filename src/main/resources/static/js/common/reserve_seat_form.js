var refreshByForce = false;

window.addEventListener('beforeunload', (event) => {
	if(refreshByForce)	// 강제 새로고침은 발생 가능
		return;
	
	salesCode = document.querySelector('#salesCode').value;
	if(salesCode.length > 4)
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



//문서가 준비되면 제일 먼저 실행
$(document).ready(function() {
	$("#iamportPayment").click(function() {
		payment(); //버튼 클릭하면 호출 
	});
})

//버튼 클릭하면 실행
function payment() {
	//좌석 고르지도 않아놓고 누르지마.
	if(document.querySelector('#salesAmount').value == 0)
		return;
	
	
	
	$.ajax({
		url: '/getBuyCode', //요청경로
		type: 'post',
		data: new FormData( document.querySelector('#reserveSeatForm') ),
		
		//얘네 둘은 뭐하는 애들이지?? 얘네 없으면 FormData 안됨
		processData:false,
		contentType:false,
		
		//동기 방식으로 사용하기
		async: false,
		
		success: function(result) {
			// 생성 가능하다면 salesCode.
			// 생성 불가능하면 null.
			document.querySelector('#salesCode').value = result;
		},
		error: function() {
			alert('실패');
		}
	});
	
	if(document.querySelector('#salesCode').value.length < 4){
		alert('해당 좌석은 이미 선점되었습니다.');
		
		refreshByForce = true;
		history.go(0);
	}
	
	
	alert();
	const mer_uid = 'mer_' + document.querySelector('#salesCode').value;
	
	IMP.init('imp60175080');				//아임포트 관리자 콘솔에서 확인한 '가맹점 식별코드' 입력
	IMP.request_pay({						// param
		pg: "kakaopay.TC0ONETIME", 			//pg사명 or pg사명.CID (잘못 입력할 경우, 기본 PG사가 띄워짐)
		pay_method: "card", 				//지불 방법
		merchant_uid: 'ssssssssssss', //+"iamport_test_ids", 	//가맹점 주문번호 (아임포트를 사용하는 가맹점에서 중복되지 않은 임의의 문자열을 입력)
		name: "티켓", 						//결제창에 노출될 상품명
		//amount: document.querySelector('#salesTotalPrice').value,	//금액
		amount: 100,	//금액
		buyer_email: "testiamport@naver.com",
		buyer_name: "테스트",
		buyer_tel: "01012341234"
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
				
				success: function(result)
				{ alert(result); },
				error: function()
				{ alert('실패'); }
			});
			
		} else {
			
			$.ajax({
				url: '/cancelWhenPaying', //요청경로
				type: 'post',
				data: {	"salesCode" : salesCode },
				success: function(result)
				{ console.log(result); },
				error: function()
				{ console.log('실패'); }
			});
			
		}
	});
}

































// 환불... 포기...
function cancelPay() {
	//ajax start
	$.ajax({
		url: '/canclePay', //요청경로
		type: 'post',
		data: {	"imp_uid" : document.querySelector('#salesCode').value,
//				"amount" : document.querySelector('#salesTotalPrice').value,	//금액
				"amount" : 100 },
		success: function(result) {
			alert(result)
		},
		error: function() {
			alert('ㅠㅠ');
		}
	});
	//ajax end
}

