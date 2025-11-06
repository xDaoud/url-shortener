package com.xDaoud.url_shortener.service;

import com.xDaoud.url_shortener.model.ShortLink;
import com.xDaoud.url_shortener.repository.ShortLinkRepository;
import org.springframework.stereotype.Service;

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

    public ShortLink createShortLink(String originalUrl) {
        String baseHash = generateHash(originalUrl);
        String finalHash = findUniqueHash(baseHash);
        ShortLink shortLink = new ShortLink(originalUrl, finalHash);
        shortLinkRepository.save(shortLink);
        return shortLink;
    }
    private String generateHash(String originalUrl) {
        String input = originalUrl + System.nanoTime();
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(input.getBytes());
            return Base64.getUrlEncoder().encodeToString(hash).substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    private String findUniqueHash(String baseHash) {
        String tempHash = baseHash;
        int attempts = 0;
        while(shortLinkRepository.findByHash(tempHash) != null) {
            tempHash = baseHash + "-" + attempts;
            attempts++;
        }
        return tempHash;
    }
}
