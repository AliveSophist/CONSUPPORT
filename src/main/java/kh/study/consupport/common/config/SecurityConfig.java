package kh.study.consupport.common.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		
		
		
		security.csrf().disable();
		
		security
				.authorizeRequests()
				
					.anyRequest().permitAll()
				
					//.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
					
					
					
//					.and()
//					
//				.formLogin().loginPage	("/member/login")
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
//				.logoutSuccessUrl("/item/list")
//				.invalidateHttpSession(true)
//				
//					.and()						나중에 추가해볼까
//				.exceptionHandling().accessDeniedPage("/accessDenied"); 권한없을때
				;
		
		return security.build();
	}
	
//	//암호화 기능을 갖는 객체를 하나 생성!
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	//파일은 권한검사 제외하겠다!
//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring().antMatchers("...");
//	}
//	
//	@Bean
//	public SessionRegistry sessionRegistry() {
//		return new SessionRegistryImpl();
//	}
}
