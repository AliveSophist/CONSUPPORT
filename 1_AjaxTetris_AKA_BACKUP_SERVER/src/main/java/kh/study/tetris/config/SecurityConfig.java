package kh.study.tetris.config;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration // 스프링께서 이 클래스는 설정이라 하시옵고
@EnableWebSecurity // 해당 클래스로부터 만들어진 객체가 Security 설정 파일임을 인지하시나니.....
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		
		System.out.println("waaaaaa Git Test!!");
		
		security.csrf().disable();
		//참고 https://stackoverflow.com/questions/47347037/spring-security-guest-user
		security.anonymous().authenticationFilter( new AnonymousAuthenticationFilter("WTF") {
			
			@Override
			protected Authentication createAuthentication(HttpServletRequest request) {

				System.out.println("### 익명 유저가 새로 접속 ###");
		    	
				UserDetails user = (User
										.withUsername(UUID.randomUUID().toString())
										.password("")
										.roles("ANONYMOUS")
										.build());
				
				return new UsernamePasswordAuthenticationToken (user, null, AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
				//return new AnonymousAuthenticationToken(key, user, AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
				//ㄴ 아니 뭔 매개변수 순서가 이렇게 제멋대로야 미쳤나 싑
			}
			
		} );
		
		
		
		
		return security
					.authorizeRequests()
					
						.anyRequest().permitAll()
	/*
						.antMatchers( "/"
									, "/login"
									, "/tetris/**"
									).permitAll()
	//					.antMatchers("/admin/**").hasAnyRole("ADMIN") 기능을 넣을지?
						.anyRequest().authenticated()
	*/	
						
						
	//					.and()
	//					
	//				.formLogin().loginPage	("/login")
	//					.successHandler( new SimpleUrlAuthenticationSuccessHandler() {
	//					    @Override
	//					    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
	//					    	response.sendRedirect("/member/loginResult?isSuccess=true");
	//					    }
	//					})
	//					.failureUrl			("/member/loginResult?isSuccess=false")
	//					.usernameParameter	("memberId")
	//					.passwordParameter	("memberPw")
	//					
	//					.and()
	//					
	//				.logout()
	//				.invalidateHttpSession(true)
	//				.logoutSuccessUrl("/item/list")
	//				
	//				//나중에 추가해볼까
	//				.and()						
	//				.exceptionHandling().accessDeniedPage("/accessDenied"); 권한없을때
	
						
						.and()
					.build();
	}

//	@Bean
//	public AnonymousAuthenticationFilter anonymousAuthenticationFilter() {
//		return new AnonymousAuthenticationFilter(null) {
//			@Override
//			protected Authentication createAuthentication(HttpServletRequest request) throws AuthenticationException {
//				
//			}
//		};
//	}

	// 암호화 기능을 갖는 객체를 하나 생성!
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 파일은 권한검사 대충 제외하겠다!
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
	}

}