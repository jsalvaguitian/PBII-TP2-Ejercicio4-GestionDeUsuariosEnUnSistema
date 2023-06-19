package ar.edu.unlam.pb2.sistemas;

import java.util.HashSet;
/* El usuario tiene 3 intentos para ingresar su contraseña al loguear, 
 * si en el ultimo intento ingresa mal lanzar una excepcion y que el usuario se convierta en usuario bloqueado
 * hasta que pueda cambiar contrasenia, si cambia la contrasenia satisfactoriamente el usuario 
 * se convierte en usuario (no bloqueado, ni logueado)
 * 
 * */
public class Usuario {
	protected String userName;
	protected String password;
	protected String nombre;
	protected String apellido;
	protected Integer edad;
	protected static final Integer LONGITUD_MINIMA_PSWD = 8;
	protected HashSet<String> listaDeContraseniasUsadas;
	protected Integer cantidadIntentosLogIn;
	protected Boolean isLogIn;
	
	
	public Usuario(String userName, String password) {
		this.userName = userName;
		this.password = password;
		nombre = "";
		apellido = "";
		edad = 0;
		this.listaDeContraseniasUsadas = new HashSet<>();
		this.cantidadIntentosLogIn = 3;
		this.isLogIn = false; //¿registrarse significa logueado?
		
		//De paso la agrego como contrasenia usada
		this.guardarContraseniaUsadas(password);
	}
	
	public Usuario(String userName, String password, String name, String surname, Integer age) {
		this.userName = userName;
		this.password = password;
		this.nombre = name;
		this.apellido = surname;
		this.edad = age;
		this.listaDeContraseniasUsadas = new HashSet<>();
		this.cantidadIntentosLogIn = 3;
		this.isLogIn = false; //¿registrarse significa logueado?
		
		//De paso la agrego como contrasenia usada
		this.guardarContraseniaUsadas(password);
	}

	private void guardarContraseniaUsadas(String password) {
		this.listaDeContraseniasUsadas.add(password);	
	}

	/* 
	 * Desarrolle el método “laContraseniaEsValida”. Para eso considerar que una contraseña es válida si
	 * cumple con las siguientes condiciones:
	 * 
	 * a. Tiene una longitud de al menos 8 caracteres.
	 * b. Contiene al menos un carácter numérico.
	 * c. Contiene al menos una mayúscula.
	 * d. Contiene al menos una minúscula.
	 * e. Contiene al menos un carácter especial.
	 */

	public static Boolean laContraseniaEsValida(String contrasenia) {
		boolean tieneUnNumero = false;
		boolean tieneMayuscula = false;
		boolean tieneMinuscula = false;
		boolean tieneCaracterEspecial = false;
		
		if(contrasenia.length()>= LONGITUD_MINIMA_PSWD) {
			tieneUnNumero = tieneNumeroLaContrasenia(contrasenia);
			tieneMayuscula = tieneMayusculaLaContrasenia(contrasenia);
			tieneMinuscula = tieneMinusculaLaContrasenia(contrasenia);
			tieneCaracterEspecial = tieneCaracterEspecial(contrasenia);
			
			if(tieneUnNumero && tieneMayuscula && tieneMinuscula && tieneCaracterEspecial) {
				return true;
			}
		}
		return false;
	}
	

	private static boolean tieneCaracterEspecial(String contrasenia) {
		int primerSignoAscii = 33, signoAsciiPunto = 46, penultimoAscii = 58, ultimoAscci =63;
		
		for(int i=0;i< contrasenia.length(); i++) {
			for(int j=primerSignoAscii; j<signoAsciiPunto; j++) {
				if(contrasenia.charAt(i) == (char)j) {
					return true;
				}
			}
			
			for(int j= penultimoAscii; j < ultimoAscci; j++) {
				if(contrasenia.charAt(i) == (char) j) {
					return true;
				}
			}
		}
	
		return false;
	}

	private static boolean tieneMinusculaLaContrasenia(String contrasenia) {
		char letraA = 'a';
		char letraZ = 'z';
		
		for(int i=0; i< contrasenia.length(); i++) {
			for(int j = (int)letraA; j< (int)letraZ; j++) {
				if(contrasenia.charAt(i) == (char)j)
					return true;
			}
			
		}
		return false;
	}

	private static boolean tieneMayusculaLaContrasenia(String contrasenia) {
		char letraMayuscA = 'A';
		char letraMayuscZ = 'Z';
		
		for(int i= 0; i < contrasenia.length(); i++) {
			for(int j = (int)letraMayuscA; j< (int)letraMayuscZ; j++) {
				if(contrasenia.charAt(i) == (char)j)
					return true;
				
			}
		}
		return false;
	}

	private static boolean tieneNumeroLaContrasenia(String contrasenia) {
		int numeroCero = 48, numeroNueve = 57;
	
		for(int i = 0; i< contrasenia.length(); i ++) {
			for(int j= numeroCero; j<numeroNueve; j++) {
				if(contrasenia.charAt(i) == (char)j) {
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * 2. En la clase Usuario desarrolle el método “cambiarContrasenia”:
	 * a. La nueva contraseña debe ser válida.
	 * b. La nueva contraseña debe ser diferente a las últimas contraseñas utilizadas.
	 * c. El método devolverá true si la contraseña se pudo actualizar o false en caso contrario.
	 */
	
	public boolean cambiarContrasenia(String contrasenia) {
		
		if(laContraseniaEsValida(contrasenia) && !(this.laContraseniaEsRepetida(contrasenia))) {
			this.setPassword(contrasenia);
			this.guardarContraseniaUsadas(contrasenia);
			return true;
		}
		return false;
	}

	private boolean laContraseniaEsRepetida(String contrasenia) {
		if(this.listaDeContraseniasUsadas.contains(contrasenia))
			return true;
		return false;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public Integer getEdad() {
		return edad;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	public Integer getIntentos() {
		
		return this.cantidadIntentosLogIn;
	}

	public void decrementarSusIntentos() {
		if(this.cantidadIntentosLogIn>0)
			this.cantidadIntentosLogIn--;
		
	}

	public void setLogIn(boolean estadoDeLogIn) {
		this.isLogIn = estadoDeLogIn;
		
	}
	
	
	
	
	
	
	
	
	

}





