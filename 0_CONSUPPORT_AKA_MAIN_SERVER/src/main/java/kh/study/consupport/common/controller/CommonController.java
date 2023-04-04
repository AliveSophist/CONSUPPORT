package kh.study.consupport.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import kh.study.consupport.admin.service.AdminService;
import kh.study.consupport.artist.service.ArtistService;
import kh.study.consupport.common.config.SecurityConfig;
import kh.study.consupport.common.constant.UserRole;
import kh.study.consupport.common.constant.UserStatus;
import kh.study.consupport.common.service.CommonService;
import kh.study.consupport.common.service.MailService;
import kh.study.consupport.common.service.PaymentService;
import kh.study.consupport.common.vo.ArtistVO;
import kh.study.consupport.common.vo.ConcertPriceVO;
import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.CouponVO;
import kh.study.consupport.common.vo.HallSeatVO;
import kh.study.consupport.common.vo.HallVO;
import kh.study.consupport.common.vo.SalesVO;
import kh.study.consupport.common.vo.TicketVO;
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
	
	@Resource(name = "paymentService")
	private PaymentService paymentService;
	
	@Resource(name = "mailService")
	private MailService mailService;
	
	
	
	
	
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
	
	// 현재 접속 아이디가 모임?
	@GetMapping("test")
	public String authenticationTest(HttpServletRequest request, HttpSession session, Authentication authentication, Model model) {
		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println("현재 아이디 : " + ((UserDetails)authentication.getPrincipal()).getUsername());
		System.out.println("접속 총인원 : " + securityConfig.sessionRegistry().getAllPrincipals().size());	
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		// 이멜 테스트
		//mailService.sendMailWhenPaid("dop03072@naver.com", null /* SalesVO sales */, null /* ConcertVO concert */);
		
		return "/content/common/test";
		//return "redirect:/concertList";
	}

	@GetMapping("accessDenied")
	public String accessDenied() {
		return "/content/common/accessDenied";
	}
	
//==================================================================================================================================================================================================================

	



	
	// 메인화면 콘서트 목록 조회
	@GetMapping("concertList")
	@CrossOrigin(origins = "http://localhost:8082")	// CORS 오류방지. 본섭 & 백섭 간의 이동이 가능해진다.
	public String selectConcertListOfCommon(Model model) {
		//model.addAttribute("concertList", commonService.selectConcertListOfCommon(1));
		model.addAttribute("specialList", commonService.selectSpecialConcertListOfCommon());
		return "/content/common/concert_list";
	}
	
	// 무한스크롤
	@ResponseBody
	@PostMapping("loadPage")
	public List<ConcertVO> loadPage(int pageNum){
		return commonService.selectConcertListOfCommon(pageNum);
	}
	
	
	
	
	
	
	// 콘서트 상세 조회
	@ResponseBody
	@PostMapping("selectConcertDetail")
	public ConcertVO selectConcertDetail(ConcertVO concertVO) {
		return commonService.selectConcertDetail(concertVO);
	}
	
	// 아티스트 상세 조회
	@ResponseBody
	@PostMapping("/selectArtistDetail")
	public ArtistVO selectArtistDetail(ArtistVO artistVO) {
		return adminService.selectArtistDetail(artistVO);
	}
	
//==================================================================================================================================================================================================================
	
	// 콘서트 검색 페이지
	@RequestMapping("searchForm")
	public String searchForm(@RequestParam Map<String, String> paramMap, Model model) {
		model.addAttribute("concertListMap", commonService.serchConcert(paramMap));
		
		// 검색회면 키테고리 목록 띄우기
		model.addAttribute("genreList", artistService.genreList());
		
		model.addAttribute("paramMap", paramMap);
		
		return "/content/common/search_form";
	}
	
//==================================================================================================================================================================================================================
	
	@GetMapping("reserveSeatForm")
	public String loadReserveSeatForm(Authentication authentication, ConcertVO concert, Model model) {
		
		// 권한 넣어서 가라
		model.addAttribute("authorities", ((UserDetails)authentication.getPrincipal()).getAuthorities().toString());
		
		// concertPrice 넣어서 가라
		concert.setConcertPrice( commonService.selectConcertPrice(concert) );
		model.addAttribute("concert", concert);
		
		// 좌석목록 넣어서 가라
		List<TicketVO> ticketList = commonService.selectTicketList(concert);
		model.addAttribute("ticketList", ticketList);
		
		return "/content/common/reserve_seat_form";
	}
	
	@ResponseBody
	@PostMapping("getSalesCode")
	public String insertSalesAndGetBuyCode(SalesVO sales, CouponVO coupon, HallSeatVO hallSeat, HttpServletRequest request, Authentication authentication) {

		if(request.isUserInRole("ROLE_ANONYMOUS"))
			sales.setUserId("ANONYMOUS");
		else
			sales.setUserId(((UserDetails)authentication.getPrincipal()).getUsername());


		
		sales.setHallseat(hallSeat);
		sales.setCoupon(  coupon.getCouponValue()>0 ? coupon : new CouponVO()  );
		
		return commonService.getSalesCode(sales);
	}
	
	@ResponseBody
	@PostMapping("reserveSeat")
	public String reserveSeat(SalesVO sales, CouponVO coupon, String anonymousEmail, HttpServletRequest request, Authentication authentication) {
		
		if(request.isUserInRole("ROLE_ANONYMOUS")) {
			sales.setUserId("ANONYMOUS");
			
			ConcertVO concert = new ConcertVO();
			concert.setConcertCode(sales.getConcertCode());
			concert = commonService.selectConcertDetail(concert);
			
			// 익명유저일 경우 이메일을 보낸다.
			mailService.sendMailWhenPaid(anonymousEmail, sales, concert);
		}
		else
			sales.setUserId(((UserDetails)authentication.getPrincipal()).getUsername());
		
		sales.setCoupon(  coupon.getCouponValue()>0 ? coupon : new CouponVO()  );
		
		// it returns 'PAID' or 'CANCELED'
		return commonService.tryTicketing(sales);
	}
	
	@ResponseBody
	@PostMapping("cancelWhenPaying")
	public String cancelWhenPaying(SalesVO sales) {
		
		commonService.cancelWhenPaying(sales);
		
		return "CANCELED COMPLETE";
	}
	

	@ResponseBody
	@PostMapping("canclePay")
	@CrossOrigin(origins = "https://api.iamport.kr")	//https://api.iamport.kr/payments/cancel
	public String canclePay(SalesVO sales, String merchant_uid, int cancel_request_amount) {
		
		System.out.println();
		System.out.println();
		System.out.println("merchant_uid : " + merchant_uid);
		System.out.println("merchant_uid : " + merchant_uid);
		System.out.println();
		System.out.println("cancel_request_amount : " + cancel_request_amount);
		System.out.println("cancel_request_amount : " + cancel_request_amount);
		System.out.println();
		System.out.println();
		
		try {
			// 아임포트에 환불 요청..!
			paymentService.paymentCancle(merchant_uid, cancel_request_amount);

			// 환불이 완료되면 티켓 원상복구 + 해당 SALES_STATUS는 'REFUNDED'로 변경
			commonService.refundAll(sales);
			
			return "환불되었습니다.";
		} catch (IOException e) {
			e.printStackTrace();
			return "환불오류..";
		}
	}
	
	@ResponseBody
	@PostMapping("selectCoupon")
	public CouponVO selectCoupon(String couponCode) {
		return commonService.selectCoupon(couponCode);
	}
	
	
	

	@ResponseBody
	@PostMapping("getAge")
	public int getAge(HttpServletRequest request, Authentication authentication) {
		
		if(request.isUserInRole("ROLE_ANONYMOUS"))
			return 0;
		
		
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		
		return commonService.getUserAge(userId);
	}
	
	
	
	
	
	
	
	
	
	@GetMapping("concertSchedule")
	public String loadConcertSchedule() {
		
		return "/content/common/concert_schedule";
	}
	
	@ResponseBody
	@PostMapping("selectConcertListForCalendar")
	public Map<String, Object> selectConcertListForCalendar(){

		Map<String, Object> result = new HashMap<>();
		result.put("concertList", commonService.selectConcertListForCalendar());
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//== 가입 및 로그인 관련 ===================================================================================================================================================================================================================

	
	@ResponseBody
	@PostMapping("join")
	public Object join(@Validated UsersVO user, BindingResult bindingResult, HttpServletRequest request) {
		// ㄴ 이걸 Object 로 할 수도 있네!?
		
		if(bindingResult.hasErrors()){

			//대충 에러 리스트 받아오는 외계어임
			//람다 개어렵네 무슨뜻인지 모름 ㅅㄱ
			List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			
			Map<String, Object> hashErrorInfo = new HashMap<>();
			hashErrorInfo.put("errors", errors);
			
			return hashErrorInfo;
		}
		
		
		// 가입 쿼리 실행 전 password 인코딩, userStatus userRole 셋팅
		user.setUserPw		( securityConfig.passwordEncoder().encode(user.getUserPw()) );
		user.setUserStatus	( UserStatus.ACT.toString() );
		user.setUserRole	( UserRole.MEMBER.toString() );
		
		// 생년월일은 -빼고 넣자
		user.setUserBirth	( user.getUserBirth().replaceAll("-", "") );
		
		
		// 아이디 중복 가입! 오류뱉자!
		try {
			commonService.insertUser(user);
		} catch (Exception e2) {
			Map<String, Object> hashErrorInfo = new HashMap<>();
			hashErrorInfo.put("duplicated", "해당 아이디는 이미 가입한 계정입니다.");
			
			return hashErrorInfo;
		}
		
		
		// 가입 오류 없으면 로그인까지 슥삭! 먼저 ID PW 셋팅해주시고~
		request.setAttribute("userId", user.getUserId());
	    request.setAttribute("userPw", user.getUserPw());
	    
	    // PostMapping 방식으로 redirect 하는 방법! Spring Security의 login은 PostMapping이므로.....
		request.setAttribute( View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT );
		return new ModelAndView("redirect:/login");
	}
	
	@GetMapping("login")
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
	@RequestMapping("loginResult")
	public Map<String, String> loginResult(	HttpServletRequest request
											, boolean isSuccess
											, String failureCode
											, HttpSession session
											, Authentication authentication) {
		
		Map<String, String> hashLoginInfo = new HashMap<>();
		
		//로그인 정보 없을 경우, 오류코드 들고 강제 탈출!
		if(!isSuccess) {
			hashLoginInfo.put("alertMsg", failureCode);
			return hashLoginInfo;
		}
		
		
		UserDetails userDetails = (User)authentication.getPrincipal();
		
		UsersVO loginInfo = commonService.selectLoginInfo( userDetails.getUsername() );
		session.setAttribute( "loginInfo", loginInfo);

		hashLoginInfo.put("userId", loginInfo.getUserId());
		hashLoginInfo.put("userName", loginInfo.getUserName());
		hashLoginInfo.put("userRole", loginInfo.getUserRole());
		
		
		// 이전 페이지 주소 가져오기! Referer!!
		String referer = request.getHeader("Referer");
		
		// &openLogin= 가 있었다면 빼줘라
		if(referer.contains("openLogin"))
			referer = referer.substring(0, referer.indexOf("openLogin")-1 /** 한글자 앞까지 지워서 ? & 도 지워줘! **/ );
		
		hashLoginInfo.put("Referer", referer); // 이전 페이지(로그인을 수행한 페이지) 주소를 가져간다...
		
		return hashLoginInfo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// URI Request. 참고용
	
	// 콘서포트서버에서 백업서버로 보내버리는 리퀘스트.
	//			http://localhost:8082/
	
	// 백업서버에서 본서버에 통신하는 리퀘스트 두개.
	//			http://localhost:8088/enterTheWaitingQueue
	//			http://localhost:8088/isAvailableEnter
	
	
	// 테스트 대기열에 입장 시도. (입장 불가 시 로그인 불가.)

	
	// 테스트 대기열 생성 
	@GetMapping ("readyForWaitingQueueTest")
	public String setForWaitingQueueTest() {
		System.out.println("################################################################");
		System.out.println("###");
		System.out.println("### 테스트용 큐에 더미 유저 채우는중... ^^");
		System.out.println("###");
		System.out.println("################################################################");
		
		// n개의 더미 계정 삽입..!
		securityConfig.makeDummyWaitingQueue( 50 );
		
		return "/content/common/waiting_queue_test";
	}
	
	
	// 테스트 정지
	@GetMapping ("stopQueueTest")
	public String stopQueueTest() {
		System.out.println("################################################################");
		System.out.println("###");
		System.out.println("### 테스트를. 정지합니다.");
		System.out.println("###");
		System.out.println("################################################################");
		
		securityConfig.breakDummyWaitingQueue();
		
		return "redirect:/concertList";
	}
	

	// 테스트 대기열에 입장 (로그인 불가. +로그아웃)
	@ResponseBody
	@PostMapping ("enterTheWaitingQueue")
	@CrossOrigin(origins = "http://localhost:8082")	// CORS 오류방지. 본섭 & 백섭 간의 이동이 가능해진다.
	public void enterTheWaitingQueue(String keyQueue) {
		System.out.println("################################################################");
		System.out.println("###");
		System.out.println("### 현재 큐 접속 인원 : " + securityConfig.getNowTestQueueSize());
		System.out.println("###");
		System.out.println("### 테스트용 큐에 접속합니다...");
		System.out.println("###");
		System.out.println("################################################################");
		
		// LET ME IN TESTING QUEUE!!
		securityConfig.plzLetMeInTestQueue( keyQueue );
	}
	
	
	// 대기열에서 입장가능한지 확인..!
	@ResponseBody
	@PostMapping ("isAvailableEnter")
	@CrossOrigin(origins = "http://localhost:8082")	// CORS 오류방지. 본섭 & 백섭 간의 이동이 가능해진다.
	public Map<String, Object> isAvailableEnter(String keyQueue) {
		
		Map<String, Object> result = new HashMap<>();
		
		
		// 나 지금 들어갈 수 있냐?
		boolean isAvailableEnter = securityConfig.isAvailableEnter(keyQueue);
		result.put("isAvailableEnter", isAvailableEnter);
		
		
		// 기다려야하네..?
		if(!isAvailableEnter)
		{
			// 현재 대기열 번호 줘.
			int numLeftQueue = securityConfig.getMyQueueNum(keyQueue);
			result.put("numLeftQueue", numLeftQueue);
		}
		
		// 0번 대기열이시면 입장하실 수 있습니다. 갓 생성한 따끈따끈한 마법의 코드를 발! 급!
		else
		{
			securityConfig.randomTemporaryCertCode();
			result.put("certCode", securityConfig.getTemporaryCertCode());
		}
		
		
		result.put("toString", result.toString());
		
		return result;
	}
	
	
}
