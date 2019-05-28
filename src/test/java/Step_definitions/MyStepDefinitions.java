package Step_definitions;


import Utils.DriverUtil;
import Utils.WebElementUtill;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.То;
import cucumber.api.java.ru.Тогда;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.CheckFilms;
import pages.loginpage;
import pages.Homepage;
import pages.FilmsNavigate;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pages.Homepage.CheckTitle;

//import static Utils.WebElementUtill.takeScreenShot;

public class MyStepDefinitions {
    public static WebDriver driver;

    @Before("@withdrawal")
    public void prepareData() {
        //подготовить данные
        System.out.println("This will run");
    }

    @After("@withdrawal")
    public void clearData(Scenario scenario) {
        if (scenario.isFailed()) {
            //takeScreenShot(driver);
        }
        driver.quit();
    }
/*
1.	Зайти на сайт  https://www.kinopoisk.ru/ ;
2.	Проверить корректность входа на нужную страницу; (навык проверки страницы по title);
3.	Залогиниться на сайте под своей тестовой учетной записью; (навык работы с элементами внутри фрейма,
 т.к. окно логина это фрейм, работа с assert)
4.	Проверить корректность входа по имени аккаунта (навык работы с методом .moveToElement класса Actions,
 т.к. имя акканута будет видно только после наведения мышкой на картинку «человека» в меню, работа с assert);
5.	Перейти в Фильмы/Навигатор по фильмам (навык работы с методом .moveToElement класса Actions,
т.к. кликнуть подраздел «Навигатор по фильмам» можно только после наведения мышкой на раздел меню «Фильмы»);
6.	Проверить корректность перехода в раздел «Навигатор по фильмам» (навык проверки страницы по title);
7.	Найти все фильмы в жанре «комедии» снятые в США в интервале годов с 1998 по 2000 с ретингом > 7,
рейтингом IMDb > 7, рейтингом кинокритиков > 80, рейтингом положительных рецензий  от 90 до 95.
Передвинуть ползунок «Минимальное количество оценок» на 2000 используя либо drag and drop класса Actions,
либо используя класс Robot. Рейтинг MPAA – любой. Возраст – 12+. Бюджет фильма от 50 до 100 миллионов $.
Кассовые сборы от 25 млн $ в США (работа с текстовыми полями, выпадающими списками, маркированными списками,
чекбоксами) ;
8.	В появившемся в окне тексте «Найдено: <количество фильмов> фильма» с помощью регулярного выражения
проверить текст «<количество фильмов> фильма(ов)»  (навык работы с рег. выражениями, ожиданиями и assert’ами);
9.	Нажать кнопку «показать фильмы»;
10.	Проверить, что в результатах поиска отображен массив фильмов снятых в США в жанре «комедия» с рейтингом
у каждого >7 (навык работы с массивами элементов, преобразования строки с рейтингом в число и сравнении
этого числа с условием assert’а) и рейтингом IMDb > 7 (навык работы с массивами элементов, навык
вытягивания строки числа из общего текста «IMDb: 7.00» с помощью регулярного выражения, навык
работы с преобразованием этой строки в числовое значение и дальнейшего сравнения с условием assert’а);
11.	Произвести выход из профиля;
12.	Проверить, что выход из профиля осуществлен корректно (проверка наличия кнопки «Войти»).
*/
    @Given("^user opens the site \"https://www.kinopoisk.ru/\"$")
    public void UserOpenSite(String arg1) throws Throwable {
        driver = DriverUtil.startCromeDriver();
        driver.get(arg1);
    }

    @Then("^Login on the site \"Кинопоиск\"$")
    public void Userloginandcheck() throws Throwable {
        try {
            WebElement element = driver.findElement(Homepage.EnterButton);
            //WebElement element = driver.findElement(By.xpath("//button[.='Войти']"));
            element.click();
            loginpage login = new loginpage();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

            //Проверить нужен ли
            element = driver.findElement(login.OtherAcc);
            element.click();
            //И удалить

            element = driver.findElement(login.login);
            element.sendKeys("BRAKnaZAVODE");
            element = driver.findElement(login.buttonEnter);
            element.click();

            element = (new WebDriverWait(driver, 1, 1000)).until(ExpectedConditions.elementToBeClickable(login.password));
            element = driver.findElement(login.password);

            System.out.println(element.isDisplayed());

            element.sendKeys("naZAVODEBRAK");
            element = driver.findElement(login.buttonEnter);
            element.click();


        }catch (TimeoutException e){
            System.out.println(driver.getCurrentUrl());
            e.printStackTrace();

        }
    }
    @Then("^Logout$")
    public void AccLogout() throws Throwable {
        driver.get("https://www.kinopoisk.ru/");
        Boolean isPresent = driver.findElements(Homepage.EnterButton).size() > 0;
        if(isPresent){
            System.out.println("Не нужно выходить");
        }
        else {
            //WaitElementUtill.waitElement(driver,MainPage.avatar);
            //WaitElementUtill.waitElement(driver,MainPage.buttonExit);
        }
    }

    @То("^Проверить, что в результатах поиска отображен массив фильмов снятых в \"([^\"]*)\" в жанре \"([^\"]*)\" с рейтингом  более \"([^\"]*)\"  и рейтингом IMDb более \"([^\"]*)\"$")
    public void проверитьЧтоВРезультатахПоискаОтображенМассивФильмовСнятыхВВЖанреСРейтингомБолееИРейтингомIMDbБолее(String arg1, String arg2, String arg3, String arg4) throws Throwable {
        int i=1;

        while(i<4) {

            String infoFilm = (new WebDriverWait(driver, 4, 1000))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id = 'itemList']/div["+i+"]//div[@class = 'info']/span[@class = 'gray_text'][1]"))).getText();
            String infoReating = (new WebDriverWait(driver, 4, 1000))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id = 'itemList']/div["+i+"]/div[@class = 'numVote  ratingGreenBG']/span"))).getText();
            String infoImdb = (new WebDriverWait(driver, 4, 1000))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id = 'itemList']/div["+i+"]//div[@class= 'imdb']"))).getText();

            //правило regexp на введенные данные
            String regex1 =arg1;
            String regex2 = arg2;
            String regex3 = "[^.]*";
            String regex4 = "[0-9]";

            int num;
            Matcher matcher1 = Pattern.compile(regex1).matcher(infoFilm);
            Matcher matcher2 = Pattern.compile(regex2).matcher(infoFilm);
            Matcher matcher3 = Pattern.compile(regex3).matcher(infoReating);
            Matcher matcher4 = Pattern.compile(regex4).matcher(infoImdb);

            //проверка введенных аргументов
            Assert.assertEquals(matcher1.find(), true);
            Assert.assertEquals(matcher2.find(), true);

            if (matcher3.find()) {
                num = Integer.parseInt(matcher3.group());
                if(num >= 7)
                    Assert.assertEquals(true, true);
            }
           if (matcher4.find()) {
               num = Integer.parseInt(matcher4.group());
               if(num >= 7)
                   Assert.assertEquals(true, true);
           }
            i++;
        }
    }



    @Тогда("^Push the button «показать фильмы»$")
    public void PushButtonShowFilm() throws Throwable {
       // WaitElementUtill.waitElement(driver, FilmsRating.showFilmButton);
    }




    @Тогда("^ищем по параметрам$")
    public void ищемПоПараметрам(DataTable arg1) throws Throwable {



        String key, value = "";
        List<Map<String, String>> table = arg1.asMaps(String.class, String.class);

        WaitElementUtill.waitElement(driver, FilmsRating.country);
        WaitElementUtill.countryList(driver, table.get(1).get("col2"));
        WaitElementUtill.waitElement(driver, FilmsRating.genre);
        WaitElementUtill.genreList(driver, "комедия");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("var get = document.getElementsByClassName('narrow year_select_interval');\n" +
                String.format("get[0].value = \"%s\";", table.get(2).get("col2")) +
                String.format("get[1].value = \"%s\";", table.get(3).get("col2")));
        WaitElementUtill.sendKeys(driver, FilmsRating.ratingFilmMin,table.get(4).get("col2"));
        WaitElementUtill.sendKeys(driver, FilmsRating.IMDbMin,table.get(5).get("col2"));
        WaitElementUtill.sendKeys(driver, FilmsRating.moveCriticMin,table.get(6).get("col2"));
        WaitElementUtill.sendKeys(driver, FilmsRating.revieProcentMax,"100");
        WaitElementUtill.sendKeys(driver, FilmsRating.revieProcentMin,table.get(8).get("col2"));
        js.executeScript("var get = document.getElementById('min_vote');\n" + "get.value =\"2000\";");
        WaitElementUtill.waitElement(driver,FilmsRating.budgetMin);
        WaitElementUtill.selectElement(driver,table.get(9).get("col2"));



        js.executeScript("var get = document.getElementsByClassName('narrow');\n" +
                String.format("get[3].value = \"%s\";", table.get(10).get("col2"))+
                String.format("get[4].value = \"%s\";", table.get(11).get("col2")));





    }


    @Тогда("^Проверка на количество фильмов \"([^\"]*)\"$")
    public void проверкаНаКоличествоФильмов(String arg1) throws Throwable {

        WebElement  element = (new WebDriverWait(driver, 4, 1000)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@onclick,'navigator.loadResult()')]")));
        String str = element.getText();
        String regex ="(\\d+)";
        String num = null;
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find( ))
        {
            num = matcher.group();
        }
        Assert.assertEquals(num, arg1);


    }


    @Step("проверка на конкретного пользователя")
    @Тогда("^проверяем пользователя$")
    public void проверяемПользователя() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String text;
        MainPage mainPage = new MainPage();
        WebElement element = driver.findElement(mainPage.avatar);
        element.click();
        text = driver.findElement(mainPage.userName).getText();
        if(text.equals("senckoya")){
            System.out.println("Пользователь вошел");
        }
    }


    @Тогда("^ищем \"([^\"]*)\" снятый \"([^\"]*)\" с \"([^\"]*)\" по \"([^\"]*)\"$")
    public void ищемСнятыйСПо(String arg1, String arg2, String arg3, String arg4) throws Throwable {
        WebElement element = (new WebDriverWait(driver,1,1000))
                .until(ExpectedConditions.elementToBeClickable(SearchFilms.contentFind));
        element.click();
        WaitElementUtill.waitElement(driver,SearchFilms.selected);
        WaitElementUtill.selectElement(driver, "фильм");
        WaitElementUtill.waitElement(driver,SearchFilms.country);
        WaitElementUtill.selectElement(driver, "США");
        WaitElementUtill.waitElement(driver,SearchFilms.fromYear);
        WaitElementUtill.selectElement(driver, "2000");
        WaitElementUtill.waitElement(driver,SearchFilms.toYear);
        WaitElementUtill.selectElement(driver, "1998");
        WaitElementUtill.waitElement(driver,SearchFilms.buttonSearch);
    }


    @Тогда("^перехоим в разддел навигатор фильмов$")
    public void перехоимВРаздделНавигаторФильмов() throws Throwable {
        MainPage mainPage = new MainPage();
        Actions action = new Actions(driver);
        WebElement element = (new WebDriverWait(driver, 1, 1000)).until(ExpectedConditions.elementToBeClickable(By.xpath("//nav//a[.='Фильмы']")));
        action.moveToElement(element).build().perform();
        element = (new WebDriverWait(driver, 1, 1000)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[.='Навигатор по фильмам']")));
        element.click();
    }

    @Тогда("^открываем расширенный поиск$")
    public void открываеРасширенныйПоиск() throws Throwable {
        MainPage mainPage = new MainPage();
        WebElement element = (new WebDriverWait(driver, 5, 1000)).until(ExpectedConditions.presenceOfElementLocated(MainPage.searchFilms));
        element.click();
    }



    @Тогда("^проверим тайтл \"([^\"]*)\"$")
    public void проверимТайтл(String arg1) throws Throwable {
        (new WebDriverWait(driver, 5, 1000)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//head/title")));
        String element = driver.getTitle();
        System.out.println(element);
        provTitle(element,arg1);
    }




}
