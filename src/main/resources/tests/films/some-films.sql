-- тут можно разместить данные для тестов
merge into films (film_id, releaseDate, description, name, duration, mpa_id)
values (0,'1988-12-28', 'descriptionZeroFilm', 'nameOfZeroFilm', 34L, 1L);

merge into films (film_id, releaseDate, description, name, duration, mpa_id)
values (1,'1989-12-28', 'descriptionFirstFilm', 'nameOfFirstFilm', 35L, 2L);
