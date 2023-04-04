package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CouponVO {
//	TABLE COUPON
//	
//	    COUPON_CODE       VARCHAR2(100) CONSTRAINT COUPON_PK PRIMARY KEY
//		COUPON_VALUE      NUMBER NOT NULL
//		COUPON_STATUS     VARCHAR2(20) DEFAULT 'UNUSED' NOT NULL        -- (USED, UNUSED)
//		
//		USER_ID           VARCHAR2(100)
//		                  CONSTRAINT COUPON_FK_REF_USER REFERENCES USERS(USER_ID)
//		SALES_CODE        VARCHAR2(100)
//		                  CONSTRAINT COUPON_FK_REF_SALES REFERENCES SALES(SALES_CODE)
	
	private String couponCode;
	
	private int couponValue;
	private String couponStatus;
	private String userId;
	private String salesCode;
}
