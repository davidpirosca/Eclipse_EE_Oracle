package oracle.oracle;

public class UsaAccesoOracle {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AccesoOracle a = new AccesoOracle();
		a.abrirConexion();
		a.mostrarContactos();
		a.cerrarConexion();
	}
}
