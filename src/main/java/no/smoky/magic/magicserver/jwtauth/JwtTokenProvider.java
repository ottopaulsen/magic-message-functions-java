package no.smoky.magic.magicserver.jwtauth;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.util.Value;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import no.smoky.magic.magicserver.model.User;

@Component
public class JwtTokenProvider {

    private static FirebaseAuth fbAuth;

    public static void setFirebaseAuth(FirebaseAuth auth) {
        fbAuth = auth;
    }

    static Logger logger = Logger.getLogger(JwtTokenProvider.class.getName());

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    public Authentication getAuthentication(String token) {
        String userName = getUsername(token);
        String userId = getUid(token);
        User userDetails = new User(userName, userId);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        FirebaseToken firebaseToken;
        try {
            firebaseToken = fbAuth.verifyIdToken(token);
            return firebaseToken.getEmail();
        } catch (FirebaseAuthException e) {
            logger.info("getUserName not able to verify id token: " + e);
            return null;
        }
    }

    public String getUid(String token) {
        FirebaseToken firebaseToken;
        try {
            firebaseToken = fbAuth.verifyIdToken(token);
            return firebaseToken.getUid();
        } catch (FirebaseAuthException e) {
            logger.info("getUid not able to verify id token: " + e);
            return null;
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        // logger.info("JwtTokenProvider.resolveToken found token: " + bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            fbAuth.verifyIdToken(token);
            // FirebaseToken firebaseToken = fbAuth.verifyIdToken(token);
            // logger.info("Authenticated: " + firebaseToken.getEmail());
            return true;
        } catch (FirebaseAuthException e) {
            logger.info("Authentication error: " + e);   

            return false;
        }
    }
}