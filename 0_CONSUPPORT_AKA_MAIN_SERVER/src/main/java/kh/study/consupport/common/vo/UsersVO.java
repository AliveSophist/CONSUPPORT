package kh.study.consupport.common.vo;

import java.util.HashMap;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
//		USER_BIRTH        VARCHAR2(100) NOT NULL
//		USER_TELL         VARCHAR2(100) NOT NULL
//	
//		USER_MILEAGE      NUMBER DEFAULT 0
//		USER_ROLE         VARCHAR2(20) DEFAULT 'MEMBER' NOT NULL     -- (MEMBER, ARTIST, OWNER, ADMIN)
//		USER_STATUS       VARCHAR2(20) DEFAULT 'ACT' NOT NULL        -- (ACT, DEACT)
	
	@NotBlank(message = "아이디는 필수 입력 사항입니다.")
	@Email(message = "아이디의 이메일 형식이 올바르지 않습니다.")
	private String userId;
	
	@Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9]).{6,16}$", message = "비밀번호는 영문 대소문자, 숫자를 포함한 6~16자리여야 합니다.")
	private String userPw;
	
	@Pattern(regexp = "[0-9a-zA-Zㄱ-ㅎ가-힣]{3,10}$", message = "닉네임은 특수문자를 제외한 3~10자리여야 합니다.")
	private String userName;

	private String userAddr;
	private String userAddrDetail;
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
		hash.put("userBirth", this.userBirth);
		hash.put("userTell", this.userTell);
		hash.put("userMileage", this.userMileage+"");
		hash.put("userRole", this.userRole);
		hash.put("userStatus", this.userStatus);
		
		return hash;
	}
}
