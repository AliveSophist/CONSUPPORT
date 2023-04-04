package kh.study.tetris.vo;

import lombok.Data;

@Data
public class UserVO {
	
	private String userId;	//a.k.a key, if ANONYMOUS user
	
	

	private int[][] tetris_board;

	private int score;
	private int piece_kind;
	private int piece_kind_next;

	private CurrentBlockVO currentBlockVO;

	private String userHighScore;

}