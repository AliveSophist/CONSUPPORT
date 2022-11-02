package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersVO {
//	TABLE USERS
//
//		USER_ID           VARCHAR2(100) CONSTRAINT USER_PK PRIMARY KEY
//		USER_PW           VARCHAR2(100) NOT NULL
//		USER_NAME         VARCHAR2(100) NOT NULL
//		USER_ADDR         VARCHAR2(100) NOT NULL
//		USER_ADDR_DETAIL  VARCHAR2(100)
//		USER_MAIL         VARCHAR2(100)
//		USER_BIRTH        VARCHAR2(100) NOT NULL
//		USER_TELL         VARCHAR2(100) NOT NULL
//		USER_MILEAGE      NUMBER DEFAULT 0
//		
//		USER_ROLE         VARCHAR2(20) DEFAULT 'MEMBER' NOT NULL     -- (MEMBER, ARTIST, OWNER, ADMIN)
//		USER_STATUS       VARCHAR2(20) DEFAULT 'ACT' NOT NULL        -- (ACT, DEACT)

	private String userId;
	
	private String userPw;
	private String userName;
	private String userAddr;
	private String userAddrDetail;
	private String userMail;
	private String userBirth;
	private String userTell;
	private int userMileage;
	
	private String userRole;
	private String userStatus;
}
