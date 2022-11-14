package kh.study.consupport.admin.service;

import java.util.List;

import kh.study.consupport.common.vo.ArtistVO;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
