package kh.study.consupport.artist.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import kh.study.consupport.common.service.CommonService;
import kh.study.consupport.common.vo.ConcertImgVO;
import kh.study.consupport.common.vo.ConcertPriceVO;
import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.HallDateVO;
import kh.study.consupport.common.vo.HallVO;
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

	// 이 Controller의 모든 Request가 실행되기전에 무조건 거쳐가야하는 메소드!
	@ModelAttribute
	public void putModelAttribute(HttpServletRequest request, Model model) {

	}

	// 공연 등록 페이지
	@GetMapping("/regConcertForm")
	public String regConcertForm(HallVO hall ,Model model, Authentication authentication) {

		model.addAttribute("hallList", artistService.hallList());
		model.addAttribute("genreList", artistService.genreList());

		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		hall.setUserId(userId);
		model.addAttribute("hall", hall);
		return "content/artist/reg_concert_from";
	}

	//홀 정보 불러오기
	@ResponseBody
	@PostMapping("/hallInfoAjax")
	public HallVO hallInfoAjax(String hallCode) {
		HallVO hallInfo = artistService.hallInfo(hallCode);
		for(HallDateVO hallDate : hallInfo.getHallDateList()) {
			
			hallDate.setHallRentDate( hallDate.getHallRentDate().replace("00:00:00", "오전") );
			hallDate.setHallRentDate( hallDate.getHallRentDate().replace("12:00:00", "오후") );
			
		}
		
		
		return hallInfo;
	}
	
	//모달창 닫힐 때 실행되는 홀목록 조회
	@ResponseBody
	@PostMapping("/hallListAjax")
	public List<HallVO> selectHallListAjax(){
		return artistService.hallList();
	}
	
	
	
	// 공연 등록
	@ResponseBody
	@PostMapping("/regConcert")
	public int regConcert(ConcertVO concert
						, MultipartFile imgMain
						, List<MultipartFile> imgsSub
						, Authentication authentication) {

		String userId = ((UserDetails)authentication.getPrincipal()).getUsername();
		
		// 메인이미지 없으면 종료.
		if(imgMain==null || (imgMain!=null && (imgMain.getOriginalFilename()==null || imgMain.getOriginalFilename().equals(""))))
			return 0;
		
		// 서브이미지 없으면 종료.
		if(imgsSub.size()==0 || (imgsSub.size()>0 && (imgsSub.get(0).getOriginalFilename()==null || imgsSub.get(0).getOriginalFilename().equals(""))))
			return 0;
		
	    // 메인&서브이미지 모두 폴더에 저장 후 UUID 파일명 받아오기
	    String mainImgAttachedName = uploadFile(imgMain);
	    List<String> subImgsAttachedNameList = uploadFiles(imgsSub);
		
	    /************** 메인이미지와 서브이미지를 모두 imgList에 넣는다 **************/
		List<ConcertImgVO> imgList = new ArrayList<>();
		
		{
			// 메인이미지 imgList에 넣는다
			ConcertImgVO concertImg = new ConcertImgVO();
			concertImg.setConcertImgNameOrigin(imgMain.getOriginalFilename());
			concertImg.setConcertImgNameAttached(mainImgAttachedName);
			concertImg.setConcertImgIsMain("Y");
			
			imgList.add(concertImg);
		}
		
		// 상세이미지'들' imgList에 넣는다
		for( int i=0; i<subImgsAttachedNameList.size(); i++ )
		{
			ConcertImgVO concertImg = new ConcertImgVO();
			concertImg.setConcertImgNameOrigin(imgsSub.get(i).getOriginalFilename());
			concertImg.setConcertImgNameAttached(subImgsAttachedNameList.get(i));
			concertImg.setConcertImgIsMain("N");
			
			imgList.add(concertImg);
		}
		/************** 메인이미지와 서브이미지를 모두 imgList에 넣는다 **************/
		
		concert.setConcertImgList(imgList);
		
		
		//공연정보 삽입
		artistService.regConcert(concert);
		
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
				String UPLOAD_PATH = "C:\\dev\\workspaceSTS_CONSUPPORT\\CONSUPPORT\\src\\main\\resources\\static\\img\\concert\\";

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
	
	
	
	
//===========================================================================================================================================================================================================================

	// 아티스트 매출관리 그래프 띄우는 페이지
	@GetMapping("/incomeManager")
	public String incomeManager() {
		return "/content/artist/income_manager";
	}

	// 내 공연 팔린좌석 그래프로 보기
	@ResponseBody
	@PostMapping("/loadIncomeManager")
	public Map<String, Object> selectSoldRSAcnt(ArtistVO artist, Authentication authentication){

		User user = (User)authentication.getPrincipal();
		artist.setUserId(user.getUsername());

		// 맵 생성
		Map<String, Object> chartMap = new HashMap<>();

		// 내 공연 매출 정보 그래프로 보기(bar)
		chartMap.put("soldSeatAmountList", artistService.selectSoldRSA_amount(artist));

		// 내 공연 팔린좌석 그래프로 보기(pie)
		chartMap.put("soldSeatCntList", artistService.selectSoldRSA_cnt(artist));

		return chartMap;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
