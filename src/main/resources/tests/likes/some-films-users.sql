-- тут можно разместить данные для тестов
merge into films (film_id, releaseDate, description, name, duration, mpa_id)
values (0,'1988-12-28', 'descriptionZeroFilm', 'nameOfZeroFilm', 34L, 1L);

merge into films (film_id, releaseDate, description, name, duration, mpa_id)
values (1,'1989-12-28', 'descriptionFirstFilm', 'nameOfFirstFilm', 35L, 2L);

merge into films (film_id, releaseDate, description, name, duration, mpa_id)
values (2,'1990-12-28', 'descriptionSecondFilm', 'nameOfSecondFilm', 38L, 3L);

merge into films (film_id, releaseDate, description, name, duration, mpa_id)
values (3,'1990-12-28', 'descriptionThirdFilm', 'nameOfThirdFilm', 39L, 4L);

merge into films (film_id, releaseDate, description, name, duration, mpa_id)
values (4,'1990-12-28', 'descriptionFourFilm', 'nameOfFourFilm', 50L, 4L);

-- тут можно разместить данные для тестов
merge into users (user_id, birthday, login, name, email)
values (0,'1988-12-28', 'testLogin', 'nameOfZeroUser' ,'yulkaTEST@ya.ru');

merge into users (user_id, birthday, login, name, email)
values (1,'1989-12-28', 'testLoginOne', 'nameOfFirstUser' ,'yulkaTESTOne@ya.ru');

merge into users (user_id, birthday, login, name, email)
values (2,'1989-12-28', 'testLoginTwo', 'nameOfSecondUser' ,'yulkaTESTTwo@ya.ru');

merge into users (user_id, birthday, login, name, email)
values (3,'1989-12-28', 'testLoginThree', 'nameOfThirdUser' ,'yulkaTESTThree@ya.ru');

merge into users (user_id, birthday, login, name, email)
values (4,'1989-12-28', 'testLoginFour', 'nameOfFourUser' ,'yulkaTESTFour@ya.ru');