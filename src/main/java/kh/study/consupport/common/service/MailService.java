package kh.study.consupport.common.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kh.study.consupport.common.vo.ConcertVO;
import kh.study.consupport.common.vo.SalesDetailVO;
import kh.study.consupport.common.vo.SalesVO;

@Service
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
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

	public void sendMailWhenPaid(String toUser, SalesVO sales, ConcertVO concert) {
		
		sales = sqlSession.selectOne("commonMapper.selectSalesInfoForEmail", sales);
		
		
		MailHandler mailHandler;
		try {
			mailHandler = new MailHandler(javaMailSender);

			mailHandler.setTo(toUser);
			
			// 제목
			mailHandler.setSubject("[CONSUPPORT] 티켓 구매 정보");
	
			// html 로 작성된 내용.
			String content = "";
			content +="<p style=\"font-size: 10pt; font-family: sans-serif; padding: 0px 0px 0px 10pt;\"><br><br></p><div style=\"background-image: url(&quot;https://imgur.com/eBJwwBd.jpg&quot;); width: 800px; background-repeat: no-repeat; background-size: contain; padding-right: 400px;\">\r\n"
					+ "\r\n"
					+ "<p style=\"text-align: center;\"><br></p><p style=\"text-align: center;\"><br></p><p style=\"text-align: center;\"><a href=\"http://localhost:8088/concertList\" target=\"_blank\"><img src=\"https://imgur.com/TAjDIo7.jpg\"></a></p><p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><br></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><b><span style=\"font-size: 18pt; font-family: Verdana; color: rgb(0, 0, 0);\">이용해 주셔서 감사합니다!</span></b></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><span style=\"font-family: &quot;Courier New&quot;; color: rgb(0, 0, 0);\"><span style=\"font-family: Verdana;\">* </span><span style=\"font-family: Verdana;\">티켓 정보를 확인하</span><span style=\"font-family: Verdana;\">시고&nbsp;</span></span><span style=\"font-family: &quot;Courier New&quot;;\"><span style=\"font-family: Verdana;\">이상이 있으시면 고객센터로 문의</span><span style=\"font-family: Verdana;\">주시</span><span style=\"font-family: Verdana;\">기</span><span style=\"font-family: Verdana;\">&nbsp;바랍니다.</span></span></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><br></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><span style=\"font-size: 14pt; font-family: &quot;Courier New&quot;; color: rgb(246, 246, 246);\"><b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">공연명 :</span></b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\"> "+concert.getConcertName()+"</span></span></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><b><br></b></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><span style=\"font-size: 14pt; font-family: &quot;Courier New&quot;; color: rgb(246, 246, 246);\"><b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">공연일시 : </span></b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">";
			
			content += concert.getConcertDate().indexOf("12:")>0 ? 
					concert.getConcertDate().substring(0, concert.getConcertDate().indexOf(" ")) + " 오후" : concert.getConcertDate().substring(0, concert.getConcertDate().indexOf(" ")) + " 오전";
			
			content +="</span></span></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><b><br></b></p>\r\n"
					+ "<p style=\"text-align: center;\"><span style=\"font-size: 14pt; color: rgb(246, 246, 246); font-family: &quot;Courier New&quot;;\"><b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">공연장 : </span></b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">"+concert.getHallName()+"</span></span></p>\r\n"
					+ "<p style=\"text-align: center;\"><span style=\"font-size: 24pt;\"><span style=\"color: rgb(246, 246, 246); font-family: &quot;Courier New&quot;; font-size: 14pt;\"><b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">좌석정보 : </span></b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">";
			
			for(SalesDetailVO salesDetail : sales.getSalesDetailList()) {
				content += (salesDetail.getSeatCode()+" ");
			}
			
			content +="</span></span></span></p>\r\n"
					+ "<p style=\"text-align: center;\"><br></p>\r\n"
					+ "<p style=\"text-align: center;\"><b style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: start;\"><b></b></b></p>\r\n"
					+ "<p style=\"margin: 0px; padding: 0px; text-align: center;\"><span style=\"font-size: 13.3333px; font-family: Verdana; color: rgb(0, 0, 0);\">즐거운 관람 되시기를</span><span style=\"font-size: 13.3333px; font-family: Verdana; color: rgb(0, 0, 0);\">&nbsp;바랍니다.</span></p><p style=\"margin: 0px; padding: 0px; text-align: center;\"><br></p><p style=\"margin: 0px; padding: 0px; text-align: center;\"><span style=\"font-size: 13.3333px; font-family: Verdana; color: rgb(0, 0, 0);\"><br></span></p><p style=\"margin: 0px; padding: 0px; text-align: center;\"><span style=\"font-size: 13.3333px; font-family: Verdana; color: rgb(0, 0, 0);\"><br></span></p><p style=\"margin: 0px; padding: 0px; text-align: center;\"><span style=\"font-size: 13.3333px; font-family: Verdana; color: rgb(0, 0, 0);\"><br></span></p>\r\n"
					+ "<p style=\"margin: 0px; padding: 0px; text-align: center;\"><br></p><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">\r\n"
					+ "\r\n"
					+ "</span></div><p><br></p>";
					
					
			
					/*
					"<div style=\"background-image:url('https://imgur.com/eBJwwBd.jpg'); background-repeat: no-repeat; background-size: contain; padding-right:300px;\">\r\n"
					+ "\r\n"
					
					+ "<p style=\"text-align: center;\"><a href='http://localhost:8088/concertList'><img src='https://imgur.com/TAjDIo7.jpg'/> </a></p>\r\n"
					
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><br></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><b><span style=\"font-size: 18pt; font-family: Verdana; color: rgb(0, 0, 0);\">이용해 주셔서 감사합니다!</span></b></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><br></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><span style=\"font-family: &quot;Courier New&quot;; color: rgb(0, 0, 0);\"><span style=\"font-family: Verdana;\">* </span><span style=\"font-family: Verdana;\">티켓 정보를 확인하</span><span style=\"font-family: Verdana;\">시고&nbsp;</span></span><span style=\"font-family: &quot;Courier New&quot;;\"><span style=\"font-family: Verdana;\">이상이 있으시면 고객센터로 문의</span><span style=\"font-family: Verdana;\">주시</span><span style=\"font-family: Verdana;\">기</span><span style=\"font-family: Verdana;\">&nbsp;바랍니다.</span></span></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><span style=\"font-family: &quot;Courier New&quot;; color: rgb(246, 246, 246);\"><br></span></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><br></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><span style=\"font-size: 14pt; font-family: &quot;Courier New&quot;; color: rgb(246, 246, 246);\"><b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">공연명 :</span></b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\"> " + concert.getConcertName() + "</span></span></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><b><br></b></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><span style=\"font-size: 14pt; font-family: &quot;Courier New&quot;; color: rgb(246, 246, 246);\"><b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">공연일시 : </span></b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">";
			
			content += concert.getConcertDate().indexOf("12:")>0 ? 
					concert.getConcertDate().substring(0, concert.getConcertDate().indexOf(" ")) + " 오후" : concert.getConcertDate().substring(0, concert.getConcertDate().indexOf(" ")) + " 오전";
			
			content += "</span></span></p>\r\n"
					+ "<p style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: center;\"><b><br></b></p>\r\n"
					+ "<p style=\"text-align: center;\"><span style=\"font-size: 14pt; color: rgb(246, 246, 246); font-family: &quot;Courier New&quot;;\"><b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">공연장 : </span></b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">" + concert.getHallName() + "</span></span></p>\r\n"
					+ "<p style=\"text-align: center;\"><span style=\"font-size: 24pt;\"><span style=\"color: rgb(246, 246, 246); font-family: &quot;Courier New&quot;; font-size: 14pt;\"><b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">좌석정보 : </span></b><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">";
			
			for(SalesDetailVO salesDetail : sales.getSalesDetailList()) {
				content += (salesDetail.getSeatCode()+" ");
			}
			
			content += "</span></span></span></p>\r\n"
					+ "<p style=\"text-align: center;\"><br></p>\r\n"
					+ "<p style=\"text-align: center;\"><b style=\"color: rgb(0, 0, 0); font-family: Gulim, 굴림, sans-serif; font-size: 14px; text-align: start;\"><b></b></b></p>\r\n"
					+ "<p style=\"margin: 0px; padding: 0px; text-align: center;\"><span style=\"font-size: 13.3333px; font-family: Verdana; color: rgb(0, 0, 0);\">즐거운 관람 되시기를</span><span style=\"font-size: 13.3333px; font-family: Verdana; color: rgb(0, 0, 0);\">&nbsp;바랍니다.</span></p>\r\n"
					+ "<p style=\"margin: 0px; padding: 0px; text-align: center;\"><br></p><span style=\"color: rgb(0, 0, 0); font-family: Verdana;\">\r\n"
					+ "\r\n"
					+ "</span></div><p><br></p>";*/
					
					
			
			mailHandler.setText( content, true);
	
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