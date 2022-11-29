package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QboardVO extends PageVO{
	
//    QBOARD_NUM VARCHAR2(100) CONSTRAINT Q_BOARD_PK PRIMARY KEY
//    , QBOARD_TITLE VARCHAR2(100) NOT NULL
//    , QBOARD_CONTENT VARCHAR2(100) NOT NULL
//    , USER_ID VARCHAR2(30) CONSTRAINT Q_BOARD_FK
//        REFERENCES USERS (USER_ID) 
//    , QBOARD_CREATE_DATE DATE DEFAULT SYSDATE
//    , Q_SECRET VARCHAR2(50)
	
	private int qboardNum;
	private String qboardTitle;
	private String qboardContent;
	private String userId;
	private String qboardCreateDate;
	private String qsecret;		// 'T', 'F'
	
	
}
