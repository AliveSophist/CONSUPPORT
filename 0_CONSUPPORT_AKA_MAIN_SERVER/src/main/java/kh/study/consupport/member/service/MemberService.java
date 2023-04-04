package kh.study.consupport.member.service;

import java.util.List;

import kh.study.consupport.common.vo.ArtistVO;
import kh.study.consupport.common.vo.SalesVO;
import kh.study.consupport.common.vo.UsersVO;

public interface MemberService {
	
//===============================================================================================================================================================

	// 아티스트 등록
	void insertArtist(ArtistVO artistVO);
	
//===============================================================================================================================================================

	// 비밀번호 확인
	String selectPw(String userId);

//===============================================================================================================================================================

	
	//내 정보 수정 화면 이동
	UsersVO editInfoForm(String userId);
	
	//내 정보 수정
	void updateEditInfo(UsersVO users);
	
//===============================================================================================================================================================

	// 마이페이지 구매 정보 조회
	List<SalesVO> selectMypageSalesInfo(UsersVO users);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
