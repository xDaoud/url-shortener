package com.xDaoud.url_shortener.service;

import com.xDaoud.url_shortener.model.ShortLink;
import com.xDaoud.url_shortener.repository.ShortLinkRepository;
import com.xDaoud.url_shortener.utility.Base62;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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

    @Transactional
    public ShortLink createShortLink(String originalUrl) {
        ShortLink shortLink = new ShortLink();
        shortLink.setUrl(originalUrl);
        shortLinkRepository.save(shortLink);

        String shortCode = Base62.encode(shortLink.getId());
        shortLink.setHash(shortCode);
        shortLinkRepository.save(shortLink);

        return shortLink;
    }

}
