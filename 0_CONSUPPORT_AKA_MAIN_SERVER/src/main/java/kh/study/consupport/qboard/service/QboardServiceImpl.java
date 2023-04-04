package kh.study.consupport.qboard.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.study.consupport.common.vo.AboardVO;
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

	//문의 개수 조회
	@Override
	public int selectQboardCnt() {
		return sqlSession.selectOne("qboardMapper.selectQboardCnt");
	}

	//문의 상세보기
	@Override
	public QboardVO selectDetailQboard(int qboardNum) {
		return sqlSession.selectOne("qboardMapper.selectDetailQboard", qboardNum);
	}
	
	//문의 수정
	@Override
	public void updateQboard(QboardVO qboard) {
		sqlSession.update("qboardMapper.updateQboard", qboard);
	}
	
	//문의 삭제
	@Transactional
	@Override
	public void deleteQboard(int qboardNum) {
		sqlSession.delete("qboardMapper.deleteAboard", qboardNum);
		sqlSession.delete("qboardMapper.deleteQboard", qboardNum);
	}

	//문의 응답
	@Override
	public void insertAboard(AboardVO aboard) {
		sqlSession.insert("qboardMapper.insertAboard", aboard);
	}

	//응답 조회
	@Override
	public List<AboardVO> selectAboardList(int qboardNum) {
		return sqlSession.selectList("qboardMapper.selectAboard", qboardNum);
	}

	
	
	
	
	
}
