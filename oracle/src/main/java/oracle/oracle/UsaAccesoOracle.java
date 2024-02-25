package oracle.oracle;

public class UsaAccesoOracle {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AccesoOracle a = new AccesoOracle();
		a.abrirConexion();
//		a.mostrarContactos();
		
		// EJERCICIOS 4.1. ORACLE
		
		a.mostrarAlumnos();		
//		a.insertarAlumno("06C","Ana Gutierrez","967365433");
//		a.mostrarAlumnos();
		
//		a.borrarAlumnoPorNombre("Ana Gutierrez");
//		a.mostrarAlumnos();
		
//		String nombreAlumno = "David Perez";
//		String telefono = a.buscarTelefonoPorNombre(nombreAlumno);
//		if (telefono != null) {
//		    System.out.println("Teléfono de " + nombreAlumno + "es -> " + telefono);
//		} else {
//		    System.out.println("No se encontró ningún alumno con el nombre '" + nombreAlumno + "'.");
//		}
		
//		a.mostrarListadoAlumnos();
//		a.mostrarInformacionAdmitidos();
		
		a.cerrarConexion();
	}
}
