package com.booking.main;

import com.booking.helper.FileHelper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistrationPageAccess() throws Exception {
        String response = mockMvc.perform(get("/register"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        assertEquals(
            FileHelper.readFileAsString("responseFiles/registration.html")
                    .replace("\r\n", "\n"),
            response.replace("\r\n", "\n").replaceFirst("<input name=\"_csrf\"[^\\/]+\\/>", "token")
        );
    }

    @Test
    public void testRegistrationCycle() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post("/register")
                .param("email", "functional@test.com")
                .param("name", "Functional Test")
                .param("password", "testPass")
                .param("passwordRepeat", "testPass")
        ).andExpect(status().isOk()).andReturn();

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
        assertNotNull(session);
        String response = mvcResult.getResponse().getContentAsString();

        assertThat(response, containsString(
            "follow link to confirm email address <a href=\"/confirm/hash?email=functional@test.com&hash=a63d8e538946a527e03ccf7b0110391a4ffadb1276c6229635c67c377856847e&\">confirm_email</a>"
        ));

        mockMvc.perform(get("/confirm/hash?email=functional@test.com&hash=a63d8e538946a527e03ccf7b0110391a4ffadb1276c6229635c67c377856847e&").session(session))
                .andExpect(status().is(302)).andExpect(redirectedUrl("/login?email=functional%40test.com"));

        mockMvc.perform(get("/login").session(session))
                .andExpect(status().isOk()).andExpect(content().string(containsString("email confirmed please login")));
    }

    @ParameterizedTest
    @MethodSource("errorTestProvider")
    public void registerErrors(String email, String name, String password, String repeatPassword, String message) throws Exception {
        mockMvc.perform(post("/register")
                .param("email", email)
                .param("name", name)
                .param("password", password)
                .param("passwordRepeat", repeatPassword)
        ).andExpect(status().isBadRequest()).andExpect(content().string(containsString(message)));
    }

    private static Stream<Arguments> errorTestProvider() {
        return Stream.of(
                Arguments.of("functional@test.com", generateSymbols(65), "", "", "user name should be less than 64"),
                Arguments.of(generateSymbols(120), "Functional Test", "password", "passwordRepeat", "email should be email (email@domain.com)"),
                Arguments.of("functional@test.com", generateSymbols(64), "password", "passwordRepeat", "password and password repeat should be match"),
                Arguments.of("functional@test.com", generateSymbols(5), "password", "password", "name should contain at least 6 symbols"),
                Arguments.of(generateSymbols(121), "Functional Test", "", "", "user name should be less than 120"),
                Arguments.of("functional@test.com", "Functional Test", "", "", "password should contain at least 6 symbols")
        );
    }

    private static String generateSymbols(Integer amount) {
        return StringUtils.repeat("*", amount);
    }
}
