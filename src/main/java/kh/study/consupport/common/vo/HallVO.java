package kh.study.consupport.common.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HallVO {
//	TABLE HALL
//
//		HALL_CODE         VARCHAR2(100) CONSTRAINT HALL_PK PRIMARY KEY
//		HALL_NAME         VARCHAR2(100) NOT NULL
//		HALL_SEAT_CNT     NUMBER NOT NULL
//		HALL_RENT_PRICE   NUMBER NOT NULL
//		HALL_DETAIL       VARCHAR2(500)
//	
//		USER_ID           VARCHAR2(100) NOT NULL
//		                  CONSTRAINT HALL_FK_REF_USER REFERENCES USERS(USER_ID)
	
	private String hallCode;
	
	private String hallName;
	private int hallSeatCnt;
	private int hallRentPrice;
	private String hallDetail;
	private String userId;
	
	
	private List<String> hallRentDateList;
	private List<HallImgVO> hallImgList;
	private HallSeatVO hallSeat;
	private List<HallDateVO> hallDateList;
}
