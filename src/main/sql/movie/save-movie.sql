INSERT INTO movies(title, duration, language, price)
VALUES (:title, :duration, :language, :price)
returning *;
