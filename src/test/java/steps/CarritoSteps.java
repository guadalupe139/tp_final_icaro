package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.CarritoPage;
import pages.HomePage;
import pages.ProductoPage;
import base.BaseTest;

import java.time.Duration;

public class CarritoSteps {

    private HomePage homePage;
    private ProductoPage productPage;
    private CarritoPage cartPage;
    private String productoEsperado = "Canon EOS 5D";
    private String cantidadEsperada = "3";

    private void inicializarPages() {
        if (homePage == null) {
            homePage = new HomePage(BaseTest.getDriver());
        }
        if (productPage == null) {
            productPage = new ProductoPage(BaseTest.getDriver());
        }
        if (cartPage == null) {
            cartPage = new CarritoPage(BaseTest.getDriver());
        }
    }

    @And("que el carrito esta vacio")
    public void queElCarritoEstaVacio() {
        inicializarPages();
        BaseTest.getDriver().get("http://opencart.abstracta.us/index.php?route=checkout/cart");

        if (!cartPage.validarCarritoVacio()) {
            cartPage.vaciarCarrito();

            try {
                WebDriverWait wait = new WebDriverWait(BaseTest.getDriver(), Duration.ofSeconds(5));
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//p[contains(text(),'Your shopping cart is empty')]")));
            } catch (TimeoutException e) {
                System.out.println("No se pudo esperar el texto, pero igual se intenta continuar.");
            }
        } else {
            System.out.println("El carrito ya estaba vacío, no se hace nada.");
        }

        cartPage.clickContinue();
    }

    @When("el usuario navega a la categoria Cameras")
    public void elUsuarioNavegaALaCategoriaCameras() {
        inicializarPages();
        homePage.navegarACameras();

        String categoriaActual = productPage.obtenerNombreCategoria();
        Assert.assertEquals(categoriaActual, "Cameras",
                "La página de categoría no cargó correctamente. Texto actual: " + categoriaActual);
    }

    @And("selecciona el producto {string}")
    public void seleccionarProductoPornombre(String nombreProducto) {
        productPage.seleccionarProductoPorNombre(nombreProducto);
    }

    @And("selecciona la opcion {string} e ingresa {string} unidades")
    public void seleccionaLaOpcionEIngresaUnidades(String option, String cantidad) {
        productPage.seleccionarOpcionesYCantidad(option, cantidad);
        cantidadEsperada = cantidad;
    }

    @And("hace click en el boton Add to Cart")
    public void haceClickEnElBotonAddToCart() {
        productPage.hacerClickEnAddToCart();
    }

    @Then("el producto es agregado con exito y navego al carrito")
    public void elProductoEsAgregadoConExitoYNavegoAlCarrito() {
        WebDriverWait wait = new WebDriverWait(BaseTest.getDriver(), Duration.ofSeconds(5));
        try {
            WebElement mensaje = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div.alert.alert-success")
            ));
            Assert.assertTrue(mensaje.getText().contains("Success"),
                    "El mensaje de éxito al agregar al carrito no apareció.");
        } catch (TimeoutException e) {
            System.out.println("El mensaje de éxito no apareció, se valida directamente en el carrito.");
        }

        cartPage = productPage.irACarritoDesdeNotificacion();
        cartPage.validarUrlContiene("checkout/cart");
    }

    @And("valido que el producto y la cantidad esten correctos en el carrito")
    public void validoQueElProductoYLaCantidadEstenCorrectosEnElCarrito() {
        WebDriverWait wait = new WebDriverWait(BaseTest.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("checkout/cart"));

        cartPage.esperarCarritoListo();

        if (cartPage.validarCarritoVacio()) {
            Assert.fail("El carrito quedó vacío y no hay producto para validar.");
        }

        String nombreActual = cartPage.obtenerNombreProducto();
        Assert.assertTrue(nombreActual.contains(productoEsperado),
                "El producto esperado (" + productoEsperado + ") no se encontró en el carrito. Nombre actual: " + nombreActual);

        String cantidadActual = cartPage.obtenerCantidadProducto();
        Assert.assertEquals(cantidadActual.trim(), cantidadEsperada,
                "La cantidad del producto en el carrito es incorrecta. Cantidad actual: " + cantidadActual);
    }

}