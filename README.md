URL shortener service
=====================

Example application demonstrating micro service architecture and SaaS deployment using Spring
Boot and Heroku.


### Application architecture

The application contains a REST API to submit URL shortening requests and an API that redirects
shortened URLs to the actual ones. Application utilizes Spring Boot autoconfiguration and the
starter modules. URLs and the shortened hashes are stored to MongoDB using Spring Data MongDB.


### Building and running

Spring Boot Maven plugin creates "fat" jar that can be started using just `java -jar <file>`
command.

```shell
mvn package
java -jar target/gofurl.jar
```


### Endpoints

```
POST  / HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded

url=http%3A%2F%2Fgoogle.fi


{
  "hash": "9267c490",
  "url": "http://google.fi"
}
```

```
GET /9267c490 HTTP/1.1
Host: localhost:8080


Content-Length:0
Location:http://google.fi
```

```
GET /9267c490/stats HTTP/1.1
Host: localhost:8080


{
  "hash": "9267c490",
  "url": "http://google.fi",
  "saves": 5,
  "hits": 3
}
```


### Heroku

Heroku Toolbelt is required for Heroku interactions.

Install as follows for Ubuntu/Debian:
```shell
wget -qO- https://toolbelt.heroku.com/install-ubuntu.sh | sh
```

Login to Heroku:
```
heroku login
```

Create a new Heroku application from the project and add Heroku as a remote repository:
```shell
heroku create gofurl --region eu --buildpack https://github.com/trautonen/heroku-buildpack-oracle-java
```

Set Heroku to use `heroku` Spring profile:
```shell
heroku config:set spring.profiles.active=heroku
```

Add MongoLab, Logentries and New Relic add-ons:
```shell
heroku addons:add mongolab
heroku addons:add logentries
heroku addons:add newrelic:stark
```

Push code changes to Heroku and trigger slug build:
```shell
git push heroku master
```


### License

The project gofurl is licensed under New BSD License. See LICENSE.BSD for details.
