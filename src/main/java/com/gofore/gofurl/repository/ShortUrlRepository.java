package com.gofore.gofurl.repository;

import java.util.Optional;

import com.gofore.gofurl.domain.ShortUrl;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.CrudRepository;

public interface ShortUrlRepository extends CrudRepository<ShortUrl, String> {

    Optional<ShortUrl> findByHash(String hash);

	Optional<ShortUrl> findOneAndModifyByHash(String hash, Update update);

	default Optional<ShortUrl> findAndTag(String hash){
		return findOneAndModifyByHash(hash, new Update().inc("hits", 1));
	}

	default ShortUrl saveAndTag(ShortUrl shortUrl) {
		return findOneAndModifyByHash(shortUrl.getHash(), new Update().set("url", shortUrl.getUrl()).inc("hits", 1)).get();
	}

	// need to find a way to propagate FindAndModifyOptions

	/*
    public Optional<ShortUrl> findAndTag(String hash) {
        Update update = new Update().inc("hits", 1);
        return Optional.ofNullable(findAndModify(hash, update, false));
    }
    */

	/*
    public ShortUrl saveAndTag(ShortUrl shortUrl) {
        Update update = new Update().set("url", shortUrl.getUrl()).inc("saves", 1);
        return findAndModify(shortUrl.getHash(), update, true);
    }
	*/

	/*
    private Query query(String hash) {
        return Query.query(Criteria.where("hash").is(hash));
    }
    */

	/*
    private ShortUrl findAndModify(String hash, Update update, boolean upsert) {
        FindAndModifyOptions options = FindAndModifyOptions.options().upsert(upsert).returnNew(true);
        return mongo.findAndModify(query(hash), update, options, ShortUrl.class);
    }*/
}
