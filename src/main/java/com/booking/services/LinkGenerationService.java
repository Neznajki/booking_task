package com.booking.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LinkGenerationService {
    public String createConfirmLink(String email, String hash) {
        Map<String, String> getParams = new HashMap<>();

        getParams.put("email", email);
        getParams.put("hash", hash);

        return createLink("/confirm/hash", getParams);
    }

    public String createLink(String relativeLink, Map<String, String> getParameters) {
        StringBuilder result = new StringBuilder(relativeLink);

        if (!getParameters.isEmpty()) {
            result.append('?');

            for (String valueName: getParameters.keySet()) {
                result.append(String.format("%s=%s&", valueName, getParameters.get(valueName)));
            }
        }

        return result.toString();
    }
}
