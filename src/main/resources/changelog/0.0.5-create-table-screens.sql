CREATE TABLE screens(
id SERIAL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
capacity int NOT NULL
);
INSERT INTO screens(title, capacity) VALUES ('Screen 1', 100);

