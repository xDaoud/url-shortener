package com.xDaoud.url_shortener.service;

import com.xDaoud.url_shortener.model.ShortLink;
import com.xDaoud.url_shortener.repository.IdSequence;
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
        ShortLink isFound = shortLinkRepository.findByUrl(originalUrl);
        if(isFound != null) {
            return isFound;
        }
        ShortLink shortLink = new ShortLink();
        long id = idSequence.nextId();
        String shortCode = Base62.encode(id);

        shortLink.setUrl(originalUrl);
        shortLink.setHash(shortCode);
        shortLinkRepository.save(shortLink);

        return shortLink;
    }

}
