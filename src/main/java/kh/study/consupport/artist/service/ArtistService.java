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
	
//========================================================================================================================================================================================
	
	// 아티스트 정보 수정
	void updateArtist(ArtistVO artistVO);
	
	

	
	//장르 리스트
	List<GenreVO> genreList();
	
	//공연 등록
	void regConcert(ConcertVO concert);
	
}
