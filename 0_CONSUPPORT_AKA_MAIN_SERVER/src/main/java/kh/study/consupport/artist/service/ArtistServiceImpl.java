package kh.study.consupport.artist.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.GenreVO;
import kh.study.consupport.common.vo.HallVO;

import kh.study.consupport.common.vo.ArtistVO;


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
	
	//홀정보 불러오기
	@Override
	public HallVO hallInfo(String hallCode) {
		return sqlSession.selectOne("artistMapper.hallInfo", hallCode);
	}

	//공연 등록
	@Transactional(rollbackFor = Exception.class) //트랜잭션처리
	@Override
	public void regConcert(ConcertVO concert) {
		concert.setConcertCode(sqlSession.selectOne("artistMapper.concertCode", concert));
		System.out.println(concert);
		
		sqlSession.insert("artistMapper.regConcert", concert);
		sqlSession.insert("artistMapper.insertConcertPrice", concert);
		sqlSession.insert("artistMapper.insertConcertImg", concert);
		sqlSession.update("artistMapper.hallStatusUpdate", concert);
		
	}
	

//==================================================================================================================
	
	// 아티스트 정보 수정 
	@Override
	public void updateArtist(ArtistVO artistVO) {
		sqlSession.update("artistMapper.updateArtist", artistVO);
	}
	
	
//==================================================================================================================


	// 내 공연 매출 정보 그래프로 보기
	@Override
	public List<Map<String, Object>> selectSoldRSA_amount(ArtistVO artist) {
		return sqlSession.selectList("artistMapper.selectSoldRSA_amount", artist);
	}

	// 내 공연 팔린좌석 그래프로 보기
	@Override
	public List<Map<String, Object>> selectSoldRSA_cnt(ArtistVO artist) {
		return sqlSession.selectList("artistMapper.selectSoldRSA_cnt", artist);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

