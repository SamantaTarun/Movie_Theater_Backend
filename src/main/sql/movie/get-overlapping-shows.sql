select count(*) from shows
where  not  (
start_time > :show_end_time
or
start_time + ((select duration from movies where id = movie_id)||'minutes')::interval < :show_start_time
);
