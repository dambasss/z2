package pages;

import org.openqa.selenium.By;

public class Homepage {

    //Before LogIn
    public static By EnterButton = By.xpath("//button[.='�����']");
    public static By SearchFilms = By.xpath("//a[@data-tooltip='����������� �����']");

    //��������� ���������� ������-��������� ����
    public static By NavigateFilms = By.xpath("//li/a[.='��������� �� �������']");
    //���� ����������� �������� ������ ������
    public static By FilmsButton = By.xpath("//li/a[.='������'][contains(@class,'content')]");

    //After LogIn
    //!!!!�� �����, ��� ��� ���� � ��� ����
    public static By avatar = By.xpath("//span[contains(@class,'avatar')]");

    //��������� ������-��������� ����

    public static By userName = By.xpath("//a[contains(@class,'username')]/div[contains(@class,'user')]");
    public static By Exitbutton = By.xpath("//div[.='�����']");

    //GitHub testing jlklklkdasdsad
//change!!!!!!!!!!!!!!!!!!!!!���

    public static void CheckTitle(String textEquals,String text){
        if(textEquals.equals(text)) {
            System.out.println("ok");
        }
        else {
            System.out.println("none");
        }
    }

}
