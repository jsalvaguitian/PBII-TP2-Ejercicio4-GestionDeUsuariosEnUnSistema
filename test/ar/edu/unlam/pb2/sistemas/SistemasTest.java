package ar.edu.unlam.pb2.sistemas;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.edu.unlam.pb2.sistemas.exception.UsuarioBloqueadoException;
import ar.edu.unlam.pb2.sistemas.exception.UsuarioInexistenteException;

public class SistemasTest {

	@Test
	public void queSePuedaCrearUnSistemaConSuRespectivoNombreYCantidadDeUsuarios() {
		// Preparacion
		final String NOMBRE_DEL_SISTEMA = "SIU Guarani UTN.BA";
		final Integer CANTIDAD_MAXIMA_DE_USUARIOS = 10;
		Sistema utnba;

		// Ejecucion
		utnba = new Sistema(NOMBRE_DEL_SISTEMA, CANTIDAD_MAXIMA_DE_USUARIOS);

		// Validacion
		assertNotNull(utnba);
	}

	@Test
	public void queSePuedaAgregarDosUsuariosEnSistema() {
		// Preparacion
		final String NOMBRE_DEL_SISTEMA = "SIU Guarani UTN.BA";
		final Integer CANTIDAD_MAXIMA_DE_USUARIOS = 10;
		Sistema utnba;

		final String NOMBRE_USUARIO1 = "Belen";
		final String NOMBRE_USUARIO2 = "Teresa";

		final String PASSWORD1 = "Hola123J!";
		final String PASSWORD2 = "Qwerty35!";

		// Ejecucion
		utnba = new Sistema(NOMBRE_DEL_SISTEMA, CANTIDAD_MAXIMA_DE_USUARIOS);

		Usuario user1 = new Usuario(NOMBRE_USUARIO1, PASSWORD1);
		Usuario user2 = new Usuario(NOMBRE_USUARIO2, PASSWORD2);

		// Validacion
		assertTrue(utnba.registrarUsuarioAlSistema(user1));
		assertTrue(utnba.registrarUsuarioAlSistema(user2));

	}

	@Test
	public void queUnUsuarioDelSistemaPuedaCambiarDeContrasenia() throws UsuarioInexistenteException {
		// Preparacion
		final String NOMBRE_DEL_SISTEMA = "SIU Guarani UTN.BA";
		final Integer CANTIDAD_MAXIMA_DE_USUARIOS = 10;
		Sistema unlam;

		final String NOMBRE_USUARIO1 = "Belen";
		final String NOMBRE_USUARIO2 = "Teresa";

		final String PASSWORD1 = "Hola123J!";
		final String PASSWORD2 = "Qwerty35!";

		final String NUEVA_CONTRASENIA = "CyberSec1!";
		// Ejecucion
		unlam = new Sistema(NOMBRE_DEL_SISTEMA, CANTIDAD_MAXIMA_DE_USUARIOS);

		Usuario user1 = new Usuario(NOMBRE_USUARIO1, PASSWORD1);
		Usuario user2 = new Usuario(NOMBRE_USUARIO2, PASSWORD2);

		unlam.registrarUsuarioAlSistema(user1);
		unlam.registrarUsuarioAlSistema(user2);

		// Validacion

		assertTrue(unlam.permitirAlUsuarioCambiarSuContrasenia(NOMBRE_USUARIO1, NUEVA_CONTRASENIA));

	}

	@Test
	public void queSePuedaLoguearUnUsuarioEnElSistema() throws UsuarioBloqueadoException, UsuarioInexistenteException {
		// PREPARACION
		// 1- creo el sistema
		final String NOMBRE_DEL_SISTEMA = "SIU Guarani UTN.BA";
		final Integer CANTIDAD_MAXIMA_DE_USUARIOS = 10;
		Sistema unlam = new Sistema(NOMBRE_DEL_SISTEMA, CANTIDAD_MAXIMA_DE_USUARIOS);

		// 2- creo el usuario
		final String NOMBRE_USUARIO1 = "Belen";
		final String PASSWORD1 = "Hola123J!";
		Usuario user1 = new Usuario(NOMBRE_USUARIO1, PASSWORD1);

		// 3- registro el usuario en el sistema
		unlam.registrarUsuarioAlSistema(user1);

		// EJECUCION Y VALIDACION
		assertTrue(unlam.logInUsuario(NOMBRE_USUARIO1, PASSWORD1));
		// unlam.logOutUsuario(user1); //Al salir igual quede como logueado ¿Es
		// necesario?

	}

	@Test
	public void queSePuedaCalcularLaCantidadDeUsuariosLogueados()
			throws UsuarioBloqueadoException, UsuarioInexistenteException {
		// PREPARACION
		// 1- creo el sistema
		final String NOMBRE_DEL_SISTEMA = "SIU Guarani UTN.BA";
		final Integer CANTIDAD_MAXIMA_DE_USUARIOS = 10;
		Sistema unlam = new Sistema(NOMBRE_DEL_SISTEMA, CANTIDAD_MAXIMA_DE_USUARIOS);

		// 2- creo el usuario 1
		final String NOMBRE_USUARIO1 = "Belen";
		final String PASSWORD1 = "Hola123J!";
		Usuario user1 = new Usuario(NOMBRE_USUARIO1, PASSWORD1);

		// 2- creo el usuario 2
		final String NOMBRE_USUARIO2 = "Eli";
		final String PASSWORD2 = "Qwerty35!";
		Usuario user2 = new Usuario(NOMBRE_USUARIO2, PASSWORD2);

		// 3- registro el usuario en el sistema

		// EJECUCION
		unlam.registrarUsuarioAlSistema(user1);
		unlam.registrarUsuarioAlSistema(user2);

		unlam.logInUsuario(NOMBRE_USUARIO1, PASSWORD1);
		unlam.logInUsuario(NOMBRE_USUARIO2, PASSWORD2);

		// VALIDACION
		assertEquals((Integer) 2, unlam.calcularLaCantidadDeUsuariosLogueados());

	}

	@Test(expected = UsuarioBloqueadoException.class)
	public void queElUsuarioSeConviertaEnUsuarioBloqueadoLuegoDeHaberIntentadoTresVecesLoguearse()
			throws UsuarioBloqueadoException, UsuarioInexistenteException {

		// PREPARACION
		// 1- creo el sistema
		final String NOMBRE_DEL_SISTEMA = "SIU Guarani UTN.BA";
		final Integer CANTIDAD_MAXIMA_DE_USUARIOS = 10;
		Sistema unlam = new Sistema(NOMBRE_DEL_SISTEMA, CANTIDAD_MAXIMA_DE_USUARIOS);

		// 2- creo el usuario 1
		final String NOMBRE_USUARIO1 = "Belen";
		final String PASSWORD1 = "Hola123J!";
		Usuario user1 = new Usuario(NOMBRE_USUARIO1, PASSWORD1);

		// 3- registro el usuario en el sistema

		// EJECUCION
		unlam.registrarUsuarioAlSistema(user1);

		unlam.logInUsuario(NOMBRE_USUARIO1, "kshjdkjs");
		unlam.logInUsuario(NOMBRE_USUARIO1, "ewuy12!");
		unlam.logInUsuario(NOMBRE_USUARIO1, "ewuy12!");
		unlam.logInUsuario(NOMBRE_USUARIO1, PASSWORD1);

	}

	@Test
	public void queSePuedaCalcularLaCantidadDeUsuarioBloqueados() throws UsuarioInexistenteException {
		// PREPARACION
		// 1- creo el sistema
		final String NOMBRE_DEL_SISTEMA = "SIU Guarani UTN.BA";
		final Integer CANTIDAD_MAXIMA_DE_USUARIOS = 10;
		Sistema unlam = new Sistema(NOMBRE_DEL_SISTEMA, CANTIDAD_MAXIMA_DE_USUARIOS);

		// 2- creo el usuario 1
		final String NOMBRE_USUARIO1 = "Belen";
		final String PASSWORD1 = "Hola123J!";
		Usuario user1 = new Usuario(NOMBRE_USUARIO1, PASSWORD1);

		// 2- creo el usuario 2
		final String NOMBRE_USUARIO2 = "Eli";
		final String PASSWORD2 = "Qwerty35!";
		Usuario user2 = new Usuario(NOMBRE_USUARIO2, PASSWORD2);

		// 2- creo el usuario 2
		final String NOMBRE_USUARIO3 = "Homero";
		final String PASSWORD3 = "Duff177!";
		Usuario user3 = new Usuario(NOMBRE_USUARIO3, PASSWORD3);

		// 3- registro el usuario en el sistema

		// EJECUCION
		unlam.registrarUsuarioAlSistema(user1);
		unlam.registrarUsuarioAlSistema(user2);
		unlam.registrarUsuarioAlSistema(user3);

		// Usuario 1 ingresa mal

		try {
			unlam.logInUsuario(NOMBRE_USUARIO1, "kshjdkjs");
			unlam.logInUsuario(NOMBRE_USUARIO1, "ewuy12!");
			unlam.logInUsuario(NOMBRE_USUARIO1, "ewuy12!");
			unlam.logInUsuario(NOMBRE_USUARIO1, PASSWORD1);


		} catch (UsuarioBloqueadoException e) {
			System.err.println(e.getMessage());
		}

		// Usuario 2 ingresa bien
		try {
			unlam.logInUsuario(NOMBRE_USUARIO2, PASSWORD2);

		} catch (UsuarioBloqueadoException e) {
			System.err.println(e.getMessage());

		}

		// Usuario 3 ingresa mal
		try {
			unlam.logInUsuario(NOMBRE_USUARIO3, "kshjdkjs");
			unlam.logInUsuario(NOMBRE_USUARIO3, "ewuy12!");
			unlam.logInUsuario(NOMBRE_USUARIO3, "ewuy12!");
			unlam.logInUsuario(NOMBRE_USUARIO3, "ewuy12!");


		} catch (UsuarioBloqueadoException e) {
			System.err.println(e.getMessage());
		}

		// VALIDACION
		assertEquals((Integer) 2, unlam.calcularLaCantidadDeUsuariosBloqueados());

	}

	@Test
	public void queSePuedaCalcularElPorcentajeDeUsuariosLogueados()
			throws UsuarioBloqueadoException, UsuarioInexistenteException {

		// PREPARACION
		// 1- creo el sistema
		final String NOMBRE_DEL_SISTEMA = "SIU Guarani UTN.BA";
		final Integer CANTIDAD_MAXIMA_DE_USUARIOS = 10;
		Sistema unlam = new Sistema(NOMBRE_DEL_SISTEMA, CANTIDAD_MAXIMA_DE_USUARIOS);

		// 2- creo el usuario 1
		final String NOMBRE_USUARIO1 = "Belen";
		final String PASSWORD1 = "Hola123J!";
		Usuario user1 = new Usuario(NOMBRE_USUARIO1, PASSWORD1);

		// 2- creo el usuario 2
		final String NOMBRE_USUARIO2 = "Eli";
		final String PASSWORD2 = "Qwerty35!";
		Usuario user2 = new Usuario(NOMBRE_USUARIO2, PASSWORD2);

		// 2- creo el usuario 2
		final String NOMBRE_USUARIO3 = "Homero";
		final String PASSWORD3 = "Duff177!";
		Usuario user3 = new Usuario(NOMBRE_USUARIO3, PASSWORD3);

		// EJECUCION
		unlam.registrarUsuarioAlSistema(user1);
		unlam.registrarUsuarioAlSistema(user2);
		unlam.registrarUsuarioAlSistema(user3);

		// Usuario 2 se loguea
		unlam.logInUsuario(NOMBRE_USUARIO2, PASSWORD2);

		// assertEquals(33.3, unlam.calcularElPorcentajeDeUsuariosLogueados(), 0.1);
		// //Este usalo cuando el metodo devuelve un primitivo

		assertEquals((Double) 33.3, unlam.calcularElPorcentajeDeUsuariosLogueados(), 0.1);// Cualquiera de los dos esta
																							// bien

	}

	@Test
	public void queSePuedaCalcularEdadPromedio() {
		// PREPARACION
		// 1- creo el sistema
		final String NOMBRE_DEL_SISTEMA = "SIU Guarani UTN.BA";
		final Integer CANTIDAD_MAXIMA_DE_USUARIOS = 10;
		Sistema unlam = new Sistema(NOMBRE_DEL_SISTEMA, CANTIDAD_MAXIMA_DE_USUARIOS);

		// 2- creo el usuario 1
		final String NOMBRE_USUARIO1 = "Belen";
		final String PASSWORD1 = "Hola123J!";
		final String NOMBRE1 = "Belen";
		final String APELLIDO1 = "Salva";
		final Integer EDAD1 = 26;

		Usuario user1 = new Usuario(NOMBRE_USUARIO1, PASSWORD1, NOMBRE1, APELLIDO1, EDAD1);

		// 2- creo el usuario 2
		final String NOMBRE_USUARIO2 = "Eli";
		final String PASSWORD2 = "Qwerty35!";
		final String NOMBRE2 = "Elizabeth";
		final String APELLIDO2 = "Martinez";
		final Integer EDAD2 = 20;

		Usuario user2 = new Usuario(NOMBRE_USUARIO2, PASSWORD2, NOMBRE2, APELLIDO2, EDAD2);

		// 2- creo el usuario 2
		final String NOMBRE_USUARIO3 = "Homero";
		final String PASSWORD3 = "Duff177!";
		final String NOMBRE3 = "Homer";
		final String APELLIDO3 = "Simpson";
		final Integer EDAD3 = 38;

		Usuario user3 = new Usuario(NOMBRE_USUARIO3, PASSWORD3, NOMBRE3, APELLIDO3, EDAD3);

		// Ejecucino
		unlam.registrarUsuarioAlSistema(user1);
		unlam.registrarUsuarioAlSistema(user2);
		unlam.registrarUsuarioAlSistema(user3);

		assertEquals((Integer) 28, unlam.calcularEdadPromedio());

	}

	/*
	 * Ver las estadísticas del sistema (Cantidad de usuarios logueados, bloqueados,
	 * porcentaje de usuarios logueados y edad promedio de los usuarios)
	 */

	@Test
	public void queSePuedaPresentarLasEstadisticasDelSistema() throws UsuarioInexistenteException {
		// PREPARACION
		String informacionTest = "Cantidad de usuarios logueados: 3" + 
				"\nCantidad de usuarios bloqueados: 2"+ 
				"\nPorcentaje de usuarios logueados: 50.0" + 
				"\nEdad promedio: 28";
		// 1- creo el sistema
		final String NOMBRE_DEL_SISTEMA = "SIU Guarani UTN.BA";
		final Integer CANTIDAD_MAXIMA_DE_USUARIOS = 10;
		Sistema unlam = new Sistema(NOMBRE_DEL_SISTEMA, CANTIDAD_MAXIMA_DE_USUARIOS);

		// 2- creo el usuario 
		
		Usuario user1 = new Usuario("Belen", "Hola123J!", "Belen", "Salva", 26);
		Usuario user2 = new Usuario("Eli", "Qwerty35!", "Elizabeth", "Martinez", 20);
		Usuario user3 = new Usuario("Homero", "Duff177!", "Homer", "Simpson", 38);
		Usuario user4 = new Usuario("Lis", "asdfA12!", "Lisa", "Simpson", 15);
		Usuario user5 = new Usuario("Marge", "uytA122@", "Marge", "Simpson", 38);
		Usuario user6 = new Usuario("Pablo", "Q$iuw123", "Pablo", "Acevedo", 30);
		
		unlam.registrarUsuarioAlSistema(user1);
		unlam.registrarUsuarioAlSistema(user2);
		unlam.registrarUsuarioAlSistema(user3);
		unlam.registrarUsuarioAlSistema(user4);
		unlam.registrarUsuarioAlSistema(user5);
		unlam.registrarUsuarioAlSistema(user6);
		
		// Ejecucion
		//2 bloqueados
		try {
			unlam.logInUsuario("Belen", "ahsk");
			unlam.logInUsuario("Belen", "382kjhw");
			unlam.logInUsuario("Belen", "wuuiq2"); //Si ingreso mal al ultimo intento no cuenta como bloqueada

		} catch (UsuarioBloqueadoException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			unlam.logInUsuario("Eli", "ahsk");
			unlam.logInUsuario("Eli", "382kjhw");
			unlam.logInUsuario("Eli", "wuuiq2");

		} catch (UsuarioBloqueadoException e) {
			System.err.println(e.getMessage());
		}
		
		//3 logueados
		try {
			unlam.logInUsuario("Homero", "ahsk");
			unlam.logInUsuario("Homero", "382kjhw");
			unlam.logInUsuario("Homero", "Duff177!"); //se logueó al final

		} catch (UsuarioBloqueadoException e) {
			System.err.println(e.getMessage());
		}
		
		
		try {
			unlam.logInUsuario("Lis", "asdfA12!"); //se logueó
			unlam.logInUsuario("Marge", "uytA122@"); //se logueó
		} catch (UsuarioBloqueadoException e) {
			System.err.println(e.getMessage());
		}
			
		//Validacion
		
		assertEquals(informacionTest , unlam.mostrarLasEstadisticasDelSistema());
		

	}

}
