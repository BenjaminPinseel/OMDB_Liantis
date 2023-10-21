-- create_watchlist_movie_table.sql
CREATE TABLE IF NOT EXISTS omdb.watchlist_movie (
    watchlist_id VARCHAR(255) NOT NULL REFERENCES omdb.watchlist(id),
    movie_id VARCHAR(255) NOT NULL ,
    PRIMARY KEY (watchlist_id, movie_id)
);