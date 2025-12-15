package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    // localizadores
    private final By campoEmail = By.id("input-email");
    private final By campoPassword = By.id("input-password");
    private final By botonLogin = By.xpath("//input[@value='Login']");
    private final By textoAdvertencia = By.xpath("//div[contains(@class,'alert-danger')]");
    private final By tituloMiCuenta = By.xpath("//h2[text()='My Account']");

    public LoginPage(WebDriver driver) {
        super(driver); // llamada al constructor de BasePage
    }

    public void ingresarCredenciales(String email, String password) {
        ingresarTexto(campoEmail, email);
        ingresarTexto(campoPassword, password);
    }

    public void hacerClickEnLogin() {
        hacerClick(botonLogin);
    }

    // validaciones
    public boolean validarLoginExitoso() {
        return esVisible(tituloMiCuenta);
    }

    public boolean seMuestraAdvertenciaLoginIncorrecto() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        try {
            WebElement advertencia = wait.until(ExpectedConditions.visibilityOfElementLocated(textoAdvertencia));
            return advertencia.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String obtenerMensajeAdvertencia() {
        return obtenerTexto(textoAdvertencia);
    }
}