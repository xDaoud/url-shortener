package com.xDaoud.url_shortener.model;

public class ShortLink {
    private long id;
    private String url;
    private String hash;
    public ShortLink(String url, String hash) {
        this.url = url;
        this.hash = hash;
    }
    public ShortLink() {}
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
}
