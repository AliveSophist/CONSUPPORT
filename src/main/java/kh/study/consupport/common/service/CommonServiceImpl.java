package kh.study.consupport.common.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kh.study.consupport.common.vo.UsersVO;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	@Override
	public UsersVO selectLoginInfo(String username) {
		return sqlSession.selectOne("commonMapper.selectLoginInfo", username);
	}
	
//	@Override
//	public boolean insertMember(MemberVO member) {
//
//		member.setMemberStatus(MemberStatus.ACTIVE.toString());
//		member.setMemberRole(MemberRole.MEMBER.toString());
//		
//		//시큐리티
//		member.setMemberPw(  passwordEncoder.encode(member.getMemberPw())  );
//		
//		int result = sqlSession.insert("memberMapper.insertMember", member);
//		
//		if(result>0)
//			return true;
//		else
//			return false;
//	}
}

