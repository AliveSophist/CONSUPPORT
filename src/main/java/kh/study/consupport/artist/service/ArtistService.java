package kh.study.consupport.artist.service;

import java.util.List;

import kh.study.consupport.common.vo.GenreVO;
import kh.study.consupport.common.vo.HallVO;

public interface ArtistService {
	
	//홀 리스트
	List<HallVO> hallList();
	
	//장르 리스트
	List<GenreVO> genreList();
	
}
