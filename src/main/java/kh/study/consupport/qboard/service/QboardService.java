package kh.study.consupport.qboard.service;

import java.util.List;

import kh.study.consupport.common.vo.QboardVO;

public interface QboardService {
	
	void insertQboard(QboardVO qboard);
	
	List<QboardVO> selectQboardList(QboardVO qboard);
}
