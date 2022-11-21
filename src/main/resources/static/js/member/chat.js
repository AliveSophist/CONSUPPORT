window.addEventListener("keyup", e => {
  const key = document.getElementById(e.key);
  if(e.key=='Enter'){
	$("#button-send").click();
}
  
});

$(document).ready(function(){
$("#disconn").on("click", (e) => {
    disconnect();
})
const view=document.querySelector('.viewChat');
$("#button-send").on("click", (e) => {
    send();
	setTimeout(function(){
		view.scrollTo(0,view.scrollHeight);
	},10);
});


const username = document.querySelector('#memberId').innerText;
const websocket = new WebSocket("ws://localhost:8088/ws/chat");
websocket.onmessage = onMessage;
websocket.onopen = onOpen;
websocket.close = onClose;
function send(){
	
    let msg = document.getElementById("msg");
	if(msg.value==(''))
	{
		return
	}
    console.log(username + ":" + msg.value);
    websocket.send(username + ":" + msg.value);
    msg.value = '';
}

//채팅창에서 나갔을 때
function onClose() {
    var str = username+"님이 방을 나가셨습니다.";
    $("#msgArea").append(str);
}

//채팅창에 들어왔을 때
function onOpen() {
    var str = "상담사가 상담중입니다. 잠시만 기다려 주세요.";
    $("#msgArea").append(str);
}

function onMessage(msg) {
    var data = msg.data;
    var sessionId = null;
    //데이터를 보낸 사람
    var message = null;
    var arr = data.split(":");

    for(var i=0; i<arr.length; i++){
        console.log('arr[' + i + ']: ' + arr[i]);
    }

    var cur_session = username;

    //현재 세션에 로그인 한 사람
    console.log("cur_session : " + cur_session);
    sessionId = arr[0];
    message = arr[1];

    console.log("sessionID : " + sessionId);
    console.log("cur_session : " + cur_session);

    //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
    if(sessionId == cur_session){
        var str = "<div class='col-12'>";
        str += "<div class=row>"
        str += "<div class=col-6></div><div class=col-6>";
        str += `<div class='text-end'>${sessionId}</div>`
        str += "<div class='alert alert-secondary text-start'>";
        str += "<b>" + message + "</b>";
        str += "</div></div></div></div>";
        $("#msgArea").append(str);
    }
    else{
		var scrollTop = $(view).scrollTop();
	    var innerHeight = $(view).innerHeight();
	    var scrollHeight = $(view).prop('scrollHeight');
        var str = "<div class='col-6'>";
        str += `<div class='text-start'>${sessionId}</div>`
        str += "<div class='alert alert-warning text-start'>";
        str += "<b>" + message + "</b>";
        str += "</div></div>";
        $("#msgArea").append(str);
        if (scrollTop + innerHeight+1 >= scrollHeight) {
			setTimeout(function(){
				view.scrollTo(0,view.scrollHeight);
			},10);
	    } else {
		}
    }
}
})
