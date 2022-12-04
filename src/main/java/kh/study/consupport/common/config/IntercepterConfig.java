package kh.study.consupport.common.config;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
				
				boolean isExistCookie = false;
				
				// 익명계정 접속기록이 쿠키에 남아 있나? 확인하자.
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
