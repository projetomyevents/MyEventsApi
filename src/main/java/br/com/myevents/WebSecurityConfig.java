package br.com.myevents;

import br.com.myevents.security.TokenService;
import br.com.myevents.security.UserAccountAuthenticationFilter;
import br.com.myevents.security.UserAccountAuthenticationProvider;
import br.com.myevents.security.UserAccountAuthorizationFilter;
import br.com.myevents.security.UserAccountDetailsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuração de uma execução do aplicativo em modo web seguro.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * POST endpoints com acesso liberado pra geral.
     */
    private static final String[] PUBLIC_POST_MATCHERS = {
            "/user/register",
            "/user/login",
            "/user/password-reset"
    };

    /**
     * PUT endpoints com acesso liberado pra geral.
     */
    private static final String[] PUBLIC_PUT_MATCHERS = {
            "/guest/**"
    };

    /**
     * GET endpoints com acesso liberado pra geral.
     */
    private static final String[] PUBLIC_GET_MATCHERS = {
            "/user/sucessful-authentication/**",
            "/user/activate**",
            "/user/resend-activation/**",
            "/user/send-password-reset/**",
            "/event/{id:[\\d+]}",
            "/event/{id:[\\d+]}/guests",
            "/guest/**"
    };

    private final UserAccountDetailsService userAccountDetailsService;
    private final TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, PUBLIC_POST_MATCHERS).permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.PUT, PUBLIC_PUT_MATCHERS).permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, PUBLIC_GET_MATCHERS).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new UserAccountAuthorizationFilter(
                        authenticationManager(), userAccountDetailsService, tokenService))
                .addFilterAfter(new UserAccountAuthenticationFilter(
                        authenticationManager(), tokenService), BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(
                new UserAccountAuthenticationProvider(userAccountDetailsService, bCryptPasswordEncoder()));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    protected BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
