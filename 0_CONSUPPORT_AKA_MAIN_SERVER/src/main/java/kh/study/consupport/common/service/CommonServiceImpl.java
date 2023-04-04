package kh.study.consupport.common.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.study.consupport.common.vo.ConcertPriceVO;
import kh.study.consupport.common.vo.ArtistVO;
import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.CouponVO;
import kh.study.consupport.common.vo.SalesVO;
import kh.study.consupport.common.vo.TicketVO;
import kh.study.consupport.common.vo.UsersVO;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	


	@Override
	public int insertUser(UsersVO user) {
		return sqlSession.insert("commonMapper.insertUser", user);
	}
	
	@Override
	public UsersVO selectLoginInfo(String username) {
		return sqlSession.selectOne("commonMapper.selectLoginInfo", username);
	}

	@Override
	public List<TicketVO> selectTicketList(ConcertVO concert) {
		return sqlSession.selectList("commonMapper.selectTicketList", concert);
	}
	
	@Override
	public ConcertPriceVO selectConcertPrice(ConcertVO concert) {
		return sqlSession.selectOne("commonMapper.selectConcertPrice", concert);
	}

	
	
	
	
	@Override
	@Transactional(rollbackFor = Exception.class) //트랜잭션처리
	public String getSalesCode(SalesVO sales) {
		
		// 받아온 ticketCode 중에 이미 팔려버린 티켓이 있다면... 넌 못사! 
		// 받아온 ticketCode 중에 '결제 대기 상태(WAIT)'인 TICKET이 있다면... 넌 못사!
		if( sales.getSalesAmount() != sqlSession.selectList("commonMapper.checkTicketingList", sales).size()
			|| sqlSession.selectList("commonMapper.isExistWaitStatus", sales).size() > 0) {
			
			System.out.println("넌 못사!넌 못사!넌 못사!넌 못사!넌 못사!");
			
			return null;
		}
		
		sales.setSalesCode(sqlSession.selectOne("commonMapper.getNextSalesCode"));
		
		sqlSession.insert("commonMapper.insertSales", sales);
		sqlSession.insert("commonMapper.insertSalesDetail", sales);
		
		return sales.getSalesCode();
	}

	@Override
	@Transactional(rollbackFor = Exception.class) //트랜잭션처리
	public String tryTicketing(SalesVO sales) {
		
		// 받아온 ticketCode 중에 이미 팔려버린 티켓이 있다면... 넌 못사!
		// 사실상 중복 검사기는 한데.. 이미 구현해 놓은 기능이라.
		if(sales.getSalesAmount() != sqlSession.selectList("commonMapper.checkTicketingList", sales).size()) {
			sales.setSalesStatus("CANCELED");
			sqlSession.update("commonMapper.updateSales", sales);
			
			return "CANCELED";
		}
		
		// 문제없어? 그럼...!
		sales.setSalesStatus("PAID");
		sqlSession.update("commonMapper.updateSales", sales);
		sqlSession.update("commonMapper.useCoupon", sales);		
		sqlSession.update("commonMapper.letsTicketing", sales);
		
		return "PAID";
	}

	@Override
	public void cancelWhenPaying(SalesVO sales) {
		// 브라우저 새로고침, 종료, 기타여러가지 문제... 발생 시 취소.
		sales.setSalesStatus("CANCELED");
		sqlSession.update("commonMapper.updateSales", sales);
	}

	@Override
	@Transactional(rollbackFor = Exception.class) //트랜잭션처리
	public void refundAll(SalesVO sales) {

		sales.setSalesStatus("REFUNDED");
		sqlSession.update("commonMapper.updateSales", sales);
		
		sqlSession.update("commonMapper.refundAll", sales);
		
	}

	@Override
	public CouponVO selectCoupon(String couponCode) {
		return sqlSession.selectOne("commonMapper.selectCoupon", couponCode);
	}

	@Override
	public int getUserAge(String userId) {
		
		int userBirth = sqlSession.selectOne("commonMapper.getUserAge", userId);
		
		int birthY = userBirth/10000;
		int birthM = (userBirth%10000)/100;
		int birthD = userBirth%100;
		
		//어쩌구저쩌구 날짜계산
		LocalDate today = LocalDate.now();
		int todayYear = today.getYear();
		int todayMonth = today.getMonthValue();
		int todayDay = today.getDayOfMonth();

		// 올해 - 태어난년도
		int americanAge = todayYear - birthY;

		// 생일이 안지났으면 - 1
		if (birthM > todayMonth) {
			americanAge--;
		} else if (birthM == todayMonth) {
			if (birthD > todayDay) {
				americanAge--;
			}
		}

		return americanAge;
	}
	
	
	
	
	
	
	
//===================================================================================================================================================================================================================


	// 메인화면 콘서트 목록 조회
	@Override
	public List<ConcertVO> selectConcertListOfCommon(int pageNum) {
		return sqlSession.selectList("commonMapper.selectConcertListOfCommon", pageNum);
	}
	
	
	// 콘서트 상세 조회
	@Override
	public ConcertVO selectConcertDetail(ConcertVO concertVO) {
		return sqlSession.selectOne("commonMapper.selectConcertDetail", concertVO);
	}
	
	// 아티스트 상세 조회
	@Override
	public ArtistVO selectArtistDetail(ArtistVO artistVO) {
		return sqlSession.selectOne("adminMapper.selectArtistDetail", artistVO);
	}

	// 메인화면 스페셜콘서트 목록 조회
	@Override
	public List<String> selectSpecialConcertListOfCommon() {
		return sqlSession.selectList("commonMapper.selectSpecialConcertListOfCommon");
	}
	
	// 콘서트 검색
	@Override
	public List<ConcertVO> serchConcert(Map<String, String> map) {
		return sqlSession.selectList("commonMapper.serchConcert", map);
	}
	
	
	
	
	
	
	
	
	// 캘린더 조회
	@Override
	public List<ConcertVO> selectConcertListForCalendar() {
		return sqlSession.selectList("commonMapper.selectConcertListForCalendar");
	}


	
	
	
	
	
}

