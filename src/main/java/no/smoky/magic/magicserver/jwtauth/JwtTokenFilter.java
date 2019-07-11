package no.smoky.magic.magicserver.jwtauth;

import java.io.IOException;
import java.util.logging.Logger;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.HttpMethod;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
@Order(1)
public class JwtTokenFilter extends GenericFilterBean {

    Logger logger = Logger.getLogger(JwtTokenFilter.class.getName());
    
    private ObjectMapper objectMapper = new ObjectMapper();

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        logger.info("JwtTokenFilter: Constructor");
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
    throws IOException, ServletException {      
        logger.info("JwtTokenFilter: doFilter");

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        HttpServletResponse httpResponse = (HttpServletResponse) res;
        HttpServletRequest httpRequest = (HttpServletRequest) req;

        if (token != null) {
            boolean tokenValid = jwtTokenProvider.validateToken(token);
    
            logger.info("JwtTokenFilter Token valid: " + tokenValid);
    
            if (tokenValid) {
                Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
                logger.info("JwtTokenFilter Authentication: " + auth);
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(req, res);
            } else {
                logger.info("Token not valid");
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                // throw new InvalidJwtAuthenticationException("Otto: Token not valid throws ex");


                // Map<String, Object> data = new HashMap<>();
                // data.put(
                //   "timestamp", 
                //   Calendar.getInstance().getTime());
                // data.put(
                //   "exception", 
                //   "Invalid authentication token");
         
                // res.getOutputStream()
                //   .println(objectMapper.writeValueAsString(data));
     
                res.getOutputStream().println("{\"Error\": \"Invalid authentication token\"}");
     


            }
        } else {
            logger.info("Token not found");
            
            if(httpRequest.getMethod().equals("OPTIONS")) {
                logger.info("But method is OPTIONS");
                httpResponse.setStatus(HttpStatus.OK.value());
                filterChain.doFilter(req, res);
            } else {
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                res.getOutputStream().println("{\"Error\": \"Authentication token not found. Use Authorization header with Bearer token\"}");
            }

        }

    }
}