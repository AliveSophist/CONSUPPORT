package kh.study.consupport.common.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kh.study.consupport.common.constant.UserRole;
import kh.study.consupport.common.constant.UserStatus;
import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.TicketVO;
import kh.study.consupport.common.vo.UsersVO;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	


	@Override
	public int insertUser(UsersVO user) {
		return sqlSession.insert("commonMapper.insertUser", user);
	}
	
	@Override
	public UsersVO selectLoginInfo(String username) {
		return sqlSession.selectOne("commonMapper.selectLoginInfo", username);
	}

	@Override
	public List<TicketVO> selectTicketList() {
		return sqlSession.selectList("commonMapper.selectTicketList");
	}
	
//==================================================================================================================================================================================================================	
	
	// 메인화면 콘서트 목록 조회
	@Override
	public List<ConcertVO> selectConcertListOfCommon() {
		return sqlSession.selectList("commonMapper.selectConcertListOfCommon");
	}

}

