package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalesDetailVO {
//	TABLE SALES_DETAIL
//
//		SALES_DETAIL_CODE VARCHAR2(100) CONSTRAINT SALES_DETAIL_PK PRIMARY KEY
//		
//		SALES_CODE        VARCHAR2(100) NOT NULL
//		                  CONSTRAINT SALES_DETAIL_FK_REF_SALES REFERENCES SALES(SALES_CODE)
//		TICKET_CODE       VARCHAR2(100) NOT NULL
//		                  CONSTRAINT SALES_DETAIL_FK_REF_TICKET REFERENCES TICKET(TICKET_CODE)
	
	private String salesDetailCode;
	
	private String salesCode;
	private String ticketCode;
	
	// 좌석 정보
	private String seatCode;	

	// 티켓 가격
	private int concertPrice;
}
