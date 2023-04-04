package kh.study.tetris.service;

import java.util.UUID;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.study.tetris.vo.CouponVO;

@Service("couponService")
public class CouponServiceImpl implements CouponService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	
	
	@Override
	@Transactional
	public CouponVO insertCoupon(int discountRate) {
		CouponVO coupon = new CouponVO();
		
		String couponCode = UUID.randomUUID().toString().toUpperCase();
		
		while( ((int)sqlSession.selectOne("couponMapper.checkDuplicated", couponCode)) > 0 ) {
			couponCode = UUID.randomUUID().toString().toUpperCase();
		}
		
		coupon.setCouponCode( couponCode );
		coupon.setCouponValue( discountRate );
		
		sqlSession.insert("couponMapper.insertCoupon", coupon);
		
		return coupon;
	}
	
	
	
	
	
}

