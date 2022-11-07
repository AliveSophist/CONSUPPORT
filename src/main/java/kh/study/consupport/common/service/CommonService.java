package kh.study.consupport.common.service;

import java.util.List;

import kh.study.consupport.common.vo.UsersVO;

public interface CommonService {
	
	UsersVO selectLoginInfo(String username);
	
}
