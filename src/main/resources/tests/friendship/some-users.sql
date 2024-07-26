-- тут можно разместить данные для тестов
merge into users (user_id, birthday, login, name, email)
values (0,'1988-12-28', 'testLogin', 'nameOfZeroUser' ,'yulkaTEST@ya.ru');

merge into users (user_id, birthday, login, name, email)
values (1,'1989-12-28', 'testLoginOne', 'nameOfFirstUser' ,'yulkaTESTOne@ya.ru');

merge into users (user_id, birthday, login, name, email)
values (2,'1989-12-28', 'testLoginTwo', 'nameOfSecondUser' ,'yulkaTESTTwo@ya.ru');

merge into users (user_id, birthday, login, name, email)
values (3,'1989-12-28', 'testLoginThree', 'nameOfThirdUser' ,'yulkaTESTThree@ya.ru');