package com.booking.services;

import com.booking.contract.EncodingInterface;
import com.booking.encoder.Sha512Encoder;
import com.booking.exception.ErrorMessageResponseException;
import com.booking.form.LoginForm;
import com.booking.request.LoginFormRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
public class LoginService {
    private final EncodingInterface passwordEncoder = new Sha512Encoder();
    private final AuthenticationManager authManager;
    private final UserService userService;

    @Autowired
    public LoginService(UserService userService, AuthenticationManager authManager) {
        this.userService = userService;
        this.authManager = authManager;
    }

    public void checkLoginCredentials(HttpServletRequest req, LoginFormRequest loginFormRequest) throws ErrorMessageResponseException {
        final LoginForm loginForm = new LoginForm(loginFormRequest.email(), loginFormRequest.password());

        UserDetails userDetails = userService.findUserByEmail(loginFormRequest.email());

        if (userDetails == null) {
            throw new ErrorMessageResponseException(
                "user not found or invalid password",
                loginForm
            );
        }

        if (! passwordEncoder.encodeString(loginFormRequest.password()).equals(userDetails.getPassword())) {
            throw new ErrorMessageResponseException(
                "user not found or invalid password",
                loginForm
            );
        }

        if (! userDetails.isAccountNonLocked()) {
            throw new ErrorMessageResponseException(
                "user account is not confirmed use confirmation link to unlock account, if you lost link please use register form again",
                loginForm
            );
        }

        this.login(req, userDetails.getUsername(), loginFormRequest.password());
    }

    private void login(HttpServletRequest req, String user, String pass) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user, pass);
        Authentication auth = authManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = req.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
    }
}
