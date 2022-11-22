package kh.study.consupport.qboard.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.consupport.common.vo.QboardVO;

@Service("qboardService")
public class QboardServiceImpl implements QboardService{
	
	@Autowired
	SqlSessionTemplate sqlSession;

	//문의글 생성
	@Override
	public void insertQboard(QboardVO qboard) {
		sqlSession.insert("qboardMapper.insertQboard", qboard);
	}
	
	//문의 리스트 창
	@Override
	public List<QboardVO> selectQboardList(QboardVO qboard) {
		return sqlSession.selectList("qboardMapper.selectQboardList", qboard);
	}
	
	
	
	
}
