package no.smoky.magic.magicserver.jwtauth;

import java.util.logging.Logger;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {
    /**
	 * 
	 */
    private static final long serialVersionUID = -761503632386396342L;
    
    Logger logger = Logger.getLogger(JwtTokenFilter.class.getName());

	public InvalidJwtAuthenticationException(String e) {
        super(e);
        logger.info("This is InvalidJwtAuthenticationException: " + e);
    }
}