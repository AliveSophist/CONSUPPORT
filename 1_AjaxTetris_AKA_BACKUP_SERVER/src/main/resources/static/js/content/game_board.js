
let piece_Y, piece_X;

let piece_kind;
let piece_kind_next = document.querySelector('#piece_kind_next').value;
let piece_rotation;

let isEvaded = false;

// tetris_board[row][col]; 20+1행 10+2열
let score = 0;
let tetris_board =
[
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1],
	[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
];

const TETRIS_BOARD_MAX_Y = 21;
const TETRIS_BOARD_MAX_X = 12;

const TETRIS_PIECE_STARTPOINT_Y = 1;
const TETRIS_PIECE_STARTPOINT_X = 4;

const TETRIS_PIECE_SHAPE = //[7 /*kind */ ][4 /* rotation */ ][4 /* horizontal blocks */ ][4 /* vertical blocks */ ] =
[
	[	// Square
		[
			[0, 0, 0, 0],
			[0, 1, 1, 0],
			[0, 1, 1, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[0, 1, 1, 0],
			[0, 1, 1, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[0, 1, 1, 0],
			[0, 1, 1, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[0, 1, 1, 0],
			[0, 1, 1, 0],
			[0, 0, 0, 0]
		]
	],
	[	// I
		[
			[0, 0, 0, 0],
			[2, 2, 2, 2],
			[0, 0, 0, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 2, 0, 0], 
			[0, 2, 0, 0],
			[0, 2, 0, 0],
			[0, 2, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[2, 2, 2, 2],
			[0, 0, 0, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 2, 0, 0], 
			[0, 2, 0, 0],
			[0, 2, 0, 0],
			[0, 2, 0, 0]
		]
	],
	[	// L
		[
			[0, 0, 0, 0],
			[0, 0, 3, 0],
			[3, 3, 3, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[0, 3, 0, 0],
			[0, 3, 0, 0],
			[0, 3, 3, 0]
		],
		[
			[0, 0, 0, 0],
			[0, 0, 0, 0],
			[3, 3, 3, 0],
			[3, 0, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[3, 3, 0, 0],
			[0, 3, 0, 0],
			[0, 3, 0, 0]
		]
	],
	[	// L mirrored
		[
			[0, 0, 0, 0],
			[4, 0, 0, 0],
			[4, 4, 4, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[0, 4, 4, 0],
			[0, 4, 0, 0],
			[0, 4, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[0, 0, 0, 0],
			[4, 4, 4, 0],
			[0, 0, 4, 0]
		],
		[
			[0, 0, 0, 0],
			[0, 4, 0, 0],
			[0, 4, 0, 0],
			[4, 4, 0, 0]
		]
	],
	[	// N
		[
			[0, 0, 0, 0],
			[5, 5, 0, 0],
			[0, 5, 5, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 0, 5, 0],
			[0, 5, 5, 0],
			[0, 5, 0, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[5, 5, 0, 0],
			[0, 5, 5, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 0, 5, 0],
			[0, 5, 5, 0],
			[0, 5, 0, 0],
			[0, 0, 0, 0]
		]
	],
	[	// N mirrored
		[
			[0, 0, 0, 0],
			[0, 6, 6, 0],
			[6, 6, 0, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 6, 0, 0],
			[0, 6, 6, 0],
			[0, 0, 6, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[0, 6, 6, 0],
			[6, 6, 0, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 6, 0, 0],
			[0, 6, 6, 0],
			[0, 0, 6, 0],
			[0, 0, 0, 0]
		]
	],
	[	// T
		[
			[0, 0, 0, 0],
			[0, 7, 0, 0],
			[7, 7, 7, 0],
			[0, 0, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[0, 7, 0, 0],
			[0, 7, 7, 0],
			[0, 7, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[0, 0, 0, 0],
			[7, 7, 7, 0],
			[0, 7, 0, 0]
		],
		[
			[0, 0, 0, 0],
			[0, 7, 0, 0],
			[7, 7, 0, 0],
			[0, 7, 0, 0]
		]
	]
];
	
	
	


// Canvas 변수1
let init=false;
let myCanvas;
let Context;

// 테트리스 보드 초기화. 최초 1회만 실행
function Init()
{
	if(init==false)
	{
		myCanvas=document.getElementById("MyCanvas");
		Context=myCanvas.getContext("2d");		
		init=true;
		
		onDraw();
	}
}





// Canvas 변수2
let tetris_block_boxsize=25;
let game_board_top_margin=25;
let game_board_left_margin=100;

// 테트리스 보드 그리기. 실시간으로 계속 그려주기
function onDraw()
{
	if(init==false) return;
	//테두리
	Context.strokeStyle="#000";
	Context.lineWidth=1;
	Context.strokeRect(0, 0, myCanvas.width-1, myCanvas.height-1);
	
														//////////////////////////////////
														//	For Debugging
														/*
														console.log('piece_Y : '+piece_Y);
														console.log('piece_X : '+piece_X);
														
														let thisblock;
														switch(piece_kind){
															case 0: thisblock = 'square';
															break;
															case 1: thisblock = 'I';
															break;
															case 2: thisblock = 'L';
															break;
															case 3: thisblock = 'Lmirror';
															break;
															case 4: thisblock = 'N';
															break;
															case 5: thisblock = 'Nmirror';
															break;
															case 5: thisblock = 'T';
															break;
														}
														console.log(`thisblock : ${thisblock}`);
														*/
														//	For Debugging
														//////////////////////////////////

	// 블럭 표시
	for(var i=2; i<TETRIS_BOARD_MAX_Y; ++i)
	{
		for(var j=0;j<TETRIS_BOARD_MAX_X;++j)
		{
			// 두가지색으로 표현된 초경량 모드...
//			Context.fillStyle = (tetris_board[i][j]==0 ? "#ccc" : "red")
//			
//			if(piece_Y <= i && i < piece_Y+4 && piece_X <= j && j < piece_X+4){
//				if(TETRIS_PIECE_SHAPE[piece_kind][piece_rotation][i-piece_Y][j-piece_X]!=0)
//					Context.fillStyle="red";
//			}
			
			//게임보드의 테두리 벽은 #444
			//게임보드의 비어있는 칸은 #ccc
			//블럭 모양에 따라 각기 다른 색 출력.
			Context.fillStyle = (tetris_board[i][j]<0 ? "#444" 		: 
								(tetris_board[i][j]<1 ? "#ccc" 		: 
								(tetris_board[i][j]<2 ? "RED" 		: 
								(tetris_board[i][j]<3 ? "MAGENTA" 	: 
								(tetris_board[i][j]<4 ? "ORANGE" 	: 
								(tetris_board[i][j]<5 ? "GREEN" 	: 
								(tetris_board[i][j]<6 ? "BLUE" 		: 
								(tetris_board[i][j]<7 ? "NAVY" 		: "PURPLE" ) ) ) ) ) ) ) );
			
			//현재 움직이고 있는 블럭은 덧씌워 그려준다!
			if(piece_Y <= i && i < piece_Y+4 && piece_X <= j && j < piece_X+4){
				if(TETRIS_PIECE_SHAPE[piece_kind][piece_rotation][i-piece_Y][j-piece_X]!=0)
					Context.fillStyle = (piece_kind==0 ? "RED" 		: 
										(piece_kind==1 ? "MAGENTA" 	: 
										(piece_kind==2 ? "ORANGE" 	: 
										(piece_kind==3 ? "GREEN" 	: 
										(piece_kind==4 ? "BLUE" 	: 
										(piece_kind==5 ? "NAVY" 	: "PURPLE" ) ) ) ) ) );
			}
			
			if(isGAMEOVER){
				if(tetris_board[i][j] == -444)
					Context.fillStyle = "#444";
				if(tetris_board[i][j] == -999)
					Context.fillStyle = "#999";
			}
			
			x=game_board_left_margin + j*tetris_block_boxsize;
			y=game_board_top_margin + i*tetris_block_boxsize;
			Context.fillRect(x, y, tetris_block_boxsize-2, tetris_block_boxsize-2);	// 2칸씩 덜그리면 윤곽선이 생긴다!?
		}
	}
}


let next_piece_top_margin=100;
let next_piece_left_margin=430;

// 테트리스 보드 그리기. 실시간으로 계속 그려주기
function onDrawNextPiece()
{
	// 블럭 표시
	for(var i=0; i<4; ++i)
	{
		for(var j=0;j<4;++j)
		{
			Context.fillStyle = (TETRIS_PIECE_SHAPE[piece_kind_next][0][i][j]<1 ? "#ccc" 		: 
								(TETRIS_PIECE_SHAPE[piece_kind_next][0][i][j]<2 ? "RED" 		: 
								(TETRIS_PIECE_SHAPE[piece_kind_next][0][i][j]<3 ? "MAGENTA" 	: 
								(TETRIS_PIECE_SHAPE[piece_kind_next][0][i][j]<4 ? "ORANGE" 		: 
								(TETRIS_PIECE_SHAPE[piece_kind_next][0][i][j]<5 ? "GREEN" 		: 
								(TETRIS_PIECE_SHAPE[piece_kind_next][0][i][j]<6 ? "BLUE" 		: 
								(TETRIS_PIECE_SHAPE[piece_kind_next][0][i][j]<7 ? "NAVY" 		: "PURPLE" ) ) ) ) ) ) );
			
			x=next_piece_left_margin + j*tetris_block_boxsize;
			y=next_piece_top_margin + i*tetris_block_boxsize;
			Context.fillRect(x, y, tetris_block_boxsize-2, tetris_block_boxsize-2);
		}
	}
}





let nowKeyEvent = '';

let numInterval = 202;	//Starts 202 ! It Downs according to numClearedLine...
let numLeftCount = numInterval;

let numClearedLine = 0;

let isGAMEOVER = false;

let isCLEARLINE = false;

let intervalPer1ms;
function intervalPer1msPlay(func) { intervalPer1ms = setInterval(func, 1); }
function intervalPer1msStop() { clearInterval(intervalPer1ms); }

// 타임 인터럽트 함수
function Run()
/********************************* 이벤트 중복실행 방지!! *********************************/
{
	intervalPer1msStop();
/********************************* 이벤트 중복실행 방지!! *********************************/
	
	
	
	// 입력된 키입력이 있다면 ajaxJoystick(nowKeyEvent) 실행
	// 이후 실행완료된 해당 키입력 지워줌
	if (nowKeyEvent.length > 0)
	{
		ajaxJoystick(nowKeyEvent);
		
		nowKeyEvent = '';
	}
	else
	{
		// numLeftCount가 0가 되면 ajaxIntervalEvent 실행
		if (numLeftCount == 0)
		{
			console.log(`RUN!! NOW INTERVAL is ${numInterval}`);
			
			ajaxIntervalEvent();
			
			numLeftCount = isEvaded ? 66 : numInterval;
		}
		
		// numLeftCount 매 카운트마다 깎는다
		numLeftCount -= 1;
	}
	
	//게임오버 발생 시 애니메이션 재생.
	if(isGAMEOVER){
		nowAnimation = 'GAMEOVER';
		intervalPer1msPlay(PlayAnimation);
		return;
	}
	
	
	
/********************************* 이벤트 중복실행 방지!! *********************************/
	intervalPer1msPlay(Run);
}
/********************************* 이벤트 중복실행 방지!! *********************************/





let nowAnimation = '';

// 타임 인터럽트 함수
function PlayAnimation()
/********************************* 이벤트 중복실행 방지!! *********************************/
{
	intervalPer1msStop();
/********************************* 이벤트 중복실행 방지!! *********************************/
	
	
	
	// 행삭제 등의 애니메이션 로직이 끝나고 난 후
	// 다시 게임 로직이 수행되는 Run 인터럽트 함수로 회귀하고 싶은경우 true. 
	var isBackToTheRun = false;
	
	switch(nowAnimation){
		case 'GAMEOVER':
		{
			// numLeftCount가 0가 되면 실행 (애니메이션 역시 numLeftCount에 의해 반복 실행되어야 함)
			if (numLeftCount == 0)
			{
				// 아직 색이 살아있는 행을 찾는다
				for(var i=0; i<TETRIS_BOARD_MAX_Y-1; ++i){
					if(tetris_board[i][1] >= 0){
						
						// 찾은 행의 색을 죽인다
						for(var j=1; j<TETRIS_BOARD_MAX_X-1; ++j)
							tetris_board[i][j] = tetris_board[i][j]>0 ? -444 : -999;
						
						onDraw();
						break;
					}
					
					//맨 마지막 줄의 색을 죽인 후 발동. 모든 이벤트 정지.
					if(i == TETRIS_BOARD_MAX_Y-2){
						
						Swal.fire({ heightAuto: false , backdrop: "rgba(0,0,0,0.4)"	// swal 뜰 때 화면 위로 올라감 방지
							, icon: 'info'
							, title: `G A M E　O V E R`
							, text: `CLEARED LINES : ${numClearedLine}　SCORE : ${score}`
						}).then(() => {
							
							Swal.fire({ heightAuto: false , backdrop: "rgba(0,0,0,0.4)"	// swal 뜰 때 화면 위로 올라감 방지
								, icon: 'success'
								, title: `${document.querySelector('#couponValue').value}% 할인쿠폰을 획득하였습니다!`
								, html:
										`
											<div class="input-group" style="margin-left: 30px; width: auto;">
											
												<span id="copyButton" class="input-group-addon btn" title="Click to copy">
													<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-clipboard-check-fill" viewBox="0 0 16 16">
														<path d="M6.5 0A1.5 1.5 0 0 0 5 1.5v1A1.5 1.5 0 0 0 6.5 4h3A1.5 1.5 0 0 0 11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3Zm3 1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h3Z"/>
														<path d="M4 1.5H3a2 2 0 0 0-2 2V14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V3.5a2 2 0 0 0-2-2h-1v1A2.5 2.5 0 0 1 9.5 5h-3A2.5 2.5 0 0 1 4 2.5v-1Zm6.854 7.354-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L7.5 10.793l2.646-2.647a.5.5 0 0 1 .708.708Z"/>
													</svg>
												</span>
												
													
												<input type="text" id="copyTarget" class="form-control" value="${document.querySelector('#couponCode').value}" style="max-width:350px;">
											</div>`,
							}).then(() => {
								
								//location.href = '/tetris';
							});
							
							initCopyToClipboard();
							
						});
						
						return;
					}
				}
				numLeftCount = 20;
			}
			
			// numLeftCount 매 카운트마다 깎는다
			numLeftCount -= 1;
		}
		break;
		case 'CLEARLINE':
		{
			// numLeftCount가 0가 되면 실행 (애니메이션 역시 numLeftCount에 의해 반복 실행되어야 함)
			if (numLeftCount == 0)
			{
				
				
				
				
				numLeftCount = 20;
			}
			
			// numLeftCount 매 카운트마다 깎는다
			numLeftCount -= 1;
		}
		// 여러. 가지. 애니. 메이. 셔언.
	}
	
	
	
/********************************* 이벤트 중복실행 방지!! *********************************/
	if(isBackToTheRun)
		intervalPer1msPlay(Run);
	else
		intervalPer1msPlay(PlayAnimation);
}
/********************************* 이벤트 중복실행 방지!! *********************************/



$(document).ready(function(){
	Init();
	
	// 첫블럭 생성
	ajaxGenerateBlockEvent();
	
	// 타이머 인터럽트 개시
	intervalPer1msPlay(Run);
});






























/************************************************************************************************************/
/*												Ajax Codes													*/
/************************************************************************************************************/

document.onkeydown = checkKey;

function checkKey(e) {
	e = window.event;	// e = e || window.event; // 이걸로도 됨
	
	// left arrow MOVE_LEFT
	if (e.keyCode == '37')
	{	nowKeyEvent = 'MOVE_LEFT';		}
	
	// right arrow MOVE_RIGHT
	else if (e.keyCode == '39')
	{	nowKeyEvent = 'MOVE_RIGHT';		}
		
	// up arrow SPIN
	else if (e.keyCode == '38')
	{	nowKeyEvent = 'SPIN';			}
	
	// down arrow MOVE_DOWN
	else if (e.keyCode == '40')
	{	nowKeyEvent = 'MOVE_DOWN';		}
	
	// space LAND
	else if (e.keyCode == '32')
	{	nowKeyEvent = 'LAND';			}
	
	// 키코드 확인 https://www.toptal.com/developers/keycode
}

function ajaxJoystick(command){
	$.ajax({
		url: '/tetris/ajaxcommand_'+command, //요청경로. (컨트롤러)
		type: 'post',
		async: false,
		data:{"piece_kind":piece_kind
			, "piece_rotation":piece_rotation
			, "piece_Y":piece_Y
			, "piece_X":piece_X}, //보내는 데이터
		success: function(result) {
			
			if(!result){
				isEvaded = true;
				return;
			}
			
			switch(command){
				case 'SPIN':
				{
					piece_rotation = (piece_rotation==3)? 0 : (piece_rotation+1);
				}
				break;
				case 'MOVE_DOWN':
				{
					piece_Y++;
				}
				break;
				case 'MOVE_LEFT':
				{
					piece_X--;
				}
				break;
				case 'MOVE_RIGHT':
				{
					piece_X++;
				}
				break;
				case 'LAND':
				{
					piece_Y += result;
					
					isEvaded = true;
					numLeftCount = 66;
				}
			}
			
			onDraw();
		},
		error: function(){
			alert('실패');
		}
	});
}

function ajaxIntervalEvent(){
	$.ajax({
		url: '/tetris/ajaxcommand_INTERVAL_EVENT',
		type: 'post',
		async: false,
		data:{"piece_kind":piece_kind
			, "piece_rotation":piece_rotation
			, "piece_Y":piece_Y
			, "piece_X":piece_X
			, "isEvaded":isEvaded},
		success: function(result) {
			
			switch(result.event){
				case 'MOVEABLE':
				{
					piece_Y++;
					isEvaded = false;	// 회피 후 옆으로 옮겼을때 MOVEABLE이 수행 가능했을 경우 회피 기회 +1
				}
				break;
				case 'EVADED':
				{
					isEvaded = true;
				}
				break;
				case 'LANDED':
				{
					for (var i = piece_Y; i < piece_Y + 4; i++)
						for (var j = piece_X; j < piece_X + 4; j++)
							if(i<TETRIS_BOARD_MAX_Y && 0<=j && j<TETRIS_BOARD_MAX_X)
								tetris_board[i][j] += TETRIS_PIECE_SHAPE[piece_kind][piece_rotation][i - piece_Y][j - piece_X];

					if(result.score > 0){
						
						//update score
						score = result.score;
						
						
						// NEEEEEEED SOME ANIMATIONS !!!!!
						// NEEEEEEED SOME ANIMATIONS !!!!!
						// NEEEEEEED SOME ANIMATIONS !!!!!
						
		
						for(var i=3; i<TETRIS_BOARD_MAX_Y-1; i++){
			
							var isFulled = true;
							for(var j=1; j<TETRIS_BOARD_MAX_X-1; j++){
								if(tetris_board[i][j] < 1)
									isFulled=false;
							}
							
							if(isFulled){
								
//								var isPlaying = true;
//								while(isPlaying){
//									
//									
//									// numLeftCount가 0가 되면 실행 (애니메이션 역시 numLeftCount에 의해 반복 실행되어야 함)
//									if (numLeftCount == 0)
//									{
//										
//									}
//									
//									
//								}
								// 애니메이션 재생 필요.
								
								numClearedLine++;
								
								// 지운 라인이 4의 배수일때마다 게임 속도 가속..!
								// 202(시작) -> 152(4줄) -> 102(8줄) -> 52(12줄)
								if(numClearedLine%4 == 0 && numInterval > 100)
									numInterval-=50;
								
								// 18!? HARD MODE ON!
								if(numClearedLine == 18)
									numInterval-=10;
								
								//fall lines
								for(var j=i; j>=3; j--)
									for(var k=0; k<TETRIS_BOARD_MAX_X-1; k++)
										tetris_board[j][k]=tetris_board[j-1][k];
								
								//clean line
								var temp=[-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1];
								for(var j=0; j<TETRIS_BOARD_MAX_X; j++)
									tetris_board[3][j]=temp[j];
						    }
						}
					}
					ajaxGenerateBlockEvent();
				}
			}
		},
		error: function(){
			alert('실패');
		}
	});
	onDraw();
}

function ajaxGenerateBlockEvent(){
	$.ajax({
		url: '/tetris/ajaxcommand_GENERATE_BLOCK_EVENT',
		type: 'post',
		async: false,
		data:{},
		success: function(result) {
			//console.log(result.event);
			
			//서버에서 보내온 랜덤값으로 새 블럭 할당 & 값 초기화
			piece_kind = piece_kind_next;
			piece_kind_next = result.piece_kind_next;
			
			piece_rotation = 0;
			isEvaded = false;
			
			piece_Y = TETRIS_PIECE_STARTPOINT_Y;
			piece_X = TETRIS_PIECE_STARTPOINT_X;
			
			switch(result.event){
				case 'GAMEOVER':
				{
					// CONSUPPORT 쿠폰 관련.
					document.querySelector('#couponCode').value = result.coupon.couponCode;
					document.querySelector('#couponValue').value = result.coupon.couponValue;
					// CONSUPPORT 쿠폰 관련.
					
					
					
					for (var i = piece_Y; i < piece_Y + 4; i++)
						for (var j = piece_X; j < piece_X + 4; j++)
							tetris_board[i][j] += TETRIS_PIECE_SHAPE[piece_kind][piece_rotation][i - piece_Y][j - piece_X];

					isGAMEOVER = true;
					break;
				}
				case 'CONTINUE':
				{
					//nothing
				}
			}
			
			onDrawNextPiece()
		},
		error: function(){
			alert('실패');
		}
	});
}



























function initCopyToClipboard() {
	"use strict";

	function copyToClipboard(elem) {
		var target = elem;

		// select the content
		var currentFocus = document.activeElement;

		target.focus();
		target.setSelectionRange(0, target.value.length);

		// copy the selection
		var succeed;

		try {
			succeed = document.execCommand("copy");
		} catch (e) {
			console.warn(e);

			succeed = false;
		}

		// Restore original focus
		if (currentFocus && typeof currentFocus.focus === "function") {
			currentFocus.focus();
		}

//		if (succeed) {
//			$(".copied").animate({ top: -25, opacity: 0 }, 700, function() {
//				$(this).css({ top: 0, opacity: 1 });
//			});
//		}

		return succeed;
	}

	$("#copyButton, #copyTarget").on("click", function() {
		copyToClipboard(document.getElementById("copyTarget"));
	});
}