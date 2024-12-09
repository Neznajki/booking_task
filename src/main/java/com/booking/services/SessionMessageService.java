package com.booking.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;


@Service
public class SessionMessageService {
    public String getMessageForDisplay(HttpSession session, String index) {
        final String sessionIndex = prepareIndex(index);
        if (session.getAttribute(sessionIndex) == null) {
            return "";
        }

        final String response = session.getAttribute(sessionIndex).toString();
        session.removeAttribute(sessionIndex);
        return response;
    }

    public void rememberMessage(HttpSession session, String index, String message) {
        session.setAttribute(prepareIndex(index), message);
    }

    private String prepareIndex(String index) {
        return "_message_session_" + index;
    }
}
