package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalesVO {
//	TABLE SALES
//	
//		SALES_CODE        VARCHAR2(100) CONSTRAINT SALES_PK PRIMARY KEY
//		SALES_AMOUNT      NUMBER NOT NULL
//		SALES_TOTAL_PRICE NUMBER NOT NULL
//		SALES_DATE        DATE DEFAULT SYSDATE NOT NULL
//		SALES_IS_STATUS   VARCHAR2(20) DEFAULT '' NOT NULL       -- (REFUNDED, CANCELED)
//		
//		USER_ID           VARCHAR2(100) NOT NULL
//		                  CONSTRAINT SALES_FK_REF_USER REFERENCES USERS(USER_ID)
//		HALL_CODE         VARCHAR2(100) NOT NULL
//		                  CONSTRAINT SALES_FK_REF_HALL REFERENCES HALL(HALL_CODE)
//		CONCERT_CODE      VARCHAR2(100) NOT NULL
//		                  CONSTRAINT SALES_FK_REF_CONCERT REFERENCES CONCERT(CONCERT_CODE)
	
	private String salesCode;
	
	private int salesAmount;
	private int salesTotalPrice;
	private String salesDate;
	private String salesIsStatus;
	private String userId;
	private String hallCode;
	private String concertCode;
}
