package kh.study.consupport.artist.service;

import java.util.List;
import java.util.Map;

import kh.study.consupport.common.vo.ConcertImgVO;
import kh.study.consupport.common.vo.ConcertPriceVO;
import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.GenreVO;
import kh.study.consupport.common.vo.HallVO;

import kh.study.consupport.common.vo.ArtistVO;

public interface ArtistService {
	
	//홀 리스트
	List<HallVO> hallList();

	//홀 정보 검색
	HallVO hallInfo(String hallCode);
	
//========================================================================================================================================================================================
	
	// 아티스트 정보 수정
	void updateArtist(ArtistVO artistVO);
	

	
	//장르 리스트
	List<GenreVO> genreList();
	
	//공연 등록
	void regConcert(ConcertVO concert);
	
	
//========================================================================================================================================================================================


	// 내 공연 매출 정보 그래프로 보기
	List<Map<String, Object>> selectSoldRSA_amount(ArtistVO artist);

	// 내 공연 팔린좌석 그래프로 보기
	List<Map<String, Object>> selectSoldRSA_cnt(ArtistVO artist);



}
