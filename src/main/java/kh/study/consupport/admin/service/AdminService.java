package kh.study.consupport.admin.service;

import java.util.List;

import kh.study.consupport.common.vo.ArtistVO;
import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.UsersVO;

public interface AdminService {
	
//==================================================================================================================
	
	// 아티스트 목록 조회
	List<ArtistVO> selectArtistList();
	
//==================================================================================================================
	
	// 아티스트 승격
	void updateUserRoleToArtist(UsersVO usersVO);
	
//==================================================================================================================
	
	// 아티스트 상세 조회
	ArtistVO selectArtistDetail(ArtistVO artistVO); 
	
//==================================================================================================================
	
	// 허가되지 않은 콘서트 목록 조회
	List<ConcertVO> selectConcertListDEACT();
	
	// 허가된 콘서트 목록 조회
	List<ConcertVO> selectConcertListACT();
	
//==================================================================================================================
	
	// 콘서트 허가
	void updateConcertStatus(ConcertVO concertVO);
	
//==================================================================================================================
	
	// 스페셜콘서트 조회
	List<String> selectSpecialConcert();
	
	// 스페셜콘서트 등록
	void insertSpecialConcert(ConcertVO concertVO);
	
	// 스페셜콘서트 폐기
	void deleteSpecialConcert(ConcertVO concertVO);
	
//==================================================================================================================
	
	// 그래프 띄우기 (concertCode)
	List<String> selectIncomeInfo_concertCodeList();
	
	// 그래프 띄우기 (concertName)
	List<String> selectIncomeInfo_concertNameList();
	
	// 그래프 띄우기 (총 팔린 가격)
	List<Integer> selectIncomeInfo_totalSoldAmountList();
	
	// 그래프 띄우기 (총 팔린 시트 수)
	List<Integer> selectIncomeInfo_soldSeatCntList();
	
//==================================================================================================================
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
