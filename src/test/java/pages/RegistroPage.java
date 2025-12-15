package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegistroPage extends BasePage {

    // campos de formulario
    private final By campoFirstName = By.id("input-firstname");
    private final By campoLastName = By.id("input-lastname");
    private final By campoEmail = By.id("input-email");
    private final By campoTelephone = By.id("input-telephone");
    private final By campoPassword = By.id("input-password");
    private final By campoConfirm = By.id("input-confirm");

    // opciones y botones
    private final By radioNewsletterNo = By.xpath("//input[@name='newsletter' and @value='0']");
    private final By checkboxPrivacyPolicy = By.xpath("//input[@name='agree']");
    private final By botonContinue = By.xpath("//input[@value='Continue']");

    // mensajes de validacion
    private final By advertenciaFirstName = By.xpath("//*[contains(@class,'text-danger') and contains(text(),'First Name')]");
    private final By advertenciaLastName = By.xpath("//*[contains(@class,'text-danger') and contains(text(),'Last Name')]");
    private final By advertenciaEmail = By.xpath("//*[contains(@class,'text-danger') and contains(text(),'E-Mail')]");
    private final By advertenciaPrivacy = By.xpath("//*[contains(text(),'Privacy Policy')]");

    private final By textoCuentaCreada = By.xpath("//p[contains(text(),'Congratulations! Your new account')]");
    private final By textoAdvertenciaGeneral = By.xpath("//div[contains(@class,'alert-danger')]");

    public RegistroPage(WebDriver driver) {
        super(driver);
    }

    public void completarFormulario(String fn, String ln, String email, String tel, String pwd) {
        ingresarTexto(campoFirstName, fn);
        ingresarTexto(campoLastName, ln);
        ingresarTexto(campoEmail, email);
        ingresarTexto(campoTelephone, tel);
        ingresarTexto(campoPassword, pwd);
        ingresarTexto(campoConfirm, pwd);
        hacerClick(radioNewsletterNo);
        hacerClick(checkboxPrivacyPolicy);
    }

    public void hacerClickEnContinue() {
        hacerClick(botonContinue);
    }

    // validaciones
    public boolean validarAdvertenciaFirstName() {
        return esVisible(advertenciaFirstName);
    }

    public boolean validarAdvertenciaLastName() {
        return esVisible(advertenciaLastName);
    }

    public boolean validarAdvertenciaEmail() {
        return esVisible(advertenciaEmail);
    }

    public boolean validarAdvertenciaPrivacy() {
        return esVisible(advertenciaPrivacy);
    }

    public boolean validarCuentaCreada() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(textoCuentaCreada)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String obtenerMensajeAdvertenciaGeneral() {
        return obtenerTexto(textoAdvertenciaGeneral);
    }
}