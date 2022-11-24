package kh.study.consupport.common.config;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.context.request.RequestContextHolder;

import kh.study.consupport.common.vo.UsersVO;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		
		
		// csrf 무시
		security.csrf().disable();
		
		
		// 익명유저 허용
		// 참고 https://stackoverflow.com/questions/47347037/spring-security-guest-user
		security.anonymous().authenticationFilter( new AnonymousAuthenticationFilter("WTF") {
			
			@Override
			protected Authentication createAuthentication(HttpServletRequest request) {

				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				System.out.println("### 접속 중이었던 인원 : " + sessionRegistry().getAllPrincipals().size());
				System.out.println("### 익명유저 새로 접속");
				
				
				
				String username = null;
				
//				// 익명계정 접속기록이 쿠키에 남아 있다면 해당 아이디를 불러온다.
//				{
//					List<Cookie> cookies = Arrays.asList(request.getCookies());
//					if(cookies.size()>0) {
//						for(Cookie ck : cookies)
//							if(ck.getName().equals("ANONYMOUS_UUID")) {
//								username = ck.getValue();
//								
//								System.out.println("### 쿠키 있음. 해당 쿠키로 로그인.");
//							}
//					}
//				}
				
				// 쿠키에 없다면 신규 UUID를 발급한다.
				if(username == null) {
					System.out.println("### 쿠키 없음. 신규 익명 아이디 발급필요.");
					username = UUID.randomUUID().toString();
					
					// ------------------ 쿠키에 username 담기 ------------------
					
				}
				
				
				
				System.out.println("### userId : "+username);
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		    	
				UserDetails user = (User
										.withUsername(username)
										.password("")
										.roles("ANONYMOUS")
										.build());
				
				return new UsernamePasswordAuthenticationToken (user, null, AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
				// return new AnonymousAuthenticationToken(key, user, AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
				// ㄴ 아니 뭔 매개변수 순서가 이렇게 제멋대로야 미쳤나 싑
			}
			
		} );
		
		
		
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
					
					//권한이 필요 없는 페이지 : 비회원 접속시 ANONYMOUS 계정으로 인증이 자동으로 되어버리므로 무의미 ㅋㅋ
					.antMatchers(
//								"/**"
								).permitAll()
					
					// MEMBER권한 페이지
					.antMatchers("/member/**").hasAnyRole("MEMBER", "ARTIST", "OWNER", "ADMIN")

					// ARTIST권한 전용 페이지
					.antMatchers("/artist/**").hasAnyRole("ARTIST", "ADMIN")

					// OWNER권한 전용 페이지
					.antMatchers("/owner/**").hasAnyRole("OWNER", "ADMIN")

					// ADMIN권한 전용 페이지
					.antMatchers("/admin/**").hasAnyRole("ADMIN")
					
					// 그외는 비회원도 허용됨
					.anyRequest().authenticated()
					
					.and()
					
				.formLogin().loginPage	("/login")
					.successHandler( new SimpleUrlAuthenticationSuccessHandler() {
					    @Override
					    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

					    	//스프링 시큐리티 기본적으로는 로그인 만료시간이 1800초. 30분.
					    	request.getSession().setMaxInactiveInterval(60*60*24); //일케하면 하루.
					    	
//					    	{
//						    	System.out.println("몇분남았니??" + request.getSession().getMaxInactiveInterval());
//						    	System.out.println("몇분남았니??" + request.getSession().getMaxInactiveInterval());
//					    	}
					    	
					    	response.sendRedirect("/loginResult?isSuccess=true");
					    }
					} )
					.failureHandler( new SimpleUrlAuthenticationFailureHandler() {
						@Override
						public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
								
							String failureCode = exception.getClass().getSimpleName();
							
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							System.out.println(exception.getMessage());
							System.out.println(exception.getClass().getSimpleName());
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							
							if(exception.getMessage().equals("!USERNAME_NOT_FOUND!")) {
								System.out.println("쇼신쇼메이노 아이디 에러이다...!");
								System.out.println("쇼신쇼메이노 아이디 에러이다...!");
							}
							
							response.sendRedirect("/loginResult?isSuccess=false&failureCode="+failureCode);
						}
					} )
					.usernameParameter	("userId")
					.passwordParameter	("userPw")
					
					.and()
					
				.logout()
				.addLogoutHandler( new LogoutHandler() {
					
					@Override
					public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
						
						System.out.println("### 삭제 아이디 : " + ((UserDetails)authentication.getPrincipal()).getUsername());
						System.out.println("### 접속 총인원 : " + sessionRegistry().getAllPrincipals().size());
						
						// 접속중이던 해당 계정을 로그아웃.
						sessionRegistry().removeSessionInformation(  RequestContextHolder.currentRequestAttributes().getSessionId()  );
						

						request.getSession().invalidate();
					}
				} )
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				
					.and()
					
				.exceptionHandling().accessDeniedPage("/accessDenied")
				;
		
		return security.build();
	}
	
	// 암호화 담당하는 객체
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 파일은 권한검사 제외하겠다! 를 선언하는 객체
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("...");
	}
	
	// 현재 세션 몇명? 받아오는 객체
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	// Bean과 익명클래스로 구현한 UserDetailsService 객체
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Autowired
			private SqlSessionTemplate sqlSession;

			@Override
			public UserDetails loadUserByUsername(String username) {
				
				UsersVO loginInfo = sqlSession.selectOne("commonMapper.login", username);
				
				if( loginInfo == null ) {
			    	System.out.println("아디없어!! 살려조! 끄아아앙!");
			    	throw new UsernameNotFoundException("!USERNAME_NOT_FOUND!");
				}
				
				// 접속중이던 익명유저 계정을 로그아웃.
				sessionRegistry().removeSessionInformation(  RequestContextHolder.currentRequestAttributes().getSessionId()  );
				
				System.out.println( username + " 님 로그인");
				System.out.println("### 접속 인원 갱신중.. : " + sessionRegistry().getAllPrincipals().size());
				
				return ( User
						.withUsername(loginInfo.getUserId())
						.password(loginInfo.getUserPw())
						.roles( loginInfo.getUserRole().split(",") )
						.build());
			}
			
		};
	}
	
	// UsernameNotFoundException 막아놓아서 못 불러오는 유미터진 시큐리티를 BadCredentialsException
	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
	    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	    daoAuthenticationProvider.setUserDetailsService( userDetailsService() );
	    daoAuthenticationProvider.setPasswordEncoder( passwordEncoder() );
	    
	    // ★ 아이디 틀렸을때 BadCredentialsException 아니라 UsernameNotFoundException 뱉게하는 마법의 문장 ★
	    daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
	    
	    return daoAuthenticationProvider;
	}

}
