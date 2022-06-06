CREATE TABLE screens(
id SERIAL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
capacity int NOT NULL
);
INSERT INTO movies
(title, start_time, end_time) VALUES
('Sample Screen', '2021-09-05 09:15:00.464396+05:30', '2021-09-05 11:30:11.464396+05:30');
