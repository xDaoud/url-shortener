CREATE TABLE IF NOT EXISTS short_links (
  id BIGSERIAL PRIMARY KEY,
  url TEXT NOT NULL ,
  hash VARCHAR(10) NOT NULL
);

CREATE UNIQUE INDEX idx_short_links_hash ON short_links (hash);