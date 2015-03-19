package com.gofore.gofurl.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Document(collection = "urls")
public class ShortUrl {

    @Id
    private String hash;

    @Indexed(unique = true)
    private String url;

    private Integer saves;

    private Integer hits;

    public ShortUrl() { }

    public ShortUrl(String hash, String url) {
        this.hash = hash;
        this.url = url;
    }

    @JsonView(Summary.class)
    public String getHash() {
        return hash;
    }

    @JsonView(Summary.class)
    public String getUrl() {
        return url;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public Optional<Integer> getSaves() {
        return Optional.ofNullable(saves);
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public Optional<Integer> getHits() {
        return Optional.ofNullable(hits);
    }

    public static interface Summary { }
}
