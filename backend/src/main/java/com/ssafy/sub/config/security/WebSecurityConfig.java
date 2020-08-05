package com.ssafy.sub.config.security;

import static com.ssafy.sub.oauth2.security.SocialType.FACEBOOK;
import static com.ssafy.sub.oauth2.security.SocialType.GOOGLE;
import static com.ssafy.sub.oauth2.security.SocialType.KAKAO;
import static com.ssafy.sub.oauth2.security.SocialType.NAVER;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.ssafy.sub.filter.CorsFilter;
import com.ssafy.sub.filter.JwtAuthenticationFilter;
import com.ssafy.sub.oauth2.security.CustomOAuth2Provider;
import com.ssafy.sub.oauth2.security.GoogleAuthenticationSuccessHandler;
import com.ssafy.sub.oauth2.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	GoogleAuthenticationSuccessHandler googleAuthenticationSuccessHandler;
	
	// 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// authenticationManager를 Bean 등록합니다.
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
				.cors().and().csrf().disable() // csrf 보안 토큰 disable처리.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지
																							// 않습니다.
				.and()
					.authorizeRequests() // 요청에 대한 사용권한 체크
						.antMatchers("/admin/**").hasRole("ADMIN")
						.antMatchers("/users/logout").hasRole("USER")
//               		.antMatchers("/feeds/**").hasRole("USER")
						.antMatchers("/facebook").hasAuthority(FACEBOOK.getRoleType()) 
						
						.antMatchers("/google").hasAuthority(GOOGLE.getRoleType()) 
						.antMatchers("/kakao").hasAuthority(KAKAO.getRoleType()) 
						.antMatchers("/naver").hasAuthority(NAVER.getRoleType())
						.anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근 가능
				.and()
					.oauth2Login()
					.userInfoEndpoint()
						.userService(new CustomOAuth2UserService())
				.and()
					.successHandler(googleAuthenticationSuccessHandler)
//					.defaultSuccessUrl("/social/login")
					.failureUrl("/login") 
				.and() 
					.exceptionHandling() 
					.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

				http
					.addFilterBefore(filter, CsrfFilter.class)
					.addFilterBefore(new CorsFilter(), SecurityContextPersistenceFilter.class)
					.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
				
		// JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
	}

	@Override // ignore check swagger resource
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**",
				"/swagger/**");
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties,
										@Value("${custom.oauth2.kakao.client-id}") String kakaoClientId,
										@Value("${custom.oauth2.kakao.client-secret}") String kakaoClientSecret,
										@Value("${custom.oauth2.naver.client-id}") String naverClientId,
										@Value("${custom.oauth2.naver.client-secret}") String naverClientSecret) {
		List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
				.map(client -> getRegistration(oAuth2ClientProperties, client)).filter(Objects::nonNull)
				.collect(Collectors.toList());
		registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao").clientId(kakaoClientId)
				.clientSecret(kakaoClientSecret).jwkSetUri("temp").build());
		registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver").clientId(naverClientId)
				.clientSecret(naverClientSecret).jwkSetUri("temp").build());
		return new InMemoryClientRegistrationRepository(registrations);
	}

	private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
		if ("google".equals(client)) {
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
			return CommonOAuth2Provider.GOOGLE.getBuilder(client).clientId(registration.getClientId())
					.clientSecret(registration.getClientSecret()).scope("email", "profile").build();
		}
		if ("facebook".equals(client)) {
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook");
			return CommonOAuth2Provider.FACEBOOK.getBuilder(client).clientId(registration.getClientId())
					.clientSecret(registration.getClientSecret())
					.userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link").scope("email").build();
		}
		return null;
	}

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.addAllowedOrigin("http://localhost:8081");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        configuration.setAllowCredentials(true);
//        
//        List<String> list = new ArrayList<String>();
//        list.add("X-AUTH-TOKEN");
//        list.add("token");
//        configuration.setExposedHeaders(list);
//        
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
