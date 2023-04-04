package kh.study.tetris.vo;

import lombok.Data;

@Data
public class CurrentBlockVO {
	private int piece_kind;
	private int piece_rotation;
	private int piece_Y;
	private int piece_X;
}
