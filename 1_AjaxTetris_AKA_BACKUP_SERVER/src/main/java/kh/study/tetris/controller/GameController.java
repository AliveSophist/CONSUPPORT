package kh.study.tetris.controller;

import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.tetris.service.CouponService;
import kh.study.tetris.vo.CouponVO;
import kh.study.tetris.vo.CurrentBlockVO;
import kh.study.tetris.vo.UserVO;
import lombok.Data;

@Controller
@RequestMapping("tetris")
public class GameController {
	
	// tetris_board[row][col]; 20+1행 10+2열
	private final int TETRIS_BOARD_MAX_Y = 21;
	private final int TETRIS_BOARD_MAX_X = 12;

	private final int TETRIS_PIECE_STARTPOINT_Y = 1;
	private final int TETRIS_PIECE_STARTPOINT_X = 4;

	private final byte[][][][] TETRIS_PIECE_SHAPE =
	//new boolean [7 /* kind */ ][4 /* rotation */ ][4 /* horizontal blocks */ ][4 /* vertical blocks */ ]
	{
		// Square
		{
			{
				{0, 0, 0, 0},
				{0, 1, 1, 0},
				{0, 1, 1, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{0, 1, 1, 0},
				{0, 1, 1, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{0, 1, 1, 0},
				{0, 1, 1, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{0, 1, 1, 0},
				{0, 1, 1, 0},
				{0, 0, 0, 0}
			}
		},
		// I
		{
			{
				{0, 0, 0, 0},
				{1, 1, 1, 1},
				{0, 0, 0, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 1, 0, 0},
				{0, 1, 0, 0},
				{0, 1, 0, 0},
				{0, 1, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{1, 1, 1, 1},
				{0, 0, 0, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 1, 0, 0},
				{0, 1, 0, 0},
				{0, 1, 0, 0},
				{0, 1, 0, 0}
			}
		},
		// L
		{
			{
				{0, 0, 0, 0},
				{0, 0, 1, 0},
				{1, 1, 1, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 1, 0, 0},
				{0, 1, 1, 0}
			},
			{
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{1, 1, 1, 0},
				{1, 0, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{1, 1, 0, 0},
				{0, 1, 0, 0},
				{0, 1, 0, 0}
			}
		},
		// L mirrored
		{
			{
				{0, 0, 0, 0},
				{1, 0, 0, 0},
				{1, 1, 1, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{0, 1, 1, 0},
				{0, 1, 0, 0},
				{0, 1, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{1, 1, 1, 0},
				{0, 0, 1, 0}
			},
			{
				{0, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 1, 0, 0},
				{1, 1, 0, 0}
			}
		},
		// N
		{
			{
				{0, 0, 0, 0},
				{1, 1, 0, 0},
				{0, 1, 1, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 0, 1, 0},
				{0, 1, 1, 0},
				{0, 1, 0, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{1, 1, 0, 0},
				{0, 1, 1, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 0, 1, 0},
				{0, 1, 1, 0},
				{0, 1, 0, 0},
				{0, 0, 0, 0}
			}
		},
		// N mirrored
		{
			{
				{0, 0, 0, 0},
				{0, 1, 1, 0},
				{1, 1, 0, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 1, 0, 0},
				{0, 1, 1, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{0, 1, 1, 0},
				{1, 1, 0, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 1, 0, 0},
				{0, 1, 1, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 0}
			}
		},
		// T
		{
			{
				{0, 0, 0, 0},
				{0, 1, 0, 0},
				{1, 1, 1, 0},
				{0, 0, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 1, 1, 0},
				{0, 1, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{1, 1, 1, 0},
				{0, 1, 0, 0}
			},
			{
				{0, 0, 0, 0},
				{0, 1, 0, 0},
				{1, 1, 0, 0},
				{0, 1, 0, 0}
			}
		}
	};
	
	
	
	
	
	@GetMapping("")
	public String initGame(HttpSession session, Authentication authentication, UserVO user) {
						/*	, @CurrentSecurityContext SecurityContext context	AnonymousAuthenticationFilter 를 건드리지 않았을 경우에 필요했다..! */
								// 해당 문법이었을때 꺼내 썼던 방법
								// ( (UserDetails)context.getAuthentication().getPrincipal() ).getUsername()
								// SecurityContext 대신 SecurityContextHolder를 쓴 경우
								// System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println("userId : "+userId);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		
		//뭔가.. 어떻게던 구현하긴 할거임.. 이건 힘들고..
//		if(session.getAttribute(userId) != null) {
//			
//			System.out.println("해당 유저는 현재 세션에 존재합니다...");
//			System.out.println("!! R E L O A D !!");
//			
//			return "redirect:/tetris";
//		}
		
		user.setTetris_board( new int[][]{
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
		});
		user.setScore(0);
		user.setPiece_kind_next(new Random(System.currentTimeMillis()).nextInt(7));
		session.setAttribute(userId, user);

		return "/content/game_board";
	}










	@ResponseBody
	@PostMapping("ajaxcommand_INTERVAL_EVENT")
	Object intervalEvent (HttpSession session, Authentication authentication, CurrentBlockVO block, boolean isEvaded) {

		// return 목적 LocalClass..!
		@Data
		class IntervalEventResultVO {

			private String event;
			/*	"MOVEABLE"	: MOVE_DOWN 가능함
			 * 									Ajax동작
			 * 									1. piece_Y++;
			 * 									2. onDraw();
			 * 	"EVADED"	: 방금 1회 회피함
			 * 									Ajax동작
			 * 									*. 없. 음.
			 * 	"LANDED"	: 착지함 score 전송
			 * 									Ajax동작
			 * 									1. 블럭 착지
			 * 									 - intScore가 0보다 크다면...
			 * 									2. 점수 갱신
			 * 									3. 라인 클리어 애니메이션
			 * 									4. 게임오버 확인 << generateBlockEvent 에 분리
			 */	
			private int score;

			IntervalEventResultVO returnEventIs(String event) {
				this.event = event;
				return this;
			}
			
		}	IntervalEventResultVO result = new IntervalEventResultVO();

		//Load UserInfo from Session
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		UserVO user = (UserVO)session.getAttribute(userId);
		int[][] tetris_board = user.getTetris_board();

		int piece_kind		= block.getPiece_kind();
		int piece_rotation	= block.getPiece_rotation();
		int piece_Y			= block.getPiece_Y();
		int piece_X			= block.getPiece_X();



		// check isMoveable
		boolean isMoveable = true;
		for (int i = piece_Y; i < piece_Y+4; i++)
			for (int j = piece_X; j < piece_X+4; j++)
				if(i+1<TETRIS_BOARD_MAX_Y && 0<=j && j<TETRIS_BOARD_MAX_X)
					if (tetris_board[i + 1][j]
						+ TETRIS_PIECE_SHAPE[piece_kind][piece_rotation][i - piece_Y][j - piece_X] >= 2)
						isMoveable = false;

		if(isMoveable)
			return result.returnEventIs("MOVEABLE");



		// check Client's isEvaded
		if(!isEvaded)
			return result.returnEventIs("EVADED");



		// land block & clearLine & calculate score
		// 애니메이션 하여야 하므로 클라이언트도 동일하게 수행해야만 한다..!
		int incScore = 0;
		{
			// land block
			for (int i = piece_Y; i < piece_Y + 4; i++)
				for (int j = piece_X; j < piece_X + 4; j++)
					if(0<=i && i<TETRIS_BOARD_MAX_Y && 0<=j && j<TETRIS_BOARD_MAX_X)
						tetris_board[i][j] += TETRIS_PIECE_SHAPE[piece_kind][piece_rotation][i - piece_Y][j - piece_X];

			// clear line & calculate score
			int combo=0;

			for(int i=3; i<TETRIS_BOARD_MAX_Y-1; i++){

				boolean isFulled=true;
				for(int j=1; j<TETRIS_BOARD_MAX_X-1; j++){
					if(tetris_board[i][j] < 1)
						isFulled=false;
				}

				if(isFulled){
					//increase score
					combo = (combo!=0 ? combo*2 : 1000);
					incScore += combo;

					//fall lines
					for(int j=i; j>=3; j--)
						for(int k=0; k<TETRIS_BOARD_MAX_X-1; k++)
							tetris_board[j][k]=tetris_board[j-1][k];

					//clean line
					char temp[]={1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
					for(int j=0; j<10; j++)
						tetris_board[3][j]=temp[j];
			    }
			}
		}

		user.setTetris_board(tetris_board);
		user.setScore(user.getScore() + incScore);
		session.setAttribute(userId, user);

		result.setScore(user.getScore());
		
		return result.returnEventIs("LANDED");
	}
	
	
	
	

	@ResponseBody
	@PostMapping("ajaxcommand_GENERATE_BLOCK_EVENT")
	Object generateBlockEvent (HttpSession session, Authentication authentication) {
		
		// return 목적 LocalClass..!
		@Data
		class GenerateBlockEventResultVO {

			String event;
			/* 	"CONTINUE"	: 게임 지속 piece_kind_next 전송
			 * 								Ajax동작
			 * 								새블럭 생성!
			 * 
			 * 	"GAMEOVER"	: 게임 종료
			 * 								Ajax동작
			 * 								게임오버된 겹쳐있는 블럭 생성..!
			 * 								가메 오베!
			 */
			int piece_kind_next;
			Map<String, String> coupon;
			
			GenerateBlockEventResultVO returnEventIs(String event) {
				this.event = event;
				return this;
			}
			
		}	GenerateBlockEventResultVO result = new GenerateBlockEventResultVO();
		
		// 간지나게 클래스로 던지려고 쓴거지만 사실 아래처럼 해쉬맵 쓰는거랑 차이없다;
		// ex)
		//		Map<String, Object> resultByHash = new HashMap<>();
		//		resultByHash.put("event"			, "#!$!@#$!@#");
		//		resultByHash.put("piece_kind_next"	, 1);
		//
		// 이거랑 다를게 없다..;

		
		//Load UserInfo from Session
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		UserVO user = (UserVO)session.getAttribute(userId);
		int[][] tetris_board = user.getTetris_board();
		user.setPiece_kind( user.getPiece_kind_next() );
		user.setPiece_kind_next( new Random(System.currentTimeMillis()).nextInt(7) );

		int piece_Y = TETRIS_PIECE_STARTPOINT_Y;
		int piece_X = TETRIS_PIECE_STARTPOINT_X;

		for (int i = piece_Y; i < piece_Y + 4; i++)
			for (int j = piece_X; j < piece_X + 4; j++)
				if (tetris_board[i][j] > 0) {
					
					// CONSUPPORT coupon
					CouponVO coupon = insertCoupon(session, authentication);
					result.setCoupon( coupon.toHashMap() );
					
					//Delete UserInfo from Session
					session.removeAttribute(userId);
					
					return result.returnEventIs("GAMEOVER");
				}

		result.setPiece_kind_next(user.getPiece_kind_next());
		
		return result.returnEventIs("CONTINUE");
	}

	// GAMEOVER 시, CONSUPPORT 쿠폰 생성
	@Resource(name = "couponService")
	private CouponService couponService;

	public CouponVO insertCoupon(HttpSession session, Authentication authentication) {
		
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		UserVO user = (UserVO)session.getAttribute(userId);

		int score = user.getScore();
		
		// 30% 쿠폰!
		if		(score >= 50000)
			return couponService.insertCoupon( 30 );
		
		// 15% 쿠폰!
		else if	(score >= 20000)
			return couponService.insertCoupon( 15 );
		
		// 5% 쿠폰!
		else
			return couponService.insertCoupon( 5 );
	}
	// GAMEOVER 시, CONSUPPORT 쿠폰 생성

	
	
	
	
	@ResponseBody
	@PostMapping("ajaxcommand_SPIN")
	boolean spin (HttpSession session, Authentication authentication, CurrentBlockVO block) {

		//Load UserInfo from Session
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		UserVO user = (UserVO)session.getAttribute(userId);
		int[][] tetris_board = user.getTetris_board();

		int piece_kind		= block.getPiece_kind();
		int piece_rotation	= block.getPiece_rotation();
		int piece_Y			= block.getPiece_Y();
		int piece_X			= block.getPiece_X();

		for (int i = piece_Y; i < piece_Y + 4; i++)
			for (int j = piece_X; j < piece_X+4; j++)
				if(0 <= j && j < TETRIS_BOARD_MAX_X)
					if (piece_rotation < 3) {
						if (tetris_board[i][j] + TETRIS_PIECE_SHAPE[piece_kind][piece_rotation + 1][i - piece_Y][j - piece_X] >= 2)
							return false;
					} else {
						if (tetris_board[i][j] + TETRIS_PIECE_SHAPE[piece_kind][0][i - piece_Y][j - piece_X] >= 2)
							return false;
					}

		return true;
	}
	@ResponseBody
	@PostMapping("ajaxcommand_MOVE_LEFT")
	boolean moveLeft (HttpSession session, Authentication authentication, CurrentBlockVO block) {
		
		//Load UserInfo from Session
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		UserVO user = (UserVO)session.getAttribute(userId);
		int[][] tetris_board = user.getTetris_board();

		int piece_kind		= block.getPiece_kind();
		int piece_rotation	= block.getPiece_rotation();
		int piece_Y			= block.getPiece_Y();
		int piece_X			= block.getPiece_X();

		for(int i=piece_Y; i<piece_Y+4; i++)
			for(int j=piece_X; j<piece_X+4; j++)
				if(i<TETRIS_BOARD_MAX_Y && j-1 >= 0)
					if(tetris_board[i][j-1]+TETRIS_PIECE_SHAPE[piece_kind][piece_rotation][i-piece_Y][j-piece_X]>=2)
						return false;

		return true;
	}
	@ResponseBody
	@PostMapping("ajaxcommand_MOVE_RIGHT")
	boolean moveRight (HttpSession session, Authentication authentication, CurrentBlockVO block) {
		
		//Load UserInfo from Session
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		UserVO user = (UserVO)session.getAttribute(userId);
		int[][] tetris_board = user.getTetris_board();

		int piece_kind		= block.getPiece_kind();
		int piece_rotation	= block.getPiece_rotation();
		int piece_Y			= block.getPiece_Y();
		int piece_X			= block.getPiece_X();

		for (int i=piece_Y; i<piece_Y+4; i++)
			for (int j=piece_X; j<piece_X+4; j++)
				if(j+1 < TETRIS_BOARD_MAX_X)
					if (i<TETRIS_BOARD_MAX_Y && tetris_board[i][j + 1]
							+ TETRIS_PIECE_SHAPE[piece_kind][piece_rotation][i - piece_Y][j - piece_X] >= 2)
						return false;

		return true;
	}
	@ResponseBody
	@PostMapping("ajaxcommand_MOVE_DOWN")
	boolean moveDown (HttpSession session, Authentication authentication, CurrentBlockVO block) {

		//Load UserInfo from Session
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		UserVO user = (UserVO)session.getAttribute(userId);
		int[][] tetris_board = user.getTetris_board();

		int piece_kind		= block.getPiece_kind();
		int piece_rotation	= block.getPiece_rotation();
		int piece_Y			= block.getPiece_Y();
		int piece_X			= block.getPiece_X();

		for (int i = piece_Y; i < piece_Y+4; i++)
			for (int j = piece_X; j < piece_X+4; j++)
				if(i+1<TETRIS_BOARD_MAX_Y && 0<=j && j<TETRIS_BOARD_MAX_X)
					if (tetris_board[i + 1][j]
							+ TETRIS_PIECE_SHAPE[piece_kind][piece_rotation][i - piece_Y][j - piece_X] >= 2)
						return false;

		return true;
	}
	@ResponseBody
	@PostMapping("ajaxcommand_LAND")
	int land (HttpSession session, Authentication authentication, CurrentBlockVO block) {

		//Load UserInfo from Session
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		UserVO user = (UserVO)session.getAttribute(userId);
		int[][] tetris_board = user.getTetris_board();

		int piece_kind		= block.getPiece_kind();
		int piece_rotation	= block.getPiece_rotation();
		int piece_Y			= block.getPiece_Y();
		int piece_X			= block.getPiece_X();

		int moved = 0;

		while(true) {
			for (int i = piece_Y; i < piece_Y+4; i++)
				for (int j = piece_X; j < piece_X+4; j++)
					if(i+1<TETRIS_BOARD_MAX_Y && 0<=j && j<TETRIS_BOARD_MAX_X)
						if (tetris_board[i + 1][j] +
								TETRIS_PIECE_SHAPE[piece_kind][piece_rotation][i - piece_Y][j - piece_X] >= 2)
							return moved;
			
			piece_Y++;
			moved++;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	//For Debugging
//	private void printTetrisBoard( int[][] tetris_board ) {
//		for(int i=0; i<TETRIS_BOARD_MAX_Y; i++)
//		{
//			for(int j=0; j<TETRIS_BOARD_MAX_X; j++)
//				System.out.print(tetris_board[i][j] + " ");
//			
//			System.out.println();
//		}
//		System.out.println();
//		System.out.println();
//	}
}
