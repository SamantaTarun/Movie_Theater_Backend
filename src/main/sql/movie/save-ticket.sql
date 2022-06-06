INSERT INTO ticket(user_email, seat_number, show_id)
values(:user_email, :seat_number, :show_id)
returning *;
