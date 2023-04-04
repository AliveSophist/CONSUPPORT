package kh.study.consupport.common.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketVO {
//	TABLE TICKET
//	
//		TICKET_CODE       VARCHAR2(100) CONSTRAINT TICKET_PK PRIMARY KEY
//		SEAT_CODE         VARCHAR2(100) NOT NULL		-- 'SEAT_001' ~ 'SEAT_100'
//		
//		USER_ID           VARCHAR2(100)
//		                  CONSTRAINT TICKET_FK_REF_USER REFERENCES USERS(USER_ID)
//		HALL_CODE         VARCHAR2(100) NOT NULL
//		                  CONSTRAINT TICKET_FK_REF_HALL REFERENCES HALL(HALL_CODE)
//		CONCERT_CODE      VARCHAR2(100) NOT NULL
//		                  CONSTRAINT TICKET_FK_REF_CONCERT REFERENCES CONCERT(CONCERT_CODE)
	
	private String ticketCode;
	
	private String seatCode;
	private String userId;
	private String hallCode;
	private String concertCode;
	
	private List<String> seatCodeList;

	// userId null 일경우 'EMPTY'
	// userId null 아닐경우 'BOOKED'
	private String seatStatus;
}
