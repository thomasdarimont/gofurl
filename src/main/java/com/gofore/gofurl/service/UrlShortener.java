package com.gofore.gofurl.service;

import com.gofore.gofurl.domain.InvalidUrlException;
import com.gofore.gofurl.domain.ShortUrl;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UrlShortener {

    public ShortUrl shortenUrl(String url) {
        return parseUrl(url)
                .map(u -> new ShortUrl(Hashing.murmur3_32().hashString(u, StandardCharsets.UTF_8).toString(), u))
                .orElseThrow(InvalidUrlException::new);
    }

    public Optional<String> parseUrl(String value) {
        try {
            return Optional.of(new URL(value).toURI().normalize().toString());
        } catch (MalformedURLException | URISyntaxException ex) {
            return Optional.empty();
        }
    }
}
