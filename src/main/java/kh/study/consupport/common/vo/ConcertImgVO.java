package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConcertImgVO {
//	TABLE CONCERT_IMG
//	
//	    CONCERT_IMG_CODE  VARCHAR2(100) CONSTRAINT CONCERT_IMG_PK PRIMARY KEY
//	    CONCERT_IMG_NAME_ORIGIN   VARCHAR2(100) NOT NULL
//	    CONCERT_IMG_NAME_ATTACHED VARCHAR2(100) NOT NULL
//	    CONCERT_IMG_IS_MAIN       VARCHAR2(20)    -- (Y, null)
//	    
//	    CONCERT_CODE      VARCHAR2(100) NOT NULL
//	                      CONSTRAINT CONCERT_IMG_FK_REF_CONCERT REFERENCES CONCERT(CONCERT_CODE)
	
	private String concertImgCode;
	
	private String concertImgNameOrigin;
	private String concertImgNameAttached;
	private String concertImgIsMain;
	private String concertCode;
}
