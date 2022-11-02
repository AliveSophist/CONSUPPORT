package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HallImgVO {
//	TABLE HALL_IMG
//
//		HALL_IMG_CODE     VARCHAR2(100) CONSTRAINT HALL_IMG_PK PRIMARY KEY
//		HALL_IMG_NAME_ORIGIN      VARCHAR2(100) NOT NULL
//		HALL_IMG_NAME_ATTACHED    VARCHAR2(100) NOT NULL
//		HALL_IMG_IS_MAIN          VARCHAR2(20)    -- (Y, null)
//		
//		HALL_CODE         VARCHAR2(100) NOT NULL
//		                  CONSTRAINT HALL_IMG_FK_REF_HALL REFERENCES HALL(HALL_CODE)
	
	private String hallImgCode;
	
	private String hallImgNameOrigin;
	private String hallImgNameAttached;
	private String hallImgIsMain;
	private String hallCode;
}
