package kh.study.consupport.common.service;

import java.util.List;

import kh.study.consupport.common.vo.ConcertPriceVO;
import kh.study.consupport.common.vo.ArtistVO;
import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.SalesVO;
import kh.study.consupport.common.vo.TicketVO;
import kh.study.consupport.common.vo.UsersVO;

public interface CommonService {
	
	int insertUser(UsersVO user);
	
	UsersVO selectLoginInfo(String username);
	
	
	
	// 티켓 및 결제 기능
	ConcertPriceVO selectConcertPrice(ConcertVO concert);
	List<TicketVO> selectTicketList(ConcertVO concert);
	String getSalesCode(SalesVO sales);
	String tryTicketing(SalesVO sales);
	void cancelWhenPaying(SalesVO sales);
	void refundAll(SalesVO sales);
	

	// 메인화면 콘서트 목록 조회
	List<ConcertVO> selectConcertListOfCommon();
	
	// 콘서트 상세 조회
	ConcertVO selectConcertDetail(ConcertVO concertVO);
	
	// 아티스트 상세 조회
	ArtistVO selectArtistDetail(ArtistVO artistVO); 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
