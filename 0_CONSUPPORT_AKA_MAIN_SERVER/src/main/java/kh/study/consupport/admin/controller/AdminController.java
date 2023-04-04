package kh.study.consupport.admin.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.consupport.admin.service.AdminService;
import kh.study.consupport.artist.service.ArtistService;
import kh.study.consupport.common.service.CommonService;
import kh.study.consupport.common.vo.ArtistVO;
import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.UsersVO;
import kh.study.consupport.member.service.MemberService;
import kh.study.consupport.owner.service.OwnerService;

@Controller
@RequestMapping("/admin")
public class AdminController {

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
	
	//이 Controller의 모든 Request가 실행되기전에 무조건 거쳐가야하는 메소드!
	@ModelAttribute
	public void putModelAttribute(HttpServletRequest request, Model model) {
		
	}
	
//==================================================================================================================
	
	// 아티스트 목록 조회
	@GetMapping("/artistManager")
	public String artistManager(Model model) {
		model.addAttribute("artistList", adminService.selectArtistList());
		return "content/admin/artist_manager";
	}
	
//==================================================================================================================
	
	// 아티스트 승격
	@ResponseBody
	@PostMapping("/updateUserRoleToArtist")
	public void updateUserRoleToArtist(UsersVO usersVO) {
		adminService.updateUserRoleToArtist(usersVO);
	}
	
//==================================================================================================================
	
	// 아티스트 상세 조회
	@ResponseBody
	@PostMapping("/selectArtistDetail")
	public ArtistVO selectArtistDetail(ArtistVO artistVO) {
		return adminService.selectArtistDetail(artistVO);
	}
	
//==================================================================================================================
	
	// 콘서트 목록 조회
	@GetMapping("/concertManager")
	public String concertManager(Model model) {
		model.addAttribute("concertListDEACT", adminService.selectConcertListDEACT());
		
		List<ConcertVO> concertListACT = adminService.selectConcertListACT();
		List<String> specialConcerList = adminService.selectSpecialConcert();
		
		// 스페셜콘서트 조회. 개빡세네;
		for(ConcertVO concertVO : concertListACT) {
			for(String concertCode : specialConcerList) {
				if(concertVO.getConcertCode().equals(concertCode)) {
					concertVO.setIsSpecial("SPECIAL");
				}
			}
		}
		
		model.addAttribute("concertListACT", concertListACT);
		
		return "content/admin/concert_manager";
	}
	
//==================================================================================================================
	
	// 스페셜콘서트 등록
	@ResponseBody
	@PostMapping("/insertSpecialConcert")
	public void insertSpecialConcert(ConcertVO concertVO) {
		adminService.insertSpecialConcert(concertVO);
	}
	
	// 스페셜콘서트 폐기
	@ResponseBody
	@PostMapping("/deleteSpecialConcert")
	public void deleteSpecialConcert(ConcertVO concertVO) {
		adminService.deleteSpecialConcert(concertVO);
	}
	
	
	
//==================================================================================================================
	
	// 콘서트 허가 or 불허가
	@PostMapping("/updateConcertStatus")
	public String updateConcertStatus(ConcertVO concertVO) {
		adminService.updateConcertStatus(concertVO);
		return "redirect:/admin/concertManager";
	}
	
//==================================================================================================================
	
	// 콘서트 상세 조회
	@ResponseBody
	@PostMapping("selectConcertDetail")
	public ConcertVO selectConcertDetail(ConcertVO concertVO) {
		return commonService.selectConcertDetail(concertVO);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	// 오너 매니저 불러오기
	@GetMapping("/ownerManager")
	public String ownerManager(Model model) {
		model.addAttribute("ownerList", adminService.selectOwnerList());
		return "content/admin/owner_manager";
	}
	
	// 오너 권한 부여 철회
	@ResponseBody
	@PostMapping("/updateUserRoleAboutOwner")
	public boolean updateUserRoleToOwner(UsersVO user) {
		System.out.println("권한변경실행!");
		System.out.println("권한변경실행!");
		System.out.println("권한변경실행!");
		System.out.println("권한변경실행!");
		System.out.println("권한변경실행!");
		System.out.println("권한변경실행!");
		System.out.println("권한변경실행!");
		System.out.println("권한변경실행!");
		
		
		return adminService.updateUserRoleAboutOwner(user);
	}
	
	
	
	
	
	
	
	
	
	
}
