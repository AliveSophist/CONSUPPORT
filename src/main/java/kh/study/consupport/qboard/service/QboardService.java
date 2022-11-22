package kh.study.consupport.qboard.service;

import java.util.List;

import kh.study.consupport.common.vo.QboardVO;

public interface QboardService {
	
	void insertQboard(QboardVO qboard);
	
	List<QboardVO> selectQboardList(QboardVO qboard);
	
	//문의 개수 조회
	int selectQboardCnt();
	
	//문의 상세보기
	QboardVO selectDetailQboard(int qboardNum);
	
}
