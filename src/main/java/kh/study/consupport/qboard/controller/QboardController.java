package kh.study.consupport.qboard.controller;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.consupport.admin.service.AdminService;
import kh.study.consupport.artist.service.ArtistService;
import kh.study.consupport.common.service.CommonService;
import kh.study.consupport.common.vo.AboardVO;
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
	@ResponseBody
	@PostMapping("/regQboard")
	public int regQboard(@Validated QboardVO qboard) {
		
		if(qboard.getQsecret() == null)
			qboard.setQsecret("F");
		
		qboardService.insertQboard(qboard);
		return 1;
	}

	//글 상세보기
	//넘어오는 데이터의 이름과 매개변수의 이름을 일치시키면
	//매개변수에 자동으로 데이터가 넘어온다
	//매개변수에 @RequestParam 어노테이션을 사용하면
	//넘어오는 데이터에 관한 설정을 할 수 있다.
	//속성1 required : true, false 값이 올 수 있고, 넘어오는 
	//				데이터의 필수여부를 지정한다.
	//속성2 defaultValue : 넘어오는 데이터가 없을 때 기본값 지정
	//속성3 name : 넘어오는 데이터의 이름과 매개변수의 이름이 불일치 할때
	//				데이터를 바인딩(연결) 시켜준다.
	@GetMapping("/detailQboard")
	public String detailBoard(
			@RequestParam(required = false
						, defaultValue = "10"
						, name = "num") int qboardNum, Model model
						, Authentication authentication) {
		
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		
		model.addAttribute("qboard", qboardService.selectDetailQboard(qboardNum));
		model.addAttribute("aboardList", qboardService.selectAboardList(qboardNum));
		return "content/board/detail_qboard";
	}
	
	//글 수정페이지 이동
	@GetMapping("/qboardUpdate")
	public String boardUpdate(@RequestParam(required = false
								, defaultValue = "10"
								, name = "num") int qboardNum, Model model) {
		
		model.addAttribute("qboard", qboardService.selectDetailQboard(qboardNum));
		
		return "content/board/qboard_update";
	}
	
	//글 수정
	@PostMapping("/qboardUpdate")
	public String boardUpdate(QboardVO qboard) {
		
		if(qboard.getQsecret() == null)
			qboard.setQsecret("F");
		
		qboardService.updateQboard(qboard);
		return "redirect:/board/detailQboard?num=" + qboard.getQboardNum();
	}
	
	//글 삭제
	@GetMapping("/qboardDelete")
	public String boardDelete(@RequestParam(required = false
											, defaultValue = "10"
											, name = "num")int qboardNum) {
		qboardService.deleteQboard(qboardNum);
		return "redirect:/board/qboardList";
	}
	
	//문의 답글 작성
	@PostMapping("/aboardInsert")
	public String insertAboard(AboardVO aboard) {
		qboardService.insertAboard(aboard);
		return "redirect:/board/detailQboard?num=" + aboard.getQboardNum();
	}
	
	
}