package pages;

import org.openqa.selenium.By;

public class Homepage {

    //Before LogIn
    public static By EnterButton = By.xpath("//button[.='Войти']");
    public static By SearchFilms = By.xpath("//a[@data-tooltip='Расширенный поиск']");

    //Очередная интересное видимо-невидимое меню
    public static By NavigateFilms = By.xpath("//li/a[.='Навигатор по фильмам']");
    //Если понадобится Основная кнопка фильмы
    public static By FilmsButton = By.xpath("//li/a[.='Фильмы'][contains(@class,'content')]");

    //After LogIn
    //!!!!не нужен, так как меню и так есть
    public static By avatar = By.xpath("//span[contains(@class,'avatar')]");

    //Интересно скрыто-нескрытое меню

    public static By userName = By.xpath("//a[contains(@class,'username')]/div[contains(@class,'user')]");
    public static By Exitbutton = By.xpath("//div[.='Выйти']");

    //GitHub testing jlklklkdasdsad
//change!!!!!!!!!!!!!!!!!!!!!лол

    public static void CheckTitle(String textEquals,String text){
        if(textEquals.equals(text)) {
            System.out.println("ok");
        }
        else {
            System.out.println("none");
        }
    }

}
