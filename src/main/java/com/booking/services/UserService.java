package com.booking.services;

import com.booking.db.entity.AppUser;
import com.booking.db.repository.AppUserRepository;
import com.booking.exception.DuplicateUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        final Optional<AppUser> byEmail = appUserRepository
                .findByEmail(userEmail);
        return byEmail
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, userEmail)));
    }

    public AppUser findUserByEmail(String userEmail) throws UsernameNotFoundException {
        return appUserRepository
                .findByEmail(userEmail)
                .orElse(null);
    }

    public AppUser createUser(String email, String name, String password) throws DuplicateUserException {
        AppUser existingUser = this.findUserByEmail(email);

        if (existingUser == null) {
            AppUser user = new AppUser(name, email, password);
            appUserRepository.save(user);

            return user;
        }

        if (existingUser.isConfirmed()) {
            throw new DuplicateUserException("user already exists");
        }
        existingUser.setName(name);
        existingUser.setPassword(password);


        appUserRepository.save(existingUser);

        return existingUser;
    }

    public void confirmUser(AppUser appUser) {
        appUser.setConfirmed(true);
        appUserRepository.save(appUser);
    }
}
