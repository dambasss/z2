
@withdrawal
Feature:  Check title

@success
Scenario: Check title
  * user opens the site "https://www.kinopoisk.ru/"
  * check title "КиноПоиск — Все фильмы планеты"

@fail
Scenario: Unsuccessful check title
  * user don't open the site "https://www.kinopoisk.ru/"
  * checked title isn't "КиноПоиск — Все фильмы планеты"

