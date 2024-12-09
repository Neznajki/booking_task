package com.booking.services;

import com.booking.contract.EncodingInterface;
import com.booking.db.entity.AppUser;
import com.booking.db.entity.ConfirmLink;
import com.booking.db.repository.ConfirmLinkRepository;
import com.booking.encoder.Sha256Encoder;
import com.booking.services.html.HtmlRenderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfirmLinkService {
    public static final String NOHASH_CODE_MESSAGE = "no hash code existed";
    private final EncodingInterface encoding = new Sha256Encoder();
    private final ConfirmLinkRepository confirmLinksRepository;
    private final LinkGenerationService linkGenerationService;
    private final UserService userService;
    private final SessionMessageService sessionMessageService;

    @Autowired
    public ConfirmLinkService(ConfirmLinkRepository confirmLinksRepository, LinkGenerationService linkGenerationService, UserService userService, SessionMessageService sessionMessageService) {
        this.confirmLinksRepository = confirmLinksRepository;
        this.linkGenerationService = linkGenerationService;
        this.userService = userService;
        this.sessionMessageService = sessionMessageService;
    }

    public boolean confirmUserEmail(HttpServletRequest req, String email, String hash) {
        List<ConfirmLink> confirmLinkList = confirmLinksRepository.findByUserEmail(email);

        if (confirmLinkList.isEmpty()) {
            sessionMessageService.rememberMessage(req.getSession(), HtmlRenderService.ERROR_MESSAGE_INDEX, NOHASH_CODE_MESSAGE);
            return false;
        }

        for (ConfirmLink confirmLink: confirmLinkList) {
            if (confirmLink.getConfirmHash().equals(hash)) {
                userService.confirmUser(confirmLink.getUser());
                sessionMessageService.rememberMessage(req.getSession(), HtmlRenderService.SUCCESS_MESSAGE_INDEX, "email confirmed please login");
                return true;
            }
        }

        sessionMessageService.rememberMessage(req.getSession(), "errorMessage", NOHASH_CODE_MESSAGE);
        return false;
    }

    public String createConfirmLink(AppUser user) {
        String hashCode = encoding.encodeString(user.getEmail() + user.getName());
        ConfirmLink confirmLink = new ConfirmLink(user, hashCode);
        confirmLinksRepository.save(confirmLink);

        return linkGenerationService.createConfirmLink(user.getEmail(), confirmLink.getConfirmHash());
    }
}
