package com.gofore.gofurl.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.gofore.gofurl.domain.ShortUrl;
import com.gofore.gofurl.domain.UrlNotFoundException;
import com.gofore.gofurl.repository.ShortUrlRepository;
import com.gofore.gofurl.service.UrlShortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UrlShortenerResource {

    private final ShortUrlRepository shortUrlRepository;
    private final UrlShortener urlShortener;

    @Autowired
    public UrlShortenerResource(ShortUrlRepository shortUrlRepository, UrlShortener urlShortener) {
        this.shortUrlRepository = shortUrlRepository;
        this.urlShortener = urlShortener;
    }

    @JsonView(ShortUrl.Summary.class)
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ShortUrl shorten(@RequestParam String url) {
        ShortUrl shortUrl = urlShortener.shortenUrl(url);
        return shortUrlRepository.saveAndTag(shortUrl);
    }

    @RequestMapping(value = "/{hash}", method = RequestMethod.GET)
    public String redirect(@PathVariable String hash) {
        return shortUrlRepository.findAndTag(hash)
                .map(u -> "redirect:" + u.getUrl())
                .orElseThrow(UrlNotFoundException::new);
    }

    @RequestMapping(value = "/{hash}/stats", method = RequestMethod.GET)
    public @ResponseBody ShortUrl stats(@PathVariable String hash) {
        return shortUrlRepository.findByHash(hash)
                .orElseThrow(UrlNotFoundException::new);
    }
}
