package ar.edu.unlam.pb2.sistemas;

public class UsuarioLogueado extends Usuario {

	public UsuarioLogueado(String userName, String password, String name, String surname, Integer age) {
		super(userName, password, name, surname, age);
	}

	public UsuarioLogueado(String userName, String password) {
		super(userName, password);
	}

}
