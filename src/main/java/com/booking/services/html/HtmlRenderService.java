package com.booking.services.html;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import com.booking.contract.HtmlPageInterface;
import com.booking.services.SessionMessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HtmlRenderService {
    public static final String ERROR_MESSAGE_INDEX = "errorMessage";
    public static final String SUCCESS_MESSAGE_INDEX = "successMessage";
    private final HtmlReaderService htmlReaderService;
    private final HtmlReplacerService htmlReplacerService;
    private final SessionMessageService sessionMessageService;

    public String createResponse(HtmlPageInterface htmlPage, HttpServletRequest req) {
        ContentReplacerItemCollection replacerItems = new ContentReplacerItemCollection();

        replacerItems.addContentReplacerItem(
            new ContentReplacerItem("title", htmlPage.getPageTitle())
        );
        String pageContent = htmlReaderService.readHtmlFileContent("templates/index.html");
        String bodyContent = htmlReaderService.readHtmlFileContent(htmlPage.getHtmlFileLocation());

        ContentReplacerItemCollection pageReplacerItems = htmlPage.getReplacerItems();
        pageReplacerItems
                .addContentReplacerItem(new ContentReplacerItem("CSRF_INPUT", getCsrfTokenData(req)));
        replacerItems.addContentReplacerItem(
            new ContentReplacerItem(
                "content",
                htmlReplacerService.replaceItems(bodyContent, pageReplacerItems)
            )
        );

        String errorMessage = "";
        String successMessage = "";
        if (req != null) {
            errorMessage = sessionMessageService.getMessageForDisplay(req.getSession(), ERROR_MESSAGE_INDEX);
            successMessage = sessionMessageService.getMessageForDisplay(req.getSession(), SUCCESS_MESSAGE_INDEX);
        }

        replacerItems.addContentReplacerItem(new ContentReplacerItem(ERROR_MESSAGE_INDEX, errorMessage));
        replacerItems.addContentReplacerItem(new ContentReplacerItem(SUCCESS_MESSAGE_INDEX, successMessage));

        return htmlReplacerService.replaceItems(pageContent, replacerItems);
    }

    private String getCsrfTokenData(HttpServletRequest req) {
        String tokenHolderContent = htmlReaderService.readHtmlFileContent("templates/security/csrfTokenHolder.html");
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        ContentReplacerItemCollection replacerItems = new ContentReplacerItemCollection();

        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(req);
        String parameterName = csrfToken.getParameterName();
        String token = csrfToken.getToken();
        String headerName = csrfToken.getHeaderName();

        replacerItems
                .addContentReplacerItem(new ContentReplacerItem("_csrf.parameterName", parameterName))
                .addContentReplacerItem(new ContentReplacerItem("_csrf.token", token))
                .addContentReplacerItem(new ContentReplacerItem("_csrf.headerName", headerName));

        return htmlReplacerService.replaceItems(tokenHolderContent, replacerItems);
    }
}
