package kh.study.consupport.qboard.controller;

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

import kh.study.consupport.admin.service.AdminService;
import kh.study.consupport.artist.service.ArtistService;
import kh.study.consupport.common.service.CommonService;
import kh.study.consupport.common.vo.QboardVO;
import kh.study.consupport.member.service.MemberService;
import kh.study.consupport.owner.service.OwnerService;
import kh.study.consupport.qboard.service.QboardService;

@Controller
@RequestMapping("/board")
public class QboardController {

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
	
	@Resource(name = "qboardService")
	private QboardService qboardService;
	
	//이 Controller의 모든 Request가 실행되기전에 무조건 거쳐가야하는 메소드!
	@ModelAttribute
	public void putModelAttribute(HttpServletRequest request, Model model) {
		
	}
	
	//문의 리스트창으로 이동
	@RequestMapping("/qboardList")
	public String qboardList(QboardVO qboard, Model model, Authentication authentication) {
		
		//전체 데이터 수
		int totalCnt = qboardService.selectQboardCnt();
		
		//페이지 정보 세팅
		qboard.setTotalDataCnt(totalCnt);
		qboard.setPageInfo();
		
		model.addAttribute("qboardList", qboardService.selectQboardList(qboard));
		
		return "content/board/qboardList";
	}
	
	// 문의 등록창으로 이동
	@GetMapping("/regQboard")
	public String regQboard() {
		
		return "content/board/regQboard";
	}
	
	//문의사항 등록
	@PostMapping("/regQboard")
	public String regQboard(QboardVO qboard) {
		qboardService.insertQboard(qboard);
		return "content/board/qboardList";
	}
	
	@GetMapping("/detailQboard")
	public String detailBoard(
			@RequestParam(required = false
						, defaultValue = "10"
						, name = "num") int qboardNum, Model model) {
		model.addAttribute("board", qboardService.selectDetailQboard(qboardNum));
		return "board/detail_board";
	}
	
}