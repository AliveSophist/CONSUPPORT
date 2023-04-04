package kh.study.consupport.common.config;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/*
 *            request                                                 response
 * 	요청html ---------> DispatcherServlet --------------> controller ----------> 반환html
 *             filter                       intercepter
 * 
 */



@Configuration
public class IntercepterConfig implements WebMvcConfigurer{

	@Autowired
	private SecurityConfig securityConfig;
	
	
	

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        .addResourceHandler("/resources/**")
        .addResourceLocations("/resources/");
	}
	

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor( certificateAnonymous() )
				.addPathPatterns("/**/**");
		//		.excludePathPatterns("/?????"); //페이지 제외시엔 이걸로 한다.
	}
	
	@Bean
	public HandlerInterceptor certificateAnonymous() {
		return new HandlerInterceptor(){
			
			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
				
				String referer = request.getHeader("Referer");
				
				// 저세상에서 왔던거면 임시인증은 여기서 죽인다.
				if(referer != null)
					if( request.getHeader("Referer").contains("localhost:8082") ) {
					
						// 저세상에서 왔어도 마법의 코드 들고왔으면 봐줄게.
						if( request.getParameter("certCode") != null ) {
							
							// 이번만 봐준다.
							
						}
						else {
							// 접속중이던 해당 계정을 강제 로그아웃.
							securityConfig.sessionRegistry().removeSessionInformation(  RequestContextHolder.currentRequestAttributes().getSessionId()  );
							
							return;
						}
					}
				
				
				
				// 익명우저 한정 쿠키발급 기능!
				// 이미 로그인한 유저에겐 필요없는 기능이다.
				if( !request.isUserInRole("ROLE_ANONYMOUS") )
					return;
				
				boolean isExistCookie = false;
				
				// 익명계정 접속기록이 현재 쿠키에 남아 있나? 확인하자.
				if(request.getCookies() != null) {
					List<Cookie> cookies = Arrays.asList(request.getCookies());
					if(cookies.size()>0) {
						for(Cookie ck : cookies)
							if(ck.getName().equals("ANONYMOUS_UUID"))
								isExistCookie = true;
					}
				}
				
				// 발급받은 ANONYMOUS_UUID 쿠키가 없다면 내가 특. 별. 히. 하! 나! 발급해주께!!
				if(!isExistCookie) {
					// ------------------ 가져와라 얍!  아이디 ------------------ //
					String anonymousUuid = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
					
					// ------------------ 쿠키에 username 담기 ------------------ //
					Cookie cookie = new Cookie("ANONYMOUS_UUID", anonymousUuid);

					//cookie.setMaxAge(60 * 60 * 24 * 30);	// 60초 * 60분 * 24시 * 30일 = 약 1달.
					cookie.setMaxAge(60 * 60 * 24);	// 60초 * 60분 * 24시 = 하루.
					//cookie.setMaxAge(60);	// 60초
					//cookie.setMaxAge(0);	// 디폴트값. 세션. 브라우저 종료시 삭제.
					response.addCookie( cookie );
					
					System.out.println("####################################################################");
					System.out.println("####");
					System.out.println("#### 신규 쿠키 발급 ! !");
					System.out.println("#### ANONYMOUS_UUID : "+anonymousUuid);
					System.out.println("####");
					System.out.println("####################################################################");
				}
			}
			
		};
	}
	
	
	
	
	
}
