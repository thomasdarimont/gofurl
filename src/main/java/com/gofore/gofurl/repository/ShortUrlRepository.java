package com.gofore.gofurl.repository;

import com.gofore.gofurl.domain.ShortUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ShortUrlRepository {

    private MongoOperations mongo;

    @Autowired
    public ShortUrlRepository(MongoOperations mongo) {
        this.mongo = mongo;
    }

    public Optional<ShortUrl> find(String hash) {
        return Optional.ofNullable(mongo.findOne(query(hash), ShortUrl.class));
    }

    public Optional<ShortUrl> findAndTag(String hash) {
        Update update = new Update().inc("hits", 1);
        return Optional.ofNullable(findAndModify(hash, update, false));
    }

    public ShortUrl saveAndTag(ShortUrl shortUrl) {
        Update update = new Update().set("url", shortUrl.getUrl()).inc("saves", 1);
        return findAndModify(shortUrl.getHash(), update, true);
    }

    private Query query(String hash) {
        return Query.query(Criteria.where("hash").is(hash));
    }

    private ShortUrl findAndModify(String hash, Update update, boolean upsert) {
        FindAndModifyOptions options = FindAndModifyOptions.options().upsert(upsert).returnNew(true);
        return mongo.findAndModify(query(hash), update, options, ShortUrl.class);
    }
}
