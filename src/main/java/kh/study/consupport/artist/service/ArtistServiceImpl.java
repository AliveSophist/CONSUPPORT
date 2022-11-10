package kh.study.consupport.artist.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.consupport.common.vo.ArtistVO;

@Service("artistService")
public class ArtistServiceImpl implements ArtistService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;


//==================================================================================================================
	
	// 아티스트 정보 수정 
	@Override
	public void updateArtist(ArtistVO artistVO) {
		sqlSession.update("artistMapper.updateArtist", artistVO);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

