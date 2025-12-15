package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.HomePage;
import pages.RegistroPage;
import base.BaseTest;

public class RegistroSteps {

    private HomePage homePage;
    private RegistroPage registerPage;

    private void inicializarPages() {
        if (homePage == null) {
            homePage = new HomePage(BaseTest.getDriver());
        }
        if (registerPage == null) {
            registerPage = new RegistroPage(BaseTest.getDriver());
        }
    }

    @When("navego a la pagina de Crear Cuenta")
    public void navegoALaPaginaDeCrearCuenta() {
        inicializarPages();
        registerPage = homePage.irARegisterPage();
    }

    // crear cuenta exitoso
    @And("completo el formulario con datos validos para un nuevo usuario")
    public void completoElFormularioConDatosValidos() {
        String emailUnico = "testing" + System.currentTimeMillis() + "@test.com";

        registerPage.completarFormulario("Guadalupe", "Atim", emailUnico, "1123456789", "Password123");
    }

    @And("presiono el boton Continue")
    public void presionoElBotonContinue() {
        registerPage.hacerClickEnContinue();
    }

    @Then("la cuenta es creada exitosamente")
    public void laCuentaEsCreadaExitosamente() {
        Assert.assertTrue(registerPage.validarCuentaCreada(),
                "El registro de cuenta no fue exitoso. No se encontró el mensaje de éxito.");
    }

    // crear cuenta incorrecto (faltan campos)
    @And("completo el formulario dejando campos obligatorios incompletos")
    public void completoElFormularioDejandoCamposObligatoriosIncompletos() {
        registerPage.completarFormulario("", "", "", "", "4444");
        registerPage.hacerClickEnContinue();
    }

    @Then("veo mensajes de advertencia para campos incompletos")
    public void veoMensajesDeAdvertenciaParaCamposIncompletos() {
        Assert.assertTrue(registerPage.validarAdvertenciaFirstName(), "No se mostró advertencia de First Name.");
        Assert.assertTrue(registerPage.validarAdvertenciaLastName(), "No se mostró advertencia de Last Name.");
        Assert.assertTrue(registerPage.validarAdvertenciaEmail(), "No se mostró advertencia de Email.");
        Assert.assertTrue(registerPage.validarAdvertenciaPrivacy(), "No se mostró advertencia de política de privacidad.");
    }
}