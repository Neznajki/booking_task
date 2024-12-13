package com.booking.main;

import com.booking.db.entity.AppUser;
import com.booking.db.repository.AppUserRepository;
import com.booking.encoder.Sha512Encoder;
import com.booking.helper.FileHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
    public static final String EMAIL = "functional@test.com";
    public static final String TEST_PASSWORD = "functional test password";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AppUserRepository appUserRepository;
    private final Sha512Encoder sha512Encoder = new Sha512Encoder();

    @Test
    public void testRedirectToLogin() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().is(302)).andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testLoginPage() throws Exception {
        String response = mockMvc.perform(get("/login"))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(
            FileHelper.readFileAsString("responseFiles/login.html").replace("\r\n", "\n"),
            response.replace("\r\n", "\n")
        );
    }

    @Test
    public void testLoginErrors() throws Exception {
        AppUser appUser = new AppUser(
            "Functional Test",
            EMAIL,
            sha512Encoder.encode(TEST_PASSWORD)
        );
        appUserRepository.save(appUser);

        mockMvc.perform(
            post("/login")
                .param("email", EMAIL)
                .param("password", "real password")
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("user not found or invalid password")));

        mockMvc.perform(
            post("/login")
                .param("email", "email")
                .param("password", TEST_PASSWORD)
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("user not found or invalid password")));

        mockMvc.perform(
            post("/login")
                .param("email", EMAIL)
                .param("password", TEST_PASSWORD)
        )
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("user account is not confirmed use confirmation link to unlock account, if you lost link please use register form again")));

        appUser.setConfirmed(true);
        appUserRepository.save(appUser);

        mockMvc.perform(
                post("/login")
                        .param("email", EMAIL)
                        .param("password", TEST_PASSWORD)
        )
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/private/cabinet"));
    }
}
