package kh.study.consupport.artist.controller;

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
import kh.study.consupport.member.service.MemberService;
import kh.study.consupport.owner.service.OwnerService;

@Controller
@RequestMapping("/artist")
public class ArtistController {

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
	
//===========================================================================================================================================================================================================================
	
	// 아티스트 정보 수정 페이지
	@GetMapping("/editInfoForm")
	public String editInfoForm() {
		return "content/artist/edit_artist_info_form";
	}
	
//===========================================================================================================================================================================================================================
	
	// 아티스트 정보 수정 (이미지는 천천히)
	@PostMapping("/editInfo")
	public String editInfo(ArtistVO artist, Authentication authentication) {
		
		// 로그인한 멤버의 정보 추출
		User user = (User)authentication.getPrincipal();
		artist.setUserId(user.getUsername());

		artistService.updateArtist(artist);
		return "content/artist/edit_artist_info_form";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
