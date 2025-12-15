Feature: Proceso de Compra - Agregar al Carrito

  @AgregarCarrito
    Scenario: 7. Agregar un producto al carrito y validar cantidad
      Given que estoy en la pagina principal de OpenCart
      When ingreso a la pagina de Login
      And ingreso el email "pruebag123@gmail.com" y password "prueba123"
      And hago click en el boton de Login
      Then el login es exitoso y soy redirigido a My Account
      And que el carrito esta vacio

      When el usuario navega a la categoria Cameras
      And selecciona el producto "Canon EOS 5D"
      And selecciona la opcion "Red" e ingresa "3" unidades
      And hace click en el boton Add to Cart
      Then el producto es agregado con exito y navego al carrito
      And valido que el producto y la cantidad esten correctos en el carrito