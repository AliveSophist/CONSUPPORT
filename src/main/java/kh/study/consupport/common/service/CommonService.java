package kh.study.consupport.common.service;

import java.util.List;

import kh.study.consupport.common.vo.TicketVO;
import kh.study.consupport.common.vo.UsersVO;

public interface CommonService {
	
	int insertUser(UsersVO user);
	
	UsersVO selectLoginInfo(String username);
	
	List<TicketVO> selectTicketList(); 
}
