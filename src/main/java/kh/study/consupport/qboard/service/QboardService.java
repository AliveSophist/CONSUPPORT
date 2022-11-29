package kh.study.consupport.qboard.service;

import java.util.List;

import kh.study.consupport.common.vo.AboardVO;
import kh.study.consupport.common.vo.QboardVO;

public interface QboardService {
	
	//문의 등록
	void insertQboard(QboardVO qboard);
	
	//문의 리스트
	List<QboardVO> selectQboardList(QboardVO qboard);
	
	//문의 개수 조회
	int selectQboardCnt();
	
	//문의 상세보기
	QboardVO selectDetailQboard(int qboardNum);
	
	//문의 수정 
	void updateQboard(QboardVO qboard);
	
	//문의 삭제
	void deleteQboard(int qboardNum);
	
	//문의 응답
	void insertAboard(AboardVO aboard);

	//응답 조회
	List<AboardVO> selectAboardList(int qboardNum);
	
}
