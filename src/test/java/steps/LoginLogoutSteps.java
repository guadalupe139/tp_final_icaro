package steps;

import base.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.HomePage;
import pages.LoginPage;

public class LoginLogoutSteps {

    private HomePage homePage;
    private LoginPage loginPage;

    // inicializacion de pagina de inicio para todos los escenarios de login y logout
    private void inicializarPages() {
        if (homePage == null) {
            homePage = new HomePage(BaseTest.getDriver());
        }
        if (loginPage == null) {
            loginPage = new LoginPage(BaseTest.getDriver());
        }
    }

    // validar pagina de inicio
    @Given("que estoy en la pagina principal de OpenCart")
    public void queEstoyEnLaPaginaPrincipalDeOpenCart() {
        inicializarPages();
        homePage.validarUrlContiene("opencart.abstracta.us");
    }

    @Then("valido que el logo y el titulo de la pagina de inicio son correctos")
    public void validoQueElLogoYElTituloDeLaPaginaDeInicioSonCorrectos() {
        Assert.assertTrue(homePage.validarLogoVisible(), "El logo de OpenCart no es visible.");
        Assert.assertTrue(BaseTest.getDriver().getTitle().contains("Your Store"), "El título de la página no es correcto.");
    }

    // login exitoso
    @When("ingreso a la pagina de Login")
    public void ingresoALaPaginaDeLogin() {
        inicializarPages();
        loginPage = homePage.irALoginPage();
    }

    @When("ingreso el email {string} y password {string}")
    public void ingresoElEmailYPassword(String email, String password) {
        loginPage.ingresarCredenciales(email, password);
    }

    @When("hago click en el boton de Login")
    public void hagoClickEnElBotonDeLogin() {
        loginPage.hacerClickEnLogin();
    }

    @Then("el login es exitoso y soy redirigido a My Account")
    public void elLoginEsExitosoYSoyRedirigidoAMyAccount() {
        Assert.assertTrue(loginPage.validarLoginExitoso(), "El login falló o no se redirigió a My Account.");
    }

    // login incorrecto
    @Then("veo un mensaje de advertencia indicando que el login es incorrecto")
    public void veoUnMensajeDeAdvertenciaIndicandoQueElLoginEsIncorrecto() {
        Assert.assertTrue(loginPage.seMuestraAdvertenciaLoginIncorrecto(),
                "No se mostró el mensaje de advertencia de login incorrecto.");
    }

    // logout
    @When("hago click en Logout")
    public void hagoClickEnLogout() {
        homePage.realizarLogout();
    }

    @Then("soy redirigido a la pagina de logout y el link de Logout desaparece")
    public void soyRedirigidoALaPaginaDeLogoutYElLinkDeLogoutDesaparece() {
        homePage.validarUrlContiene("logout");

        Assert.assertTrue(BaseTest.getDriver().getPageSource().contains("You have been logged off your account"),
                "No se validó el mensaje de logout exitoso.");
    }
}