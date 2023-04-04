package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenreVO {
//	TABLE GENRE
//	
//		GENRE_CODE        VARCHAR2(100) CONSTRAINT GENRE_PK PRIMARY KEY
//		GENRE_NAME        VARCHAR2(100) NOT NULL
	
	private String genreCode;
	
	private String genreName;
}
