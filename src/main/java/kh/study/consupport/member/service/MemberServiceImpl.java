package kh.study.consupport.member.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.study.consupport.common.vo.ArtistVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;

//===============================================================================================================================================================
	
	// 아티스트 등록
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertArtist(ArtistVO artistVO) {
		sqlSession.insert("memberMapper.insertArtist", artistVO);
		sqlSession.insert("memberMapper.insertArtistImgs", artistVO);
	}

	
	
}

