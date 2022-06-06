create table ticket(
id SERIAL PRIMARY KEY,
user_email VARCHAR NOT NULL,
seat_number INT NOT NULL,
show_id INT NOT NULL,
FOREIGN KEY(show_id)
REFERENCES shows(id)
);
