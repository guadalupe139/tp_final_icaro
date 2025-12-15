Feature: Creacion de Cuentas de Usuario

  @RegistroExitoso
  Scenario: 5. Crear Cuenta exitoso
    Given que estoy en la pagina principal de OpenCart
    When navego a la pagina de Crear Cuenta
    And completo el formulario con datos validos para un nuevo usuario
    And presiono el boton Continue
    Then la cuenta es creada exitosamente

  @RegistroIncorrecto
  Scenario: 6. Crear Cuenta incorrecto por campos faltantes
    Given que estoy en la pagina principal de OpenCart
    When navego a la pagina de Crear Cuenta
    And completo el formulario dejando campos obligatorios incompletos
    And presiono el boton Continue
    Then veo mensajes de advertencia para campos incompletos