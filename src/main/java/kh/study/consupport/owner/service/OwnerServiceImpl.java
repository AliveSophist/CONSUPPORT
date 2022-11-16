package kh.study.consupport.owner.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.HallDateVO;
import kh.study.consupport.common.vo.HallImgVO;
import kh.study.consupport.common.vo.HallSeatVO;
import kh.study.consupport.common.vo.HallVO;
import kh.study.consupport.common.vo.TicketVO;

@Service("ownerService")
public class OwnerServiceImpl implements OwnerService {
	
	@Autowired
	private SqlSessionTemplate sqlSession;


	@Override
	@Transactional(rollbackFor = Exception.class) //트랜잭션처리
	public int insertHall(HallVO hall) {
		
		hall.setHallCode(sqlSession.selectOne("ownerMapper.getNextHallCode"));
		
		sqlSession.insert("ownerMapper.insertHall", hall);
		sqlSession.insert("ownerMapper.insertHallDateList", hall);
		sqlSession.insert("ownerMapper.insertHallImgList", hall);
		return sqlSession.insert("ownerMapper.insertHallSeat", hall);
	}


	
}
