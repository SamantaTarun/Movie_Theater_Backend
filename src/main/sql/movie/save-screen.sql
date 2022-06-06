INSERT INTO screens(title, capacity)
VALUES (:title, :capacity)
returning *;
