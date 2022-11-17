package kh.study.consupport.common.service;

import java.util.List;

import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.SalesVO;
import kh.study.consupport.common.vo.TicketVO;
import kh.study.consupport.common.vo.UsersVO;

public interface CommonService {
	
	int insertUser(UsersVO user);
	
	UsersVO selectLoginInfo(String username);
	
	List<TicketVO> selectTicketList(ConcertVO concert);
	
	String getSalesCode(SalesVO sales);
	
	String tryTicketing(SalesVO sales);
}
