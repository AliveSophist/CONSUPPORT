package kh.study.tetris.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.tetris.service.CouponService;
import kh.study.tetris.vo.CouponVO;
import kh.study.tetris.vo.UserVO;

@Controller
public class MainController {
	
	//인덱스애오 ^오^
	@GetMapping("")
	@CrossOrigin(origins = "http://localhost:8088")	// CORS 오류방지. 백섭 & 본섭 간의 이동이 가능해진다.
	public String index(HttpSession session, Authentication authentication, UserVO user) {		//@CurrentSecurityContext SecurityContext context) {
		
		user.setUserId( ((UserDetails)authentication.getPrincipal()).getUsername() );
		
		return "/content/backup_server_main";
	}
	
	
	
	
	
	
	
	
	
	
//	@GetMapping("/login")
//	public String login() {
//		
//		return "redirect:/tetris?isOpenLogin=true";
//	}
//
//	@GetMapping("/loginResult")
//	public String loginResult() {
//		
//		return "redirect:/tetris?isOpenLogin=true";
//	}
}
