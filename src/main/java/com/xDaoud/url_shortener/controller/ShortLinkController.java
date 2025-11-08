package com.xDaoud.url_shortener.controller;

import com.xDaoud.url_shortener.model.ShortLink;
import com.xDaoud.url_shortener.service.ShortLinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class ShortLinkController {
    ShortLinkService shortLinkService;
    public ShortLinkController(ShortLinkService shortLinkService) {
        this.shortLinkService = shortLinkService;
    }

    @GetMapping("/{hash}")
    public ResponseEntity<Void> redirect(@PathVariable String hash) {
        String originalUrl = shortLinkService.getOriginalUrl(hash);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortLink> shorten(@RequestBody String url) {
        ShortLink shortLink = shortLinkService.createShortLink(url);
        String hashedUrl = "http://localhost:8080/" + shortLink.getHash();
        return ResponseEntity.created(URI.create(hashedUrl)).body(shortLink);
    }


}
