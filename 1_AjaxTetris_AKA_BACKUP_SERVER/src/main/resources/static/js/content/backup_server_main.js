
new bootstrap.Modal( document.querySelector('#staticBackdrop') ).show();

// 백업서버에서 받는 임시 UUID를 keyQueue로서 대기열에 입장한다.
let keyQueue = document.querySelector('#userId').value;

let tryingEnter = true;


function enterTheWaitingQueue(){
	//ajax start
	$.ajax({
		url: 'http://localhost:8088/enterTheWaitingQueue', //요청경로
		type: 'post',
		async: false,
		data: {"keyQueue":keyQueue},
		success: function() {
			//alert('큐 접속 성공.')
		},
		error: function() {
			alert('ㅠㅠ');
		}
	});
	//ajax end
}


function isAvailableEnter(){
	//ajax start
	$.ajax({
		url: 'http://localhost:8088/isAvailableEnter', //요청경로
		type: 'post',
		async: false,
		data: {"keyQueue":keyQueue},
		success: function(result) {
			console.log(result.toString);
			
			
			// 대기열이 끝났다면!
			if(result.isAvailableEnter){
				stopTry();
				
				//alert(result.certCode);
				location.href = `http://localhost:8088/concertList?certCode=${result.certCode}`;				
			}
			// 아직 대기열 중이라면..
			else{
				document.querySelector('#numLeftQueue').innerHTML = result.numLeftQueue + ' 명';
			
				
			}
			
		},
		error: function() {
			alert('ㅠㅠ');
		}
	});
	//ajax end
}


let run;
function tryEnterOnAndOn()
{ run = setInterval(isAvailableEnter, 200); }

function stopTry()
{ clearInterval(run); }

$(document).ready(function(){
	enterTheWaitingQueue();
	tryEnterOnAndOn();
});










function reallyGoTetris() {

	Swal.fire({
		title: '정말 대기열을 나가시겠습니까?',
		showCancelButton: true,
		cancelButtonText: `돌아가기.`,
		confirmButtonText: '게임으로!',
	}).then((result) => {
		if (result.isConfirmed)
			location.href='/tetris';
	});

}