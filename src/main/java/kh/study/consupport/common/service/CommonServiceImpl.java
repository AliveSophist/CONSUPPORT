package kh.study.consupport.common.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.SalesVO;
import kh.study.consupport.common.vo.TicketVO;
import kh.study.consupport.common.vo.UsersVO;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	


	@Override
	public int insertUser(UsersVO user) {
		return sqlSession.insert("commonMapper.insertUser", user);
	}
	
	@Override
	public UsersVO selectLoginInfo(String username) {
		return sqlSession.selectOne("commonMapper.selectLoginInfo", username);
	}

	@Override
	public List<TicketVO> selectTicketList(ConcertVO concert) {
		return sqlSession.selectList("commonMapper.selectTicketList", concert);
	}

	@Override
	@Transactional(rollbackFor = Exception.class) //트랜잭션처리
	public String getSalesCode(SalesVO sales) {
		
		sales.setSalesCode(sqlSession.selectOne("commonMapper.getNextSalesCode"));
		
		sqlSession.insert("commonMapper.insertSales", sales);
		sqlSession.insert("commonMapper.insertSalesDetail", sales);
		
		return sales.getSalesCode();
	}

	@Override
	@Transactional(rollbackFor = Exception.class) //트랜잭션처리
	public String tryTicketing(SalesVO sales) {
		// 받아온 ticketCode 중에 이미 팔려버린 티켓이 있다면... 넌 못사! 
		if(sales.getSalesAmount() != sqlSession.selectList("commonMapper.checkTicketingList", sales).size()) {
			sales.setSalesStatus("CANCELED");
			sqlSession.update("commonMapper.updateSales", sales);
			
			return "CANCELED";
		}
		
		// 문제없어? 그럼...!
		sales.setSalesStatus("PAID");
		sqlSession.update("commonMapper.updateSales", sales);
		sqlSession.update("commonMapper.letsTicketing", sales);
		
		return "PAID";
	}

}

