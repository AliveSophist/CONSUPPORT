package kh.study.consupport.member.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kh.study.consupport.admin.service.AdminService;
import kh.study.consupport.artist.service.ArtistService;
import kh.study.consupport.common.config.SecurityConfig;
import kh.study.consupport.common.service.CommonService;
import kh.study.consupport.common.vo.ArtistImgVO;
import kh.study.consupport.common.vo.ArtistVO;
import kh.study.consupport.common.vo.UsersVO;
import kh.study.consupport.member.service.MemberService;
import kh.study.consupport.owner.service.OwnerService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
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
	
	//이 Controller의 모든 Request가 실행되기전에 무조건 거쳐가야하는 메소드!
	@ModelAttribute
	public void putModelAttribute(HttpServletRequest request, Model model) {
		
	}
	
//===========================================================================================================================================================================================================================
	// 마이 페이지
	@GetMapping("/myPage")
	public String myPage(Authentication authentication, Model model) {
		UsersVO users = new UsersVO();
		users.setUserId(  ((UserDetails)authentication.getPrincipal()).getUsername()  );
		model.addAttribute("salesInfoList", memberService.selectMypageSalesInfo(users));
		return "content/member/my_page";
	}
	
//===========================================================================================================================================================================================================================
	
	// 아티스트 등록 페이지
	@GetMapping("/regArtistForm")
	public String regArtistForm() {
		return "content/member/reg_artist_form";
	}
	
//===========================================================================================================================================================================================================================
	
	
	
	
	
	
//===========================================================================================================================================================================================================================
	
	// 아티스트 등록
	@PostMapping("/regArtist")
		public String insertArtist(ArtistVO artist
								  , MultipartFile imgMain
								  , List<MultipartFile> imgsSub
								  , Authentication authentication) {
			
			// 로그인한 멤버의 정보 추출
			User user = (User)authentication.getPrincipal();
			artist.setUserId(user.getUsername());

			// 메인&서브이미지 모두 폴더에 저장 후 UUID 파일명 받아오기
			String mainImgAttachedName = uploadFile(imgMain);
			List<String> subImgsAttachedNameList = uploadFiles(imgsSub);
			

			/************** 메인이미지와 서브이미지를 모두 imgList에 넣는다 **************/
			
			List<ArtistImgVO> imgList = new ArrayList<>();
			{
				// 메인이미지 imgList에 넣는다
				ArtistImgVO artistImg = new ArtistImgVO();
				artistImg.setArtistImgNameOrigin(imgMain.getOriginalFilename());
				artistImg.setArtistImgNameAttached(mainImgAttachedName);
				artistImg.setArtistImgIsMain("Y");

				imgList.add(artistImg);
			}

			// 상세이미지'들' imgList에 넣는다
			for (int i = 0; i < subImgsAttachedNameList.size(); i++) {
				ArtistImgVO artistImg = new ArtistImgVO();
				artistImg.setArtistImgNameOrigin(imgsSub.get(i).getOriginalFilename());
				artistImg.setArtistImgNameAttached(subImgsAttachedNameList.get(i));
				artistImg.setArtistImgIsMain("N");

				imgList.add(artistImg);
			}
			/************** 메인이미지와 서브이미지를 모두 imgList에 넣는다 **************/

			artist.setArtistImgList(imgList);
			memberService.insertArtist(artist);
		
			return "redirect:/concertList";
		}
	
//===========================================================================================================================================================================================================================
	
	// 패스워드 체크
	@GetMapping("/pwCheckForm")
	public String pwCheck() {
		return "content/member/pwCheckForm";
	}
	
	// 패스워드 맞는지 체크
	@ResponseBody
	@PostMapping("/pwCheck")
	public boolean pwCheck(UsersVO users, Authentication authentication) {
		
		
		String userId = ((User) authentication.getPrincipal()).getUsername();
		// DB에서 받아온 패스워드   %UKWKQ~%@%@#@#!%#@!@#!$!%
		
		String selectedPw = memberService.selectPw(userId);
		
		
		// 방금 유저로부터 input 태그로 받은 패스워드 123  =  user.getUserPw()
		
		// 방금 받은 패스워드와,  DB에서 받은 패스워드를 비교.
		// 그 후 맞으면 true 전송, 틀리면 false 전송.
		return securityConfig.passwordEncoder().matches(users.getUserPw(), selectedPw);
	
		
	}
	
//===========================================================================================================================================================================================================================
	
	
	// 내 정보 수정 페이지
	@GetMapping("/editInfoForm")
	public String editInfoForm(Model model, Authentication authentication, UsersVO users) {
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		model.addAttribute("user", memberService.editInfoForm(userId));
		
		return "content/member/edit_info_form";
	}
	
	// 내 정보 수정
	@PostMapping("/editInfo")
	public String editInfo(UsersVO users) {
		
		memberService.updateEditInfo(users);
		
		return "redirect:/concertList";
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//===========================================================================================================================================================================================================================

	// 파일첨부
	public static String uploadFile(MultipartFile mpFile) {
		
		String fileName = null;
		
		if (!mpFile.isEmpty()) {

			// 첨부하려는 원본 파일명 등록
			String originFileName = mpFile.getOriginalFilename();

			// UUID.randomUUID : 파일명 중복을 막기 위해 랜덤판 파일명을 문자열로 생성
			String uuid = UUID.randomUUID().toString();

			// extention : 확장자를 추출한다. db에파일명은 랜덤생성 + 확장자로 들어간다
			String extension = originFileName.substring(originFileName.lastIndexOf("."));

			// 첨부될 파일명
			fileName = uuid + extension;

			try {
				// artist, concert, hall 서로 다른 폴더로 지정 필요
				String UPLOAD_PATH = "C:\\dev\\workspaceSTS_CONSUPPORT\\CONSUPPORT\\src\\main\\resources\\static\\img\\artist\\";
				
				mpFile.transferTo(new File(UPLOAD_PATH + fileName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return fileName;
	}

//===========================================================================================================================================================================================================================
	
	// 다중 파일첨부
	public static List<String> uploadFiles(List<MultipartFile> mpFiles) {
		List<String> fileNameList = new ArrayList<String>();
		
		for( MultipartFile mpFile : mpFiles )
			fileNameList.add(uploadFile(mpFile));
			
		return fileNameList;
   }
	
//===========================================================================================================================================================================================================================
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

