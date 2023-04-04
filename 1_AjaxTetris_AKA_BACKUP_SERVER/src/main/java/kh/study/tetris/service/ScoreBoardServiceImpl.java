package kh.study.tetris.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("scoreService")
public class ScoreBoardServiceImpl implements ScoreBoardService{
	@Autowired
	SqlSessionTemplate sqlSession;// = sqlSessionTemplate()

}
