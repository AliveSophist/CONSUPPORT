package kh.study.consupport.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.consupport.common.vo.ArtistVO;
import kh.study.consupport.common.vo.UsersVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;

//==================================================================================================================	

	// 아티스트 목록 조회
	@Override
	public List<ArtistVO> selectArtistList() {
		return sqlSession.selectList("adminMapper.selectArtistList");
	}

//==================================================================================================================
	
	// 아티스트 승격
	@Override
	public void updateUserRoleToArtist(UsersVO usersVO) {
		sqlSession.update("adminMapper.updateUserRoleToArtist", usersVO);
	}

//==================================================================================================================
	
	// 아티스트 상세 조회
	@Override
	public ArtistVO selectArtistDetail(ArtistVO artistVO) {
		return sqlSession.selectOne("adminMapper.selectArtistDetail", artistVO);
	}

	
	
}

