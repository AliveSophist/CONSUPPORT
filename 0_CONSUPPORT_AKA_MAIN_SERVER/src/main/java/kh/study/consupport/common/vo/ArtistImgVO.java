package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtistImgVO {
//	TABLE ARTIST_IMG
//	
//		ARTIST_IMG_CODE   VARCHAR2(100) CONSTRAINT ARTIST_IMG_PK PRIMARY KEY
//		ARTIST_IMG_NAME_ORIGIN    VARCHAR2(100) NOT NULL
//		ARTIST_IMG_NAME_ATTACHED  VARCHAR2(100) NOT NULL
//		ARTIST_IMG_IS_MAIN        VARCHAR2(20)    -- (Y, null)
//		
//		USER_ID           VARCHAR2(100) NOT NULL
//		                  CONSTRAINT ARTIST_IMG_FK_REF_USER REFERENCES USERS(USER_ID)
	
	private String artistImgCode;
	
	private String artistImgNameOrigin;
	private String artistImgNameAttached;
	private String artistImgIsMain;
	private String userId;
}
