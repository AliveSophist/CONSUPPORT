package kh.study.consupport.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import kh.study.consupport.admin.service.AdminService;
import kh.study.consupport.artist.service.ArtistService;
import kh.study.consupport.common.config.SecurityConfig;
import kh.study.consupport.common.service.CommonService;
import kh.study.consupport.common.vo.UsersVO;
import kh.study.consupport.member.service.MemberService;
import kh.study.consupport.owner.service.OwnerService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Controller
public class CommonController {
	
	@Autowired
	private SecurityConfig securityConfig;

	@Resource(name = "adminService")
	private AdminService adminService;

	@Resource(name = "artistService")
	private ArtistService artistService;

	@Resource(name = "commonService")
	private CommonService commonService;

	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "ownerService")
	private OwnerService ownerService;

	
	@Data
	class PageDefaultValues {
		// 로그인 및 비회원이 이용하는 서비스에 필요한 변수들..
		boolean openLogin = false;
	}
	@ModelAttribute
	public void putModelAttribute( PageDefaultValues pdv, Model model) {
		System.out.println("openLogin : " + pdv.isOpenLogin());
		System.out.println("openLogin : " + pdv.isOpenLogin());
		System.out.println("openLogin : " + pdv.isOpenLogin());
		System.out.println("openLogin : " + pdv.isOpenLogin());
		
		model.addAttribute("openLogin", pdv.isOpenLogin());
	}
	
	
	


	@GetMapping("testing")
	public String testing() {
		return "/content/common/concert_list_test";
	}
	@GetMapping("tt")
	public String toptest() {
		return "/fragment/toptest";
	}
	
	@GetMapping("test")
	public String index(HttpSession session, Authentication authentication) {

		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println("userId : "+userId);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		return "/content/common/index";
	}

	@GetMapping("concertList")
	public String goConcertList() {
		return "/content/common/concert_list";
	}
	
	
	
	

	
	


	
	
	
	
	
	
	

//	@PostMapping("/join")
//	public String join(MemberVO member, Model model, String[] memberEmail) {
//		
//		boolean isInsertSucceed = memberService.insertMember(member);
//		if(isInsertSucceed) {
//			System.out.println("회원명 : [" +member.getMemberName()+ "]님 등록완료!!");
//			System.out.println("회원명 : [" +member.getMemberName()+ "]님 등록완료!!");
//			System.out.println("회원명 : [" +member.getMemberName()+ "]님 등록완료!!");
//		}
//		
//		return "redirect:/item/list";
//	}
	
	@GetMapping("/login")
	public String loadLoginFormOnYourPage(HttpServletRequest request) {
		
		System.out.println("로그인창 열기.");
		System.out.println("현재 접속중인 인원 : " + securityConfig.sessionRegistry().getAllPrincipals().size());
		System.out.println("현재 접속중인 인원 : " + securityConfig.sessionRegistry().getAllPrincipals().size());	
		
		
		// 이전 페이지(인증안되서 팅궈 나오기 직전! 페이지!) 주소를 가져온다...
		String referer = request.getHeader("Referer");
		
		System.out.println(referer);
		System.out.println(referer);
		System.out.println(referer);
		
		// 권한페이지 접근하려고 했어???? 홈페이지로 가서해라 그냥
		if(referer == null || referer.contains("/artist") || referer.contains("/owner") || referer.contains("/admin")) {

			System.out.println("레퍼러는 널이야!");
			System.out.println("레퍼러는 널이야!");
			System.out.println("레퍼러는 널이야!");
			System.out.println("레퍼러는 널이야!");
			
			
			return ("redirect:/concertList?openLogin=true");
		}
		
		if(!referer.contains("?")) {
			return referer.contains("?openLogin=true") ?
					("redirect:"+referer) : ("redirect:"+referer+"?openLogin=true");
		}
		else {
			return referer.contains("&openLogin=true") ?
					("redirect:"+referer) : ("redirect:"+referer+"&openLogin=true");
		}
	}
	
	@ResponseBody
	@RequestMapping("/loginResult")
	public HashMap<String, String> loginResult(HttpServletRequest request, boolean isSuccess, HttpSession session, Authentication authentication) {
		
		System.out.println("여기오나??");
		System.out.println("여기오나??");
		System.out.println("여기오나??");
		System.out.println("여기오나??");
		System.out.println("여기오나??");
		System.out.println("여기오나??");
		
		
		//로그인 정보 없을 경우, 강제 탈출!
		if(!isSuccess)
			return null;
			
		UserDetails user = (User)authentication.getPrincipal();
		UsersVO loginInfo = commonService.selectLoginInfo( user.getUsername() );
		
		// 세션에 보안키로 정보를 저장한다..! 
		// 이 경우 session.getAttribute( user.getPassword() ); 이걸로 불러올 수 있다!
		session.setAttribute( user.getUsername(), loginInfo);
							//user.getPassword()하기엔 너무 긴듯 저장안됨.
		
		
		String referer = request.getHeader("Referer");
		
		
		
		// &openLogin= 가 있었다면 빼준다..! 주소창을 예쁘게!
		if(referer.contains("openLogin"))	// contains(문자열) 안에 &넣으면 동작 이상해지더라..
			referer = referer.substring(0, referer.indexOf("openLogin")-1);
		
		
		
		HashMap<String, String> hashLoginInfo = loginInfo.toHash();
		hashLoginInfo.put("Referer", referer); // 이전 페이지(로그인을 수행한 페이지) 주소를 가져간다...
		
		return hashLoginInfo;
	}
	
	@RequestMapping("/logout")
	public String logout(Authentication authentication) {

		String thisMemberId = ((UserDetails)authentication.getPrincipal()).getUsername();
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		
		
		System.out.println("현재 접속중인 인원 : " + securityConfig.sessionRegistry().getAllPrincipals().size());
		
		securityConfig.sessionRegistry().removeSessionInformation(  sessionId  );

		System.out.println(thisMemberId + " 님이 로그아웃 했습니다.");
		System.out.println("현재 접속중인 인원 : " + securityConfig.sessionRegistry().getAllPrincipals().size());
		
		
		return "redirect:/logout";
	}
}
