package kh.study.consupport.common.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtistVO {
//	TABLE ARTIST
//	
//		-- {Non PK Only FK Table}
//		USER_ID           VARCHAR2(100) NOT NULL
//		                  CONSTRAINT ARTIST_FK_REF_USER REFERENCES USERS(USER_ID)
//		ARTIST_NAME       VARCHAR2(100) NOT NULL
//		ARTIST_DETAIL     VARCHAR2(500) NOT NULL
	
	private String userId;
	
	private String artistName;
	private String artistDetail;
	
	private ArtistImgVO artistImg;
	private List<ArtistImgVO> artistImgList;
	private UsersVO users;
}
