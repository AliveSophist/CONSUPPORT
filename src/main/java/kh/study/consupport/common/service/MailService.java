package kh.study.consupport.common.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kh.study.consupport.common.vo.SalesVO;

@Service
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	
	
	public void sendMail(String toUser) {	// Example

		// 수신 대상을 담을 ArrayList 생성
		ArrayList<String> toUserList = new ArrayList<>();

		// 수신 대상 추가
		toUserList.add(toUser);

		// 수신 대상 개수
		int toUserSize = toUserList.size();

		// SimpleMailMessage (단순 텍스트 구성 메일 메시지 생성할 때 이용)
		SimpleMailMessage simpleMessage = new SimpleMailMessage();

		// 수신자 설정
		simpleMessage.setTo((String[]) toUserList.toArray(new String[toUserSize]));

		// 메일 제목
		simpleMessage.setSubject("Subject Sample");

		// 메일 내용
		simpleMessage.setText("Text Sample");

		// 메일 발송
		javaMailSender.send(simpleMessage);
	}

	public void sendMailWhenPaid(	String toUser,
									SalesVO sales,
									String concertName,
									String ticketCode,
									String seatCode) {
		MailHandler mailHandler;
		try {
			mailHandler = new MailHandler(javaMailSender);

			mailHandler.setTo(toUser);
			
			// 제목
			mailHandler.setSubject("[NFU]학사경고 안내");
	
			// html 로 작성된 내용.
			mailHandler.setText("<p>" + "안녕하세요. ㅡㅡ ㅡㅡ 내용 수정할거야 ㅡㅡ ㅡㅡ 님" + "<p>"
								+ "<b>NumberFive University 학사지원팀입니다.<b>" + "<p>" + " 학사경고 처리되었음을 알려드리고자 메일을 송부합니다."
								+ "<p style='color:blue; font-size:24px; font-weight:700;'>" + "학사경고내역 : <p>"
								+ "자세한 사항은 첨부파일을 확인하여 주시고, 학과 사무실에 문의하여 주십시오." + "<p>" + " 감사합니다." + "<p>"
								+ "<img src='https://imgur.com/jrXOBny.png'>"
								, true);
	
			// 파일 포함..?
			// "<img src='/images/cid:nf Logo'>"; <<<<< html 문자열에 cid: 를 붙여줘야한다나봐?
			// mailHandler.setAttach("학사경고 안내.pdf","static/images/학사경고 안내.pdf" );
			// mailHandler.setInline("nf logo", "static/images/nf logo.png");
	
			mailHandler.send();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public class MailHandler {
		private JavaMailSender sender;
		private MimeMessage message;
		private MimeMessageHelper msgHelper;

		public MailHandler(JavaMailSender sender) throws MessagingException {
			this.sender = sender;
			message = sender.createMimeMessage();
			msgHelper = new MimeMessageHelper(message, true, "UTF-8");
		}

		public void setFrom(String fromAddress) throws MessagingException {
			msgHelper.setFrom(fromAddress);
		}

		public void setTo(String email) throws MessagingException {
			msgHelper.setTo(email);
		}

		public void setSubject(String subject) throws MessagingException {
			msgHelper.setSubject(subject);
		}

		public void setText(String text, boolean useHtml) throws MessagingException {
			msgHelper.setText(text, useHtml);
		}

		public void setAttach(String displayFileName, MultipartFile file) throws MessagingException {
			msgHelper.addAttachment(displayFileName, file);
		}

		public void setInline(String contentId, MultipartFile file) throws MessagingException, IOException {
			msgHelper.addInline(contentId, new ByteArrayResource(file.getBytes()), "image/jpeg");
		}

		public void send() {
			try {
				sender.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}