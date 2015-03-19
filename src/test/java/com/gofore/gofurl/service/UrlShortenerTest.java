package com.gofore.gofurl.service;

import com.gofore.gofurl.domain.InvalidUrlException;
import com.gofore.gofurl.domain.ShortUrl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class UrlShortenerTest {

    @Test
    public void shortenValidUrl() {
        ShortUrl shorten = new UrlShortener().shortenUrl("http://www.google.fi");
        Assert.assertEquals("4b7c8dcd", shorten.getHash());
    }

    @Test(expected = InvalidUrlException.class)
    public void shortenInvalidUrl() {
        new UrlShortener().shortenUrl("foo.bar");
    }

    @Test
    public void parseValidUrl() {
        Optional<String> url = new UrlShortener().parseUrl("http://google.fi");
        Assert.assertEquals("http://google.fi", url.get());
    }

    @Test
    public void parseInvalidUrl() {
        Optional<String> url = new UrlShortener().parseUrl("google.fi");
        Assert.assertFalse(url.isPresent());
    }
}