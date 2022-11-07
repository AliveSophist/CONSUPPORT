package kh.study.consupport.artist.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
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
	
	//공연 등록 페이지
	@GetMapping("/regConcertForm")
	public String regConcertForm(Model model, Authentication authentication) {
		
		model.addAttribute("hallLIst", artistService.hallList());
		model.addAttribute("genreList", artistService.genreList());
		
		
		return "content/artist/reg_concert_from";
	}
	
	//공연 등록
	@ResponseBody
	@PostMapping("/regConcert")
	public String regConcert() {
		
		
		
		return "redirect:/artist/regConcertForm";
	}
	
	
	
	
	
	
	
	
}
