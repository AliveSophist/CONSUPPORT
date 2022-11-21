package kh.study.consupport.chatting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chatting")
public class ChatController {
	
	@RequestMapping("/chat")
	public String popup(){
		return "content/chatting/chat";
	}
}
