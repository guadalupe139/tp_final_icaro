package pages;

import base.BasePage;
import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductoPage extends BasePage {

    private final By linkCanonEOS5D = By.xpath("//div[@class='product-layout']//a[contains(text(),'Canon EOS 5D')]");
    private final By campoOpcion = By.id("input-option226");
    private final By campoCantidad = By.id("input-quantity");
    private final By botonAddToCart = By.id("button-cart");
    private final By mensajeExito = By.xpath("//div[contains(@class,'alert-success')]");
    private final By botonVerCarrito = By.xpath("//a[@title='Shopping Cart']");
    private final By tituloCategoria = By.xpath("//div[@id='content']//h2");

    public ProductoPage(WebDriver driver) {
        super(driver);
    }

    public String obtenerNombreCategoria() {
        return obtenerTexto(tituloCategoria);
    }

    public void seleccionarProductoPorNombre(String nombreProducto) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By producto = By.xpath("//h4/a[text()='Canon EOS 5D']");

        WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(producto));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elemento);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
    }

    /**
     * selecciona una opcion del producto y establece la cantiadd
     * @param option el valor de la opcion del producto
     * @param cantidad la cantidad a agregar
     */
    public void seleccionarOpcionesYCantidad(String option, String cantidad) {
        ingresarTexto(campoOpcion, option);

        driver.findElement(campoCantidad).clear();
        ingresarTexto(campoCantidad, cantidad);
    }

    public void hacerClickEnAddToCart() {

        hacerClick(botonAddToCart);
    }

    public String obtenerMensajeExitoCarrito() {

        return obtenerTexto(mensajeExito);
    }

    public CarritoPage irACarritoDesdeNotificacion() {
        WebDriverWait wait = new WebDriverWait(BaseTest.getDriver(), Duration.ofSeconds(5));
        try {
            // Intentar esperar el banner de éxito
            WebElement banner = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@class,'alert-success')]")
            ));
            WebElement linkCarrito = banner.findElement(By.xpath(".//a[contains(@href,'checkout/cart')]"));
            ((JavascriptExecutor) BaseTest.getDriver()).executeScript("arguments[0].click();", linkCarrito);
        } catch (TimeoutException e) {
            // Si no aparece el banner, ir directo al menú "Shopping Cart"
            WebElement cartLink = BaseTest.getDriver().findElement(By.linkText("Shopping Cart"));
            ((JavascriptExecutor) BaseTest.getDriver()).executeScript("arguments[0].click();", cartLink);
        }

        // Esperar que la URL sea la del carrito
        wait.until(ExpectedConditions.urlContains("checkout/cart"));
        return new CarritoPage(BaseTest.getDriver());
    }

}