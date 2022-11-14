package kh.study.consupport.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
		model.addAttribute("openLogin", pdv.isOpenLogin());
	}
	
	
	

	@GetMapping("")
	public String index() {
		return "redirect:/concertList";
	}
	
	@GetMapping("test")
	public String authenticationTest(HttpSession session, Authentication authentication) {
		
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!!");
		System.out.println("userId : "+userId);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!!");
		
		return "/content/common/test";
	}

	
	
	@GetMapping("concertList")
	public String goConcertList() {
		return "/content/common/concert_list";
	}
	
	
	
	

	
	


	
	
	
	
	
	
	

//	@PostMapping("/join")
//	public String join(UsersVO user, Model model, String[] memberEmail) {
//		
//		if(memberService.insertMember(member) != 0) {
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
	public Map<String, String> loginResult(	HttpServletRequest request
											, boolean isSuccess
											, String failureCode
											, HttpSession session
											, Authentication authentication) {
											//, AuthenticationException exception) {
		
		Map<String, String> hashLoginInfo = new HashMap<>();
		
		//로그인 정보 없을 경우, 오류코드 들고 강제 탈출!
		if(!isSuccess) {
//			if(exception!=null) {
//				System.out.println("오류코드 감지해?");
//				System.out.println("오류코드 감지해?");
//				System.out.println("오류코드 감지해?");
//				System.out.println("오류코드 감지해?");
//			}
			
			hashLoginInfo.put("alertMsg", failureCode);
			System.out.println("뀨앙 팅김");
			System.out.println("뀨앙 팅김");
			System.out.println("뀨앙 팅김");
			System.out.println("뀨앙 팅김");
			
			return hashLoginInfo;
		}
		
		
		
		System.out.println("로그인후 여기오나??");
		System.out.println("로그인후 여기오나??");
		System.out.println("로그인후 여기오나??");
		System.out.println("로그인후 여기오나??");
		System.out.println("로그인후 여기오나??");
		
		
		
		UserDetails userDetails = (User)authentication.getPrincipal();
		UsersVO loginInfo = commonService.selectLoginInfo( userDetails.getUsername() );
		
		// 세션에 ID로 정보를 저장한다..! 
		// 이 경우 session.getAttribute( user.getPassword() ); 이걸로 불러올 수 있다!
		session.setAttribute( userDetails.getUsername(), loginInfo);
							//user.getPassword()하기엔 너무 긴듯 저장안됨.

		hashLoginInfo.put("userId", loginInfo.getUserId());
		hashLoginInfo.put("userName", loginInfo.getUserName());
		hashLoginInfo.put("userRole", loginInfo.getUserRole());
		
		
		
		String referer = request.getHeader("Referer");
		
		// &openLogin= 가 있었다면 빼준다..! 중복제거!
		if(referer.contains("openLogin"))
			referer = referer.substring(0, referer.indexOf("openLogin")-1); // 한글자 앞까지 지워서 ? & 도 지워줘!
		
		hashLoginInfo.put("Referer", referer); // 이전 페이지(로그인을 수행한 페이지) 주소를 가져간다...
		
		return hashLoginInfo;
	}
	
	@RequestMapping("/goLogout")
	public String logout(Authentication authentication, HttpSession session) {
		
		// sessionRegistry 에서 제거해주어야 접속 인원이 줄어들게됨
		System.out.println("### 접속 중이었던 인원 : " + securityConfig.sessionRegistry().getAllPrincipals().size());
		securityConfig.sessionRegistry().removeSessionInformation(  RequestContextHolder.currentRequestAttributes().getSessionId()  );
		// ㄴ시큐리티 세션에서 해당 계정을 로그아웃. 하지만 /logout이후에는 익명유저로 로그인하게됨.
		
		System.out.println(((UserDetails)authentication.getPrincipal()).getUsername() + " 님이 로그아웃 합니다.");
		System.out.println("### 접속 인원 갱신중.. : " + securityConfig.sessionRegistry().getAllPrincipals().size());
		
		return "redirect:/logout";
	}
}
