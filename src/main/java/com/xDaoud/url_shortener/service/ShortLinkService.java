package com.xDaoud.url_shortener.service;

import com.xDaoud.url_shortener.model.ShortLink;
import com.xDaoud.url_shortener.repository.ShortLinkRepository;
import org.springframework.stereotype.Service;

@Service
public class ShortLinkService {
    ShortLinkRepository shortLinkRepository;
    public ShortLinkService(ShortLinkRepository shortLinkRepository) {
        this.shortLinkRepository = shortLinkRepository;
    }

    public String getOriginalUrl(String hash) {
        ShortLink isFound = shortLinkRepository.findByHash(hash);
        if(isFound == null) {
            throw new IllegalArgumentException("Hash not found");
        }
        else {
            return isFound.getUrl();
        }
    }
}
