package kh.study.consupport.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AboardVO{
	
//    QBOARD_NUM              NUMBER NOT NULL
//    CONSTRAINT ABOARD_FK_REF_QBOARD REFERENCES QBOARD(QBOARD_NUM)
//, ANSWER                VARCHAR2(100) NOT NULL
//, USER_ID               VARCHAR2(100) NOT NULL
//    CONSTRAINT ABOARD_FK_REF_USER REFERENCES USERS(USER_ID)
//, ANSWER_DATE           DATE DEFAULT SYSDATE
	
	private int qboardNum;
	private String answer;
	private String userId;
	private String answerDate;
	
	
}
