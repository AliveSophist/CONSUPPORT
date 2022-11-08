package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HallSeatVO {
//	TABLE HALL_SEAT
//	
//		-- {Non PK Only FK Table}
//		HALL_CODE         VARCHAR2(100) NOT NULL
//		                  CONSTRAINT HALL_SEAT_FK_REF_HALL REFERENCES HALL(HALL_CODE)
//		HALL_SEAT_R_CNT   NUMBER
//		HALL_SEAT_S_CNT   NUMBER
//		HALL_SEAT_A_CNT   NUMBER
	
	private String hallCode;
	
	private int hallSeatRCnt;
	private int hallSeatSCnt;
	private int hallSeatACnt;
}
