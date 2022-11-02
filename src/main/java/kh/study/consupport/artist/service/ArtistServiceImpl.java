package kh.study.consupport.artist.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("artistService")
public class ArtistServiceImpl implements ArtistService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	
	
}

