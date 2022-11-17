
//문서가 준비되면 제일 먼저 실행
$(document).ready(function() {
	$("#iamportPayment").click(function() {
		payment(); //버튼 클릭하면 호출 
	});
})

//버튼 클릭하면 실행
function payment() {
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
			document.querySelector('#salesCode').value = result;
		},
		error: function() {
			alert('실패');
		}
	});
	
	
	
	IMP.init('imp60175080');				//아임포트 관리자 콘솔에서 확인한 '가맹점 식별코드' 입력
	IMP.request_pay({						// param
		pg: "kakaopay.TC0ONETIME", 			//pg사명 or pg사명.CID (잘못 입력할 경우, 기본 PG사가 띄워짐)
		pay_method: "card", 				//지불 방법
		merchant_uid: salesCode+"iamport_test_ids", 	//가맹점 주문번호 (아임포트를 사용하는 가맹점에서 중복되지 않은 임의의 문자열을 입력)
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
				
				success: function(result) {
					alert(result);
				},
				error: function() {
					alert('실패');
				}
			});
			
		} else {
			alert("실패 : 코드(" + rsp.error_code + ") / 메세지(" + rsp.error_msg + ")");
		}
	});
}
