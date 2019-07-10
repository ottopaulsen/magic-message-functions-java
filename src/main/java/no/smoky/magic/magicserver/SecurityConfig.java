package no.smoky.magic.magicserver;

// Brukes

import java.util.logging.Logger;

import com.google.firebase.auth.FirebaseAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import no.smoky.magic.magicserver.jwtauth.CustomAuthenticationFailureHandler;
import no.smoky.magic.magicserver.jwtauth.JwtConfig;
import no.smoky.magic.magicserver.jwtauth.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = Logger.getLogger(SecurityConfig.class.getName());

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // .and()
            // .formLogin()
            // .failureHandler(customAuthenticationFailureHandler())
            .and()
                .authorizeRequests()
                .antMatchers("/auth/signin").permitAll()
                .antMatchers(HttpMethod.GET, "/screens/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .apply(new JwtConfig(jwtTokenProvider));
        //@formatter:on
        
        http.cors();

        logger.info("Otto Configuring JwtTokenProvider");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        JwtTokenProvider.setFirebaseAuth(auth);


    }

}