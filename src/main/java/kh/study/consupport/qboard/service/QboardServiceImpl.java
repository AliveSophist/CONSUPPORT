package kh.study.consupport.qboard.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.consupport.common.vo.QboardVO;

@Service("qboardService")
public class QboardServiceImpl implements QboardService{
	
	@Autowired
	SqlSessionTemplate sqlSession;

	@Override
	public void insertQboard(QboardVO qboard) {
		sqlSession.insert("qboardMapper.insertQboard", qboard);
	}
	
	
	
	
}
