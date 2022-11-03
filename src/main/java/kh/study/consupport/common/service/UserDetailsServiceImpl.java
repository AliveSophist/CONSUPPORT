package kh.study.consupport.common.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kh.study.consupport.common.vo.UsersVO;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public UserDetails loadUserByUsername(String username) {
		
		UsersVO loginInfo = sqlSession.selectOne("commonMapper.login", username);
		
		if( loginInfo == null )
			throw new UsernameNotFoundException("아이디!!!! 제대로줘어ㅓㅓㅓㅓㅓ억!!!!");
		
		System.out.println( username + " 님 로그인");
		System.out.println( username + " 님 로그인");
		System.out.println( username + " 님 로그인");
		System.out.println( username + " 님 로그인");
		System.out.println( username + " 님 로그인");
		
		return ( User
				.withUsername(loginInfo.getUserId())
				.password(loginInfo.getUserPw())
				.roles( loginInfo.getUserRole().split(",") )
				.build());
	}
}
