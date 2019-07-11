package no.smoky.magic.magicserver;

import java.util.logging.Logger;

import com.google.firebase.auth.FirebaseAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import no.smoky.magic.magicserver.jwtauth.JwtConfig;
import no.smoky.magic.magicserver.jwtauth.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = Logger.getLogger(SecurityConfig.class.getName());

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/auth/signin").permitAll()
                .antMatchers(HttpMethod.GET, "/screens/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .apply(new JwtConfig(jwtTokenProvider));
        //@formatter:on
        
        http.cors();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        JwtTokenProvider.setFirebaseAuth(auth);
    }
}