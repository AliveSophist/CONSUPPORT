package kh.study.consupport.common.vo;

import java.util.HashMap;

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
//		USER_EMAIL         VARCHAR2(100)
//		USER_BIRTH        VARCHAR2(100) NOT NULL
//		USER_TELL         VARCHAR2(100) NOT NULL
//	
//		USER_MILEAGE      NUMBER DEFAULT 0
//		USER_ROLE         VARCHAR2(20) DEFAULT 'MEMBER' NOT NULL     -- (MEMBER, ARTIST, OWNER, ADMIN)
//		USER_STATUS       VARCHAR2(20) DEFAULT 'ACT' NOT NULL        -- (ACT, DEACT)

	private String userId;
	
	private String userPw;
	private String userName;
	private String userAddr;
	private String userAddrDetail;
	private String userEmail;
	private String userBirth;
	private String userTell;
	
	private int userMileage;
	private String userRole;
	private String userStatus;
	
	public HashMap<String, String> toHash() {

		HashMap<String, String> hash = new HashMap<>();
		hash.put("userId", this.userId);
		hash.put("userPw", this.userPw);
		hash.put("userName", this.userName);
		hash.put("userAddr", this.userAddr);
		hash.put("userAddrDetail", this.userAddrDetail);
		hash.put("userEmail", this.userEmail);
		hash.put("userBirth", this.userBirth);
		hash.put("userTell", this.userTell);
		hash.put("userMileage", this.userMileage+"");
		hash.put("userRole", this.userRole);
		hash.put("userStatus", this.userStatus);
		
		return hash;
	}
}
