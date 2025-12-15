package pages;

import base.BasePage;
import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CarritoPage extends BasePage {

    private final By nombreProducto = By.xpath("//table//td[@class='text-left']//a");
    private final By campoCantidad = By.xpath("//table//input[@type='text']");
    private final By textoAdvertenciaVacio = By.xpath("//p[text()='Your shopping cart is empty!']");

    public CarritoPage(WebDriver driver) {
        super(driver);
    }

    public void esperarCarritoListo() {
        WebDriverWait wait = new WebDriverWait(BaseTest.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("checkout/cart"));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(nombreProducto),
                ExpectedConditions.presenceOfElementLocated(textoAdvertenciaVacio)
        ));
    }

    public String obtenerNombreProducto() {
        esperarCarritoListo();
        if (validarCarritoVacio()) {
            throw new RuntimeException("El carrito está vacío; no hay producto para leer.");
        }

        WebDriverWait wait = new WebDriverWait(BaseTest.getDriver(), Duration.ofSeconds(10));
        WebElement nombre = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div#content table.table.table-bordered td.text-left a")
        ));

        // forzar visibilidad con scroll
        ((JavascriptExecutor) BaseTest.getDriver()).executeScript("arguments[0].scrollIntoView(true);", nombre);

        return nombre.getText().trim();
    }

    public String obtenerCantidadProducto() {
        esperarCarritoListo();
        if (validarCarritoVacio()) {
            throw new RuntimeException("El carrito está vacío; no hay cantidad para leer.");
        }

        WebDriverWait wait = new WebDriverWait(BaseTest.getDriver(), Duration.ofSeconds(10));
        WebElement cantidadInput = wait.until(ExpectedConditions.presenceOfElementLocated(campoCantidad));
        ((JavascriptExecutor) BaseTest.getDriver()).executeScript("arguments[0].scrollIntoView(true);", cantidadInput);
        wait.until(ExpectedConditions.visibilityOf(cantidadInput));
        return cantidadInput.getAttribute("value").trim();
    }

    public boolean validarCarritoVacio() {
        try {
            return BaseTest.getDriver().findElement(textoAdvertenciaVacio).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void vaciarCarrito() {
        List<WebElement> botonesEliminar = BaseTest.getDriver()
                .findElements(By.xpath("//button[@data-original-title='Remove']"));
        for (WebElement boton : botonesEliminar) {
            boton.click();
            new WebDriverWait(BaseTest.getDriver(), Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOf(boton));
        }
    }

    public void clickContinue() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement botonContinue = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//a[@class='btn btn-primary' and contains(text(),'Continue')]")
                    )
            );
            try {
                botonContinue.click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", botonContinue);
            }
        } catch (TimeoutException e) {
            System.out.println("No apareció el botón Continue.");
        }
    }
}