```markdown
# URL Shortener

A URL shortener built with Spring Boot that converts long URLs into short, unique codes.

## How to Use

**Shorten a URL:**
```bash
curl -X POST -H "Content-Type: text/plain" -d "https://google.com" http://localhost:8080/api/shorten
```

**Visit Short URL:**
```
http://localhost:8080/api/4C96  # redirects to original URL
```

## Features

- Shorten long URLs to compact codes like `4C96`, `4C97`
- Automatic redirects from short codes to original URLs
- Prevents duplicate entries for the same URL
- Simple REST API

## Tech

- Java + Spring Boot
- PostgreSQL
- Base62 encoding

## Run It

1. Start PostgreSQL
2. Update `application.properties` with your DB details
3. `mvn spring-boot:run`

