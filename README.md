
# java-filmorate
repository for Filmorate project.

### Краткое описание задачи:
Бэкенд для сервиса, который будет
1) работать с фильмами и оценками пользователей, а также возвращать топ-10 фильмов,   
   рекомендованных к просмотру.
2) отображать список друзей, добавлять пользователей в друзья, удалять из друзей, выводить список общих друзей


### Автор:
Юлия А.

### Список файлов:

| Пакет | Пакет |Файл |Описание |
|--|--|--|--|
|main.java.filmorate||||
||controller||Контроллеры|
|||ExceptionHandler||
|||FilmController||
|||UserController||
||service||Сервисы, для операций с хранилищами данных|
|||FilmService||
|||UserService||
||exception||Исключения|
|||DuplicatedDataException||
|||IllegalAccessToModelException||
|||NotFoundException||
||storage||Хранение данных, операции с объектами данных|
|||FilmStorage||
|||InMemoryFilmStorage||
|||InMemoryUserStorage||
|||Storage||
|||UserStorage||
| |model| |Модели данных|
| ||Create||
| ||ErrorResponse||
| ||Film||
| ||ReleaseDateConstraint||
| ||ReleaseDateValidator||
| ||StorageData||
| ||Update||
| ||User||
|||FilmorateApplication||
|main.resources||||
|||application.properties||
|test.java.filmorate||||
|||ExpectedViolation||
|||FilmTest||
|||UserTest||
|||storage||
|||InMemoryUserStorageTest||

| |README.md  |описание проекта|

### Автор:
Юлия А.