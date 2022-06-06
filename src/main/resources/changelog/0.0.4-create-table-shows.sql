CREATE TABLE shows(
id SERIAL PRIMARY KEY,
start_time TIMESTAMPTZ NOT NULL,
movie_id int NOT NULL,
FOREIGN KEY(movie_id)
REFERENCES movies(id)
);
