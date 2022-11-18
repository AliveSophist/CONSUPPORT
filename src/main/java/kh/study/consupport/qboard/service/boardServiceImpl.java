package kh.study.consupport.qboard.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("qboardService")
public class boardServiceImpl {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	
}
