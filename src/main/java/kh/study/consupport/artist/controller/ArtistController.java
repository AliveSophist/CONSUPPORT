package kh.study.consupport.artist.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;

import kh.study.consupport.admin.service.AdminService;
import kh.study.consupport.artist.service.ArtistService;
import kh.study.consupport.common.service.CommonService;
import kh.study.consupport.common.vo.ConcertVO;
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

	// 이 Controller의 모든 Request가 실행되기전에 무조건 거쳐가야하는 메소드!
	@ModelAttribute
	public void putModelAttribute(HttpServletRequest request, Model model) {

	}

	// 공연 등록 페이지
	@GetMapping("/regConcertForm")
	public String regConcertForm(Model model, Authentication authentication) {

		model.addAttribute("hallLIst", artistService.hallList());
		model.addAttribute("genreList", artistService.genreList());

		User user = (User) authentication.getPrincipal();
		// model.addAttribute("", user)

		return "content/artist/reg_concert_from";
	}

	// 공연 등록
	@ResponseBody
	@PostMapping("/regConcert")
	public String regConcert(ConcertVO concertVO, MultipartFile concertImg, List<MultipartFile> subconcertImgs) {

		
		
		// 메인이미지가 없으면 종료
		if(concertImg==null (concertImg!=null && (concertImg.getOriginalFilename()==null	concertImg.getOriginalFilename().equals(""))))
			return 0;
		// 서브이미지가 없으면 종료
		if(subconcertImgs.size()==0 (subconcertImgs.size()>0 && (subconcertImgs.get(0).getOriginalFilename()==null	subconcertImgs.get(0).getOriginalFilename().equals(""))))
			return 0;
		
		
		// 단일 이미지 파일 첨부 - 메인이미지
		String mainIngAttachedName = uploadFile(concertImg);

		// 다중 이미지 파일 첨부 - 서브이미지
		List<String> subImgsAttachedNameList = uploadFiles(subconcertImgs);

	    // 메인&서브이미지 모두 폴더에 저장 후 UUID 파일명 받아오기
	    String mainImgAttachedName = uploadFile(concertImg);
	    List<String> subImgsAttachedNameList = uploadFiles(subconcertImgs);
		
		
		return "redirect:/artist/regConcertForm";
	}

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
				String UPLOAD_PATH = "C:\\workspaceSTS_CONSUPPORT\\CONSUPPORT\\src\\main\\resources\\static\\img\\concert";

				mpFile.transferTo(new File(UPLOAD_PATH + fileName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return fileName;
	}

	// 다중 파일첨부
	public static List<String> uploadFiles(List<MultipartFile> mpFiles) {
		List<String> fileNameList = new ArrayList<String>();

		for (MultipartFile mpFile : mpFiles)
			fileNameList.add(uploadFile(mpFile));

		return fileNameList;
	}

}
