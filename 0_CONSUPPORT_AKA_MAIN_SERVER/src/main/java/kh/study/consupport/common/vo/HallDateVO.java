package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HallDateVO {
//	TABLE HALL_DATE
//
//		HALL_DATE_CODE    VARCHAR2(100) CONSTRAINT HALL_DATE_PK PRIMARY KEY
//		HALL_RENT_DATE    DATE NOT NULL
//		HALL_STATUS       VARCHAR2(20) DEFAULT 'EMPTY' NOT NULL        -- (EMPTY, BOOKED)
//		
//		HALL_CODE         VARCHAR2(100) NOT NULL
//		                  CONSTRAINT HALL_DATE_FK_REF_HALL REFERENCES HALL(HALL_CODE)
	
	private String hallDateCode;
	
	private String hallRentDate;
	private String hallStatus;
	
	private String hallCode;
}
