package com.xDaoud.url_shortener.service;

import com.xDaoud.url_shortener.model.ShortLink;
import com.xDaoud.url_shortener.repository.IdSequence;
import com.xDaoud.url_shortener.repository.ShortLinkRepository;
import com.xDaoud.url_shortener.utility.Base62;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class ShortLinkService {
    ShortLinkRepository shortLinkRepository;
    IdSequence idSequence;
    public ShortLinkService(ShortLinkRepository shortLinkRepository, IdSequence idSequence) {
        this.shortLinkRepository = shortLinkRepository;
        this.idSequence = idSequence;
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
        if(!isValidUrl(originalUrl)) {
            throw new IllegalArgumentException("url is invalid");
        }
        ShortLink isFound = shortLinkRepository.findByUrl(originalUrl);
        if(isFound != null) {
            return isFound;
        }
        ShortLink shortLink = new ShortLink();
        long id = idSequence.nextId() + 1000000;
        String shortCode = Base62.encode(id);

        shortLink.setUrl(originalUrl);
        shortLink.setHash(shortCode);
        shortLinkRepository.save(shortLink);

        return shortLink;
    }

    private boolean isValidUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        try {
            URI uri = new URI(url).parseServerAuthority();
            return true;
        } catch (URISyntaxException | IllegalArgumentException e) {
            return false;
        }
    }

}
