INSERT INTO shows(start_time, movie_id, price, movieLanguage, movieType)
VALUES (:start_time, :movie_id, :price, :movieLanguage, :movieType)
returning *;
