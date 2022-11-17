package kh.study.consupport.common.service;

import java.util.List;

import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.TicketVO;
import kh.study.consupport.common.vo.UsersVO;

public interface CommonService {
	
	int insertUser(UsersVO user);
	
	UsersVO selectLoginInfo(String username);
	
	List<TicketVO> selectTicketList(); 
	
//==================================================================================================================================================================================================================
	
	// 메인화면 콘서트 목록 조회
	List<ConcertVO> selectConcertListOfCommon();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
