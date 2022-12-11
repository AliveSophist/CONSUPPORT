package kh.study.consupport.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.study.consupport.common.vo.ArtistVO;
import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.HallVO;
import kh.study.consupport.common.vo.TicketVO;
import kh.study.consupport.common.vo.UsersVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;

//==================================================================================================================	

	// 아티스트 목록 조회
	@Override
	public List<ArtistVO> selectArtistList() {
		return sqlSession.selectList("adminMapper.selectArtistList");
	}

//==================================================================================================================
	
	// 아티스트 승격
	@Override
	public void updateUserRoleToArtist(UsersVO usersVO) {
		sqlSession.update("adminMapper.updateUserRoleToArtist", usersVO);
	}

//==================================================================================================================
	
	// 아티스트 상세 조회
	@Override
	public ArtistVO selectArtistDetail(ArtistVO artistVO) {
		return sqlSession.selectOne("adminMapper.selectArtistDetail", artistVO);
	}
	
//==================================================================================================================
	
	// 허가되지 않은 콘서트 목록 조회
	@Override
	public List<ConcertVO> selectConcertListDEACT() {
		return sqlSession.selectList("adminMapper.selectConcertListDEACT");
	}
	
	// 허가된 콘서트 목록 조회
	@Override
	public List<ConcertVO> selectConcertListACT() {
		return sqlSession.selectList("adminMapper.selectConcertListACT");
	}

//==================================================================================================================

	// 스페셜콘서트 조회
	@Override
	public List<String> selectSpecialConcert() {
		return sqlSession.selectList("adminMapper.selectSpecialConcert");
	}
	
	// 스페셜콘서트 등록
	@Override
	public void insertSpecialConcert(ConcertVO concertVO) {
		sqlSession.insert("adminMapper.insertSpecialConcert", concertVO);
	}
	
	// 스페셜콘서트 폐기
	@Override
	public void deleteSpecialConcert(ConcertVO concertVO) {
		sqlSession.insert("adminMapper.deleteSpecialConcert", concertVO);
	}
	
	
//==================================================================================================================
	
	// 콘서트 허가
	@Override
	@Transactional(rollbackFor = Exception.class) //트랜잭션처리
	public void updateConcertStatus(ConcertVO concertVO) {
		sqlSession.update("adminMapper.updateConcertStatus", concertVO);
		
		HallVO hall = sqlSession.selectOne("adminMapper.selectHall", concertVO);
		
		TicketVO ticket = new TicketVO();
		ticket.setHallCode(hall.getHallCode());
		ticket.setConcertCode(concertVO.getConcertCode());
		
		int hallSeatCnt = hall.getHallSeatCnt();
		List<String> seatCodeList = new ArrayList<>();
		switch(hallSeatCnt) {
			case 40:
			{
				for(int i=0; i< hallSeatCnt; i++) {
					int seatNum = i+1;
					
					if		( 3 <= seatNum && seatNum <=  6)
						seatCodeList.add(String.format("R_%03d", seatNum));
					else if	(11 <= seatNum && seatNum <= 14)
						seatCodeList.add(String.format("R_%03d", seatNum));
					
					else if	(seatNum <= 16)
						seatCodeList.add(String.format("S_%03d", seatNum));
					
					else
						seatCodeList.add(String.format("A_%03d", seatNum));
				}
			}
			break;
			
			
			
			
			
			
			case 70:
			{
				for(int i=0; i< hallSeatCnt; i++)  {
					int seatNum = i+1;
					
					if		( 3 <= seatNum && seatNum <=  8)
						seatCodeList.add(String.format("R_%03d", seatNum));
					else if	(13 <= seatNum && seatNum <= 18)
						seatCodeList.add(String.format("R_%03d", seatNum));
					else if	(24 <= seatNum && seatNum <= 27)
						seatCodeList.add(String.format("R_%03d", seatNum));
					
					else if	(seatNum <= 20)
						seatCodeList.add(String.format("S_%03d", seatNum));
					else if	(22 <= seatNum && seatNum <= 29)
						seatCodeList.add(String.format("S_%03d", seatNum));
					else if	(33 <= seatNum && seatNum <= 38)
						seatCodeList.add(String.format("S_%03d", seatNum));
					
					else
						seatCodeList.add(String.format("A_%03d", seatNum));
				}
			}
			break;
			
			
			
			
			
			
			case 100:
			{
				for(int i=0; i< hallSeatCnt; i++) {
					int seatNum = i+1;
					
					if		(seatNum <= 24)
						seatCodeList.add(String.format("R_%03d", seatNum));
					
					else if	(seatNum <= 48)
						seatCodeList.add(String.format("S_%03d", seatNum));
					
					else
						seatCodeList.add(String.format("A_%03d", seatNum));
				}
			}
		}
		
		ticket.setSeatCodeList(seatCodeList);
		
		sqlSession.insert("adminMapper.insertTickets", ticket);
	}

	
	
	
	
	
	@Override
	public List<UsersVO> selectOwnerList() {
		return sqlSession.selectList("adminMapper.selectOwnerList");
	}

	@Override
	public boolean updateUserRoleAboutOwner(UsersVO user) {
		int result = sqlSession.update("adminMapper.updateUserRoleAboutOwner", user);
		
		if(result > 0)
			return true;
		else
			return false;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

