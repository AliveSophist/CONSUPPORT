package kh.study.consupport.common.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

		security.csrf().disable();
		
		// 중복 로그인 방지..!
		security.sessionManagement(session -> session
													.maximumSessions(1)
													//.maxSessionsPreventsLogin(true)
			// 좀 더 디테일 하게 만들거라면..
			// 중복 로그인 시. 유저에게 '이미 이 유저는 다른 곳에서 로그인 되어 있습니다.' 메세지 띄우고 확인시켜주는 절차 필요
		);
		
		security
				.authorizeRequests()
				
					//.anyRequest().permitAll()
				
					.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
					
					//권한이 필요 없는 페이지
					.antMatchers("/"
								,"/join"
								,"/login"
								,"/loginResult"	
								,"/logout"
								
								).permitAll()
					
					//MEMBER권한 페이지
					.antMatchers("/member/**").hasAnyRole("MEMBER", "ARTIST", "OWNER", "ADMIN")

					//ARTIST권한 전용 페이지
					.antMatchers("/artist/**").hasAnyRole("ARTIST")

					//OWNER권한 전용 페이지
					.antMatchers("/owner/**").hasAnyRole("OWNER")

					//ADMIN권한 전용 페이지
					.antMatchers("/admin/**").hasAnyRole("ADMIN")
					
					//그외.. 사실 없음..
					.anyRequest().authenticated()
					
					.and()
					
				.formLogin()
//					.successHandler( new SimpleUrlAuthenticationSuccessHandler() {
//					    @Override
//					    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//					    	response.sendRedirect("/loginResult?isSuccess=true");
//					    }
//					})
//					.failureUrl			("/loginResult?isSuccess=false")
//					.usernameParameter	("userId")
//					.passwordParameter	("userPw")
//					
//					.and()
//					
//				.logout()
//				.logoutSuccessUrl("")
//				.invalidateHttpSession(true)
					
//					.and()
//					
//				.formLogin().loginPage	("/login")
//					.successHandler( new SimpleUrlAuthenticationSuccessHandler() {
//					    @Override
//					    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//					    	response.sendRedirect("/loginResult?isSuccess=true");
//					    }
//					})
//					.failureUrl			("/loginResult?isSuccess=false")
//					.usernameParameter	("userId")
//					.passwordParameter	("userPw")
//					
//					.and()
//					
//				.logout()
//				.logoutSuccessUrl("")
//				.invalidateHttpSession(true)
				
				//.and()						나중에 추가해볼까
				//.exceptionHandling().accessDeniedPage("/accessDenied"); 권한없을때
				;
		
		return security.build();
	}
	
	//암호화 기능을 갖는 객체를 하나 생성!
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//파일은 권한검사 제외하겠다!
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("...");
	}
	
	//현재 세션 몇명?
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
}
