package kh.study.consupport.owner.controller;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
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
import kh.study.consupport.common.service.CommonService;
import kh.study.consupport.common.vo.HallDateVO;
import kh.study.consupport.common.vo.HallImgVO;
import kh.study.consupport.common.vo.HallSeatVO;
import kh.study.consupport.common.vo.HallVO;
import kh.study.consupport.member.service.MemberService;
import kh.study.consupport.owner.service.OwnerService;

@Controller
@RequestMapping("/owner")
public class OwnerController {

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
	
	
	
	
	
	@GetMapping("/regHallForm")
	public String regHallForm() {
		
		return "/content/owner/reg_hall_form";
	}
	
	@ResponseBody
	@PostMapping("/regHall")
	public int regHall(	HallVO hall
						, HallSeatVO hallSeat
						, String rentDateStart
						, String rentDateEnd
						, MultipartFile imgMain
						, List<MultipartFile> imgsSub
						, Authentication authentication) {
		
		
		// HALL TABLE's USER_ID
		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		hall.setUserId(userId);
		
		
		// HALL_SEAT TABLE 값들
		hall.setHallSeat(hallSeat);
		
		
		// HALL_DATE TABLE 값들
		List<String> hallRentDateList = new ArrayList<>();
		LocalDate startDate		= LocalDate.parse(rentDateStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate endNextDate	= LocalDate.parse(rentDateEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(+1);
		
		while(!startDate.equals(endNextDate)) {
			// 오전
			hallRentDateList.add( startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:00:00" );
			// 오후
			hallRentDateList.add( startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 12:00:00" );
			
			startDate = startDate.plusDays(+1);
		}
		hall.setHallRentDateList(hallRentDateList);
		
		
		
		
		
		// 메인이미지 없으면 NAGA.
		if(imgMain==null || (imgMain!=null && (imgMain.getOriginalFilename()==null || imgMain.getOriginalFilename().equals(""))))
			return 0;
		
		// 서브이미지 없으면 NAGA.
		if(imgsSub.size()==0 || (imgsSub.size()>0 && (imgsSub.get(0).getOriginalFilename()==null || imgsSub.get(0).getOriginalFilename().equals(""))))
			return 0;
		
		// 메인&서브이미지 모두 폴더에 저장 후 UUID 파일명 받아오기
		String mainImgAttachedName = uploadFile(imgMain);
		List<String> subImgsAttachedNameList = uploadFiles(imgsSub);
		
		
		/************** 메인이미지와 서브이미지를 모두 imgList에 넣는다 **************/
		List<HallImgVO> imgList = new ArrayList<>();
		
		{
			// 메인이미지 imgList에 넣는다
			HallImgVO hallImg = new HallImgVO();
			hallImg.setHallImgNameOrigin(imgMain.getOriginalFilename());
			hallImg.setHallImgNameAttached(mainImgAttachedName);
			hallImg.setHallImgIsMain("Y");
			
			imgList.add(hallImg);
		}
		
		// 상세이미지'들' imgList에 넣는다
		for( int i=0; i<subImgsAttachedNameList.size(); i++ )
		{
			HallImgVO hallImg = new HallImgVO();
			hallImg.setHallImgNameOrigin(imgsSub.get(i).getOriginalFilename());
			hallImg.setHallImgNameAttached(subImgsAttachedNameList.get(i));
			hallImg.setHallImgIsMain("N");
			
			imgList.add(hallImg);
		}
		/************** 메인이미지와 서브이미지를 모두 imgList에 넣는다 **************/
		
		
		hall.setHallImgList(imgList);
		
		ownerService.insertHall(hall);
		return 1;
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
				String UPLOAD_PATH = "C:\\dev\\workspaceSTS_CONSUPPORT\\CONSUPPORT\\src\\main\\resources\\static\\img\\hall\\";
				
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
		
		for( MultipartFile mpFile : mpFiles )
			fileNameList.add(uploadFile(mpFile));
			
		return fileNameList;
   }
}
