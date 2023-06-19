package ar.edu.unlam.pb2.sistemas;

import java.util.HashSet;

import ar.edu.unlam.pb2.sistemas.exception.UsuarioBloqueadoException;
import ar.edu.unlam.pb2.sistemas.exception.UsuarioInexistenteException;

public class Sistema {
	private final Integer CANTIDAD_MAXIMA_DE_USUARIOS;
	private String nombreDelSistema;
	private HashSet<Usuario> usuarios;

	public Sistema(String nombre, Integer cantidadDeUsuarios) {
		this.nombreDelSistema = nombre;
		this.CANTIDAD_MAXIMA_DE_USUARIOS = cantidadDeUsuarios;
		this.usuarios = new HashSet<>();
		
	}
	
	/*
	Desarrolle un método que sea incorporar un usuario a su lista de usuarios.
	Dicho método devolverá true si el usuario se puede ingresar
	en el sistema o false en caso contrario (no se puede ingresar un nombre de
	usuario que ya exista).
	 */
	public boolean registrarUsuarioAlSistema(Usuario user) {
		if(this.usuarios.size()< this.CANTIDAD_MAXIMA_DE_USUARIOS) {
			return this.usuarios.add(user);
		}
		return false;
		
	}
	
	// c. Desarrolle el método calcularLaCantidadDeUsuariosLogueados.
	public Integer calcularLaCantidadDeUsuariosLogueados() {
		Integer cantidadDeUsuariosLogueados = 0;
		
		for(Usuario user : this.usuarios) {
			if(user instanceof UsuarioLogueado) {
				cantidadDeUsuariosLogueados++;
			}		
		}
		
		return cantidadDeUsuariosLogueados;
	}
	
	// d. Desarrolle el método calcularLaCantidadDeUsuariosBloqueados.
	public Integer calcularLaCantidadDeUsuariosBloqueados() {
		Integer cantidadDeUsuariosBloqueados = 0;
		
		for(Usuario user:  this.usuarios) {
			if(user instanceof UsuarioBloqueado) {
				cantidadDeUsuariosBloqueados++;
			}
		}
		
		return cantidadDeUsuariosBloqueados;
	}
	
	// e. Desarrolle el método calcularElPorcentajeDeUsuariosLogueados.
	public Double calcularElPorcentajeDeUsuariosLogueados() {
		double porcentajeLogueados = 0;
		
		Integer cantidadLogueados = this.calcularLaCantidadDeUsuariosLogueados();
		Integer totalUsuarios = this.usuarios.size();
		
		if(totalUsuarios>0) {
			porcentajeLogueados = (double)cantidadLogueados*100/totalUsuarios;
		}
		
		return porcentajeLogueados;
	}
	
	// f. Desarrolle el método calcularEdadPromedio para conocer a qué público está dirigido el sistema.
	public Integer calcularEdadPromedio() {
		
		Double sumatoriaEdad = 0.0;
		int cantidadUsuarios = this.usuarios.size();
		double promedioEdad = 0;
		
		for(Usuario user : this.usuarios) {
			sumatoriaEdad += user.getEdad();
		}
		
		promedioEdad = sumatoriaEdad /cantidadUsuarios;
		
		
		return (int)Math.round(promedioEdad);
	}
	
	/*g. En la clase Sistema desarrolle el método loguearUsuario, el cual devolverá true si
	se logra loguear al usuario y false en caso contrario:
	*/
	/*public boolean logInUsuario (String usuario, String contraseña) {
		
	}*/
	
	/*
	 * 9. En la clase Sistema desarrolle el método buscarUsuario, el cual devolverá un objeto de tipo Usuario si
	 * se encuentra el mismo o null en caso contrario:
	 */
	public Usuario buscarUsuario(String userNameRegistrado) throws UsuarioInexistenteException {
		for(Usuario unoDelSistema : this.usuarios) {
			if(unoDelSistema.getUserName().equals(userNameRegistrado)) {
				return unoDelSistema;
			}
		}
		
		throw new UsuarioInexistenteException("El usuario no existe");
	}
	/*h. Ver las estadísticas del sistema 
	 *Listar Cantidad de usuarios 
	 *logueados,
	 *bloqueados,
	 *porcentaje de usuarios logueados y
	 *edad promedio de los usuarios)
	 */
	
	public String mostrarLasEstadisticasDelSistema() {
		String informacion = "";
	
		return informacion = "Cantidad de usuarios logueados: " + this.calcularLaCantidadDeUsuariosLogueados() + 
				"\nCantidad de usuarios bloqueados: "+ this.calcularLaCantidadDeUsuariosBloqueados() +
				"\nPorcentaje de usuarios logueados: " + this.calcularElPorcentajeDeUsuariosLogueados() +
				"\nEdad promedio: "+ this.calcularEdadPromedio();
	}
	
	/* Probar el login. 
	 * Esto es, como administrador se verifica el acceso al sistema de un usuario determinado.
	 * Al salir, el usuario utilizado quedará como logueado para poder evaluar las estadísticas.
	 */
	
	/*¿Es necesario?
	 * public void logOutUsuario(Usuario user) {
		
		
		
	}*/

	public boolean permitirAlUsuarioCambiarSuContrasenia(String userNameRegistrado, String nuevaPassword) throws UsuarioInexistenteException {
		
		Usuario registrado = buscarUsuario(userNameRegistrado);
		
		if(registrado.cambiarContrasenia(nuevaPassword)) {
			return true;

		}else {
			return false;
		}
		
	}

	

	public boolean logInUsuario(String userName, String password) throws UsuarioBloqueadoException, UsuarioInexistenteException {

		Usuario encontrado = this.buscarUsuario(userName);
				
		if(encontrado.getPassword().equals(password) && encontrado.getIntentos()>0) {
			//user.setLogIn(true);
			UsuarioLogueado logueado = this.convertirAlUsuarioComoUsuarioLogueado(encontrado);
			this.usuarios.remove(encontrado);
			this.usuarios.add(logueado);
			return true;
			
		}else if(!encontrado.getPassword().equals(password) && encontrado.getIntentos()>0) {
			encontrado.decrementarSusIntentos();
			
			if(encontrado.getIntentos()==0) {
				this.bloquearUsuario(encontrado);
			}

			return false;
			
		}else if((encontrado.getPassword().equals(password) || !encontrado.getPassword().equals(password)) && encontrado.getIntentos() == 0) {
			//UsuarioBloqueado unBloqueado = (UsuarioBloqueado) user; //Esto no porque me genera error ClassCastExceptio (no puedo castear un objeto posta de tipo Clase padre a una clase hija)
			throw new UsuarioBloqueadoException("Su cuenta fue bloqueada, "+ encontrado.getUserName());

		}
		
		throw new UsuarioInexistenteException("No existe el usuario :-s");

		
	}

	private void bloquearUsuario(Usuario encontrado) throws UsuarioBloqueadoException {
		UsuarioBloqueado bloqueado = this.convertirAlUsuarioComoUsuarioBloqueado(encontrado);
		
		this.usuarios.remove(encontrado);
		this.usuarios.add(bloqueado);
		throw new UsuarioBloqueadoException("Su cuenta fue bloqueada, "+ encontrado.getUserName());
	}

	private UsuarioLogueado convertirAlUsuarioComoUsuarioLogueado(Usuario user) {
		UsuarioLogueado logueado = new UsuarioLogueado(user.getUserName(), user.getPassword(),user.getNombre(), user.getApellido(), user.getEdad());
		return logueado;
	}

	private UsuarioBloqueado convertirAlUsuarioComoUsuarioBloqueado(Usuario user) {
		UsuarioBloqueado bloqueado = new UsuarioBloqueado(user.getUserName(), user.getPassword(),user.getNombre(), user.getApellido(), user.getEdad());
		return bloqueado;
	}
	
	
	

}
