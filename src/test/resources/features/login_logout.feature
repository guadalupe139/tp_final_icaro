Feature: Autenticacion y Validaciones Basicas

  @LoginExitoso
  Scenario: 1. Login exitoso
      Given que estoy en la pagina principal de OpenCart
      When ingreso a la pagina de Login
      And ingreso el email "pruebag123@gmail.com" y password "prueba123"
      And hago click en el boton de Login
      Then el login es exitoso y soy redirigido a My Account

  @LoginIncorrecto
  Scenario: 2. Login incorrecto con credenciales invalidas
      Given que estoy en la pagina principal de OpenCart
      When ingreso a la pagina de Login
      And ingreso el email "usuario_falso@error.com" y password "clave_mala"
      And hago click en el boton de Login
      Then veo un mensaje de advertencia indicando que el login es incorrecto

  @LogoutExitoso
  Scenario: 3. Logout exitoso
      Given que estoy en la pagina principal de OpenCart
      When ingreso a la pagina de Login
      And ingreso el email "pruebag123@gmail.com" y password "prueba123"
      And hago click en el boton de Login
      And hago click en Logout
      Then soy redirigido a la pagina de logout y el link de Logout desaparece

  @ValidacionInicial
  Scenario: 4. Validar página de Inicio
    Given que estoy en la pagina principal de OpenCart
    Then valido que el logo y el titulo de la pagina de inicio son correctos