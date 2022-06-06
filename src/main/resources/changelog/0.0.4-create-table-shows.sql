CREATE TABLE shows(
id SERIAL PRIMARY KEY,
start_time TIMESTAMPTZ NOT NULL,
movie_id int NOT NULL,
FOREIGN KEY(movie_id)
REFERENCES movies(id)
);
INSERT INTO shows
(title, start_time, movie_id) VALUES
('Avengers EndGame', '2021-09-05 09:15:00.464396+05:30', 1);
