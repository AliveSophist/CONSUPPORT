package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConcertPriceVO {
//	TABLE CONCERT_PRICE
//	
//		-- {Non PK Only FK Table}
//		CONCERT_CODE      VARCHAR2(100) NOT NULL
//		                  CONSTRAINT CONCERT_PRICE_FK_REF_CONCERT REFERENCES CONCERT(CONCERT_CODE)
//		CONCERT_PRICE_R   NUMBER
//		CONCERT_PRICE_S   NUMBER
//		CONCERT_PRICE_A   NUMBER
	
	private String concertCode;
	
	private int concertPriceR;
	private int concertPriceS;
	private int concertPriceA;
}
