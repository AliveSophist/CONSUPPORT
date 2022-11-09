package kh.study.consupport.artist.service;

import java.util.List;

import javax.transaction.Transactional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.consupport.common.vo.ConcertImgVO;
import kh.study.consupport.common.vo.ConcertPriceVO;
import kh.study.consupport.common.vo.ConcertVO;
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
	
	@org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class) //트랜잭션처리
	@Override
	public void regConcert(ConcertVO concert) {
		
		concert.setConcertCode(sqlSession.selectOne("artistMapper.concertCode", concert));
		
		sqlSession.insert("artistMapper.regConcert", concert);
		sqlSession.insert("artistMapper.insertConcertPrice", concert);
		sqlSession.insert("artistMapper.insertConcertImg", concert);
		
	}




	
	
}

