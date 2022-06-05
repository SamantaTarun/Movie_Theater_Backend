UPDATE seats
SET booked = TRUE
WHERE show_id = :show_id AND seat_no = :seat_no;
