package kh.study.consupport.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.consupport.admin.service.AdminService;
import kh.study.consupport.artist.service.ArtistService;
import kh.study.consupport.common.config.SecurityConfig;
import kh.study.consupport.common.controller.CommonController.PageDefaultValues;
import kh.study.consupport.common.service.CommonService;
import kh.study.consupport.common.service.PaymentService;
import kh.study.consupport.common.service.TestService;
import kh.study.consupport.member.service.MemberService;
import kh.study.consupport.owner.service.OwnerService;
import lombok.Data;

@Controller
@RequestMapping("/test")
public class TestController {

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

	@Resource(name = "testService")
	private TestService testService;

	@Data
	class PageDefaultValues {
		// 로그인 및 비회원이 이용하는 서비스에 필요한 변수들..
		boolean openLogin = false;
	}

	@ModelAttribute
	public void putModelAttribute(PageDefaultValues pdv, Model model) {
		model.addAttribute("openLogin", pdv.isOpenLogin());
	}


}
