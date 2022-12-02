package kh.study.consupport.common.service;

import java.util.List;
import java.util.Map;

import kh.study.consupport.common.vo.ConcertPriceVO;
import kh.study.consupport.common.vo.ArtistVO;
import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.CouponVO;
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
	CouponVO selectCoupon(String couponCode);
	
	int getUserAge(String userId);
	

	// 메인화면 콘서트 목록 조회
	List<ConcertVO> selectConcertListOfCommon(int pageNum);
	
	// 콘서트 상세 조회
	ConcertVO selectConcertDetail(ConcertVO concertVO);
	
	// 아티스트 상세 조회
	ArtistVO selectArtistDetail(ArtistVO artistVO); 
	
	// 메인화면 스페셜콘서트 목록 조회
	List<String> selectSpecialConcertListOfCommon();
	
	// 콘서트 검색
	List<ConcertVO> serchConcert(Map<String, String> map);

	// 캘린더 조회
	List<ConcertVO> selectConcertListForCalendar();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
