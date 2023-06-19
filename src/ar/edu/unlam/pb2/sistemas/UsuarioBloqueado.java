package ar.edu.unlam.pb2.sistemas;

public class UsuarioBloqueado extends Usuario {

	public UsuarioBloqueado(String userName, String password, String name, String surname, Integer age) {
		super(userName, password, name, surname, age);
	}

	public UsuarioBloqueado(String userName, String password) {
		super(userName, password);
	}

}
