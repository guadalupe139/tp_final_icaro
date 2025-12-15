package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    // localizadores
    private final By linkMyAccount = By.xpath("//span[text()='My Account']");
    private final By linkLogin = By.xpath("//a[text()='Login']");
    private final By linkRegister = By.xpath("//a[text()='Register']");
    private final By linkLogout = By.xpath("//a[text()='Logout']");
    private final By linkCameras = By.linkText("Cameras");
    private final By logoOpenCart = By.id("logo");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void hacerClickEnMyAccount() {
        hacerClick(linkMyAccount);
    }

    public LoginPage irALoginPage() {
        hacerClickEnMyAccount();
        hacerClick(linkLogin);
        return new LoginPage(driver);
    }

    public RegistroPage irARegisterPage() {
        hacerClickEnMyAccount();
        hacerClick(linkRegister);
        return new RegistroPage(driver);
    }

    public void navegarACameras() {
        hacerClick(linkCameras);
    }

    public void realizarLogout() {
        hacerClickEnMyAccount();
        hacerClick(linkLogout);
    }

    public boolean validarLogoVisible() {
        return esVisible(logoOpenCart);
    }

    public boolean linkLogoutEsVisible() {
        return esVisible(linkLogout);
    }
}