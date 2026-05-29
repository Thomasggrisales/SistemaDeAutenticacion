describe('Flujo Completo de Autenticación y Gestión de Tareas', () => {

  // Generar un usuario único por cada ejecución
  const usuarioUnico = `Thomas${Date.now()}`;

  it('Debería registrar un usuario, iniciar sesión e interactuar con el To-Do', () => {

    // Entrar a la raíz
    cy.visit('http://localhost:3000');

    // Ir a la pantalla de registro
    cy.contains('Registrarse').click();
    cy.url().should('include', '/register');

    // FORMULARIO DE REGISTRO
    cy.get('input[placeholder="Usuario"]').type(usuarioUnico);
    cy.get('input[placeholder="Contraseña"]').type('1234');

    cy.intercept('POST', '**/auth/register').as('registroBackend');
    cy.get('button[type="submit"]').click();
    cy.wait('@registroBackend');

    // REDIRECCIÓN
    cy.visit('http://localhost:3000');

    // FORMULARIO DE LOGIN
    cy.get('input[placeholder="Usuario"]').type(usuarioUnico);
    cy.get('input[placeholder="Contraseña"]').type('1234');

    cy.intercept('POST', '**/auth/login').as('loginBackend');
    cy.contains('Ingresar').click();
    cy.wait('@loginBackend');

    // DASHBOARD
    cy.url().should('include', '/dashboard');
    cy.contains(usuarioUnico).should('be.visible');

    // SALTO AL SERVICIO TO-DO
    cy.get("a[href*='3001']").then(($link) => {
      const href = $link.prop('href');
      cy.visit(href);
    });

    // INTERACCIÓN CON EL TO-DO
    cy.origin('http://localhost:3001', () => {

      // Escudo protector contra errores internos de React
      Cypress.on('uncaught:exception', (err, runnable) => {
        return false;
      });

      // Crear la tarea
      cy.get('input').eq(0).type('Aprobar proyecto de Calidad');
      cy.get('input[type="date"]').type('2026-12-31');
      cy.get('button').first().click();

      // Verificar que se creó
      cy.contains('Aprobar proyecto de Calidad').should('be.visible');

      // PASO FINAL: COMPLETAR LA TAREA
      cy.contains('Completar').click();

    });

  });
});