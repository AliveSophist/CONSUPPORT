package kh.study.consupport.artist.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.consupport.common.vo.GenreVO;
import kh.study.consupport.common.vo.HallVO;

@Service("artistService")
public class ArtistServiceImpl implements ArtistService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<HallVO> hallList() {
		
		return sqlSession.selectList("artistMapper.hallList");
	}

	@Override
	public List<GenreVO> genreList() {
		
		return sqlSession.selectList("artistMapper.genreList");
	}

	
	
}

