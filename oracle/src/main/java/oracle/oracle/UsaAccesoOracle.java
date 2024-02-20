package oracle.oracle;

public class UsaAccesoOracle {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AccesoOracle a = new AccesoOracle();
		a.abrirConexion();
//		a.mostrarContactos();
		
		
		// EJERCICIOS 4.1. ORACLE
		
//		a.mostrarAlumnos();
		String query = "INSERT INTO misAlumnos VALUES (estudiante('05C', persona('David Perez', '967367933')))";
		a.insertarAlumno(query);	
		
		a.borrarAlumnoPorNombre("David Perez");
		
		
		a.cerrarConexion();
	}
}
