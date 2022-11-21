package kh.study.consupport.common.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConcertVO {
//	TABLE CONCERT
//	
//		CONCERT_CODE      VARCHAR2(100) CONSTRAINT CONCERT_PK PRIMARY KEY
//		CONCERT_NAME      VARCHAR2(100) NOT NULL
//		CONCERT_STATUS    VARCHAR2(20) DEFAULT 'DEACT' NOT NULL        -- (ACT, DEACT)
//		CONCERT_RATED     VARCHAR2(20) DEFAULT 'ALL' NOT NULL        -- (ALL, 15+, 19+)
//		
//		CONCERT_TICKETING_DATE    DATE
//		
//		HALL_DATE_CODE    VARCHAR2(100) NOT NULL	
//		GENRE_CODE        VARCHAR2(100) NOT NULL
//		                  CONSTRAINT CONCERT_FK_REF_GENRE REFERENCES GENRE(GENRE_CODE)
//		HALL_CODE         VARCHAR2(100) NOT NULL
//		                  CONSTRAINT CONCERT_FK_REF_HALL REFERENCES HALL(HALL_CODE)
//		USER_ID           VARCHAR2(100) NOT NULL
//		                  CONSTRAINT CONCERT_FK_REF_USER REFERENCES USERS(USER_ID)
	
	private String concertCode;
	
	private String concertName;
	private String concertStatus;
	private String concertRated;
	private String concertTicketingDate;
	private String genreCode;
	private String hallCode;
	private String userId;
	private String hallDateCode;
	
	

	private String concertDate;
	
	//private HallVO hallVO;
	
	// A 가격 설정
	private ConcertPriceVO concertPrice;
	
	// A 아티스트
	private ArtistVO artist;
	
	
	// C 콘서트 이미지
	private List<ConcertImgVO> concertImgList;
}
