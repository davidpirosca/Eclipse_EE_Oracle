package oracle.oracle;

import java.sql.*;

public class AccesoOracle {
	private Connection con;

	void abrirConexion() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SYS  as SYSDBA", "1234");
			System.out.println("Conexion OK");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void cerrarConexion() {
		try {
			System.out.println("Conexion cerrada");
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void mostrarContactos() {
		try {
			// Create a statement
			Statement st = con.createStatement();
			ResultSet resul = st.executeQuery("SELECT c.nombre, c.telefono FROM contactos c");
			System.out.println("INFORMACION DE CONTACTOS--------------");
			while (resul.next()) {
				// You could also use resul.getInt("nif") here
				System.out.printf("\nNOMBRE: %s\nTELEFONO: %s", resul.getString(1), resul.getString(2));
			}
			System.out.println("\n--------------");
			resul.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void insertarAlumno(String query) {
		try {
			Statement st = con.createStatement();
			ResultSet resul = st.executeQuery(query);
			resul.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void borrarAlumnoPorNombre(String nombreAlumno) {
	    try {
	        // Crea la sentencia SQL para borrar el alumno por nombre
	        String query = "DELETE FROM misAlumnos WHERE DATOS_PERSONALES.NOMBRE = '" + nombreAlumno + "'";

	        // Crea la declaración y ejecuta la sentencia
	        Statement st = con.createStatement();
	        int filasAfectadas = st.executeUpdate(query);

	        // Verifica si se ha borrado algún alumno
	        if (filasAfectadas > 0) {
	            System.out.println("Alumno '" + nombreAlumno + "' borrado exitosamente.");
	        } else {
	            System.out.println("No se encontró ningún alumno con el nombre '" + nombreAlumno + "'.");
	        }

	        // Cierra la conexión
	        st.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
