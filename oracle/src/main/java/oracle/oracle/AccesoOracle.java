package oracle.oracle;

import java.sql.*;

import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class AccesoOracle {
	private Connection con;

	/**
     * Abre una conexión a la base de datos Oracle.
     */
    void abrirConexion() {
        try {
            // Cargar el controlador de la base de datos Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Establecer la conexión con la base de datos
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SYS as SYSDBA", "1234");
            
            // Imprimir mensaje de conexión exitosa
            System.out.println("Conexion OK");
        } catch (Exception e) {
            // Imprimir la traza de la excepción en caso de error
            e.printStackTrace();
        }
    }

    /**
     * Cierra la conexión a la base de datos Oracle.
     */
    void cerrarConexion() {
        try {
            // Imprimir mensaje de conexión cerrada
            System.out.println("Conexion cerrada");
            
            // Cerrar la conexión
            con.close();
        } catch (Exception e) {
            // Imprimir la traza de la excepción en caso de error
            e.printStackTrace();
        }
    }

    /**
     * Muestra la información de contactos desde la tabla contactos.
     */
    void mostrarContactos() {
        try {
            // Crea la consulta preparada
            String query = "SELECT c.nombre, c.telefono FROM contactos c";

            try (PreparedStatement pst = con.prepareStatement(query);
                 ResultSet resul = pst.executeQuery()) {

                System.out.println("INFORMACION DE CONTACTOS--------------");

                while (resul.next()) {
                    // Obtén los valores de las columnas por nombre
                    String nombre = resul.getString("nombre");
                    String telefono = resul.getString("telefono");

                    // Imprime la información del contacto
                    System.out.printf("\nNOMBRE: %s\nTELEFONO: %s", nombre, telefono);
                }

                System.out.println("\n--------------");
            }
        } catch (SQLException e) {
            // Imprime la traza de la excepción en caso de error
            e.printStackTrace();
        } catch (Exception e) {
            // Imprime la traza de la excepción en caso de error
            e.printStackTrace();
        }
    }

    /**
     * Muestra la información de todos los alumnos en la tabla misAlumnos.
     */
    void mostrarAlumnos() {
        try {
            // Crea la consulta preparada
            String query = "SELECT a.id_estudiante, a.datos_personales.nombre AS nombre, a.datos_personales.telefono AS telefono "
                         + "FROM misAlumnos a";

            try (PreparedStatement pst = con.prepareStatement(query);
                 ResultSet resul = pst.executeQuery()) {

                System.out.println("INFORMACION DE ALUMNOS--------------");

                while (resul.next()) {
                    // Obtén los valores de las columnas por nombre completo
                    String nombre = resul.getString("nombre");
                    String telefono = resul.getString("telefono");

                    // Imprime la información del alumno
                    System.out.printf("\nNombre: %s\nTeléfono: %s\n", nombre, telefono);
                }

                System.out.println("\n--------------");
            }
        } catch (SQLException e) {
            // Imprime la traza de la excepción en caso de error
            e.printStackTrace();
        } catch (Exception e) {
            // Imprime la traza de la excepción en caso de error
            e.printStackTrace();
        }
    }

	/**
     * Inserta un nuevo alumno en la tabla misAlumnos.
     *
     * @param idEstudiante Identificador del estudiante.
     * @param nombre       Nombre del estudiante.
     * @param telefono     Número de teléfono del estudiante.
     */
    public void insertarAlumno(String idEstudiante, String nombre, String telefono) {
        try {
            // Crear un objeto de tipo persona_typ
            String query = "INSERT INTO misAlumnos(id_estudiante, datos_personales) VALUES (?, persona(?, ?))";

            try (PreparedStatement pst = con.prepareStatement(query)) {
                // Establecer los parámetros del INSERT
                pst.setString(1, idEstudiante);
                pst.setString(2, nombre);
                pst.setString(3, telefono);

                // Ejecutar la consulta
                int filasAfectadas = pst.executeUpdate();

                // Verificar si se ha insertado algún alumno
                if (filasAfectadas > 0) {
                    System.out.println("Alumno insertado exitosamente.");
                } else {
                    System.out.println("No se pudo insertar el alumno.");
                }
            }

        } catch (SQLException e) {
            // Imprime la traza de la excepción en caso de error
            e.printStackTrace();
        } catch (Exception e) {
            // Imprime la traza de la excepción en caso de error
            e.printStackTrace();
        }
    }
	
    /**
     * Borra un alumno de la tabla misAlumnos por su nombre.
     *
     * @param nombreAlumno Nombre del alumno a ser borrado.
     */
    void borrarAlumnoPorNombre(String nombreAlumno) {
        try {
            // Crea la consulta parametrizada para borrar el alumno por nombre
            String query = "DELETE FROM misAlumnos a WHERE a.datos_personales.nombre = ?";
            
            // Utiliza PreparedStatement para evitar SQL Injection
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                // Establece el parámetro del nombre del alumno
                pstmt.setString(1, nombreAlumno);
                
                // Ejecuta la sentencia y obtén el número de filas afectadas
                int filasAfectadas = pstmt.executeUpdate();

                // Verifica si se ha borrado algún alumno
                if (filasAfectadas > 0) {
                    System.out.println("Alumno '" + nombreAlumno + "' borrado exitosamente.");
                } else {
                    System.out.println("No se encontró ningún alumno con el nombre '" + nombreAlumno + "'.");
                }
            }
        } catch (SQLException e) {
            // Imprime la traza de la excepción en caso de error
            e.printStackTrace();
        } catch (Exception e) {
            // Imprime la traza de la excepción en caso de error
            e.printStackTrace();
        }
    }
    
    /**
     * Busca el teléfono de un alumno por su nombre.
     * @param nombreAlumno Nombre del alumno a buscar.
     * @return El número de teléfono del alumno si se encuentra, o null si no se encuentra o hay un error.
     */
    public String buscarTelefonoPorNombre(String nombreAlumno) {
        try {
            // Crea la consulta preparada
            String query = "SELECT a.id_estudiante, a.datos_personales.nombre AS nombre, a.datos_personales.telefono AS telefono "
                         + "FROM misAlumnos a "
                         + "WHERE a.datos_personales.nombre = ?";

            try (PreparedStatement pst = con.prepareStatement(query)) {
                // Establece el parámetro del nombre del alumno
                pst.setString(1, nombreAlumno);

                try (ResultSet resul = pst.executeQuery()) {
                    if (resul.next()) {
                        // Obtén el valor del teléfono si se encuentra el alumno
                        return resul.getString("telefono");
                    }
                }
            }
        } catch (SQLException e) {
            // Imprime la traza de la excepción en caso de error
            e.printStackTrace();
        } catch (Exception e) {
            // Imprime la traza de la excepción en caso de error
            e.printStackTrace();
        }
        
        // Si no se encuentra el alumno o hay un error, devuelve null o un valor indicativo
        return null;
    }
    

	/**
     * Muestra un listado con la información de todos los alumnos.
     */
    public void mostrarListadoAlumnos() {
        try {
            // Crea la consulta preparada
            String query = "SELECT a.id_estudiante, a.datos_personales " +
                           "FROM misAlumnos a";

            try (PreparedStatement pst = con.prepareStatement(query);
                 ResultSet resul = pst.executeQuery()) {

                System.out.println("LISTADO DE ALUMNOS--------------");

                // Obtén el descriptor del tipo persona_typ
                StructDescriptor personaDescriptor = StructDescriptor.createDescriptor("PERSONA", con);

                while (resul.next()) {
                    // Obtén los valores de las columnas por nombre
                    String idEstudiante = resul.getString("id_estudiante");
                    STRUCT datosPersonalesStruct = (STRUCT) resul.getObject("datos_personales");

                    // Convierte el STRUCT a un array de objetos
                    Object[] datosPersonalesAttributes = datosPersonalesStruct.getAttributes();

                    // Extrae los valores del array
                    String nombre = (String) datosPersonalesAttributes[0];
                    String telefono = (String) datosPersonalesAttributes[1];

                    // Imprime la información del alumno
                    System.out.printf("\nID Estudiante: %s\nNombre: %s\nTeléfono: %s\n",
                            idEstudiante, nombre, telefono);
                }

                System.out.println("\n--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    /**
     * Muestra la información de los admitidos en la base de datos.
     */
    public void mostrarInformacionAdmitidos() {
        try {
            // Crea la consulta preparada
            String query = "SELECT a.dia, a.matriculado " +
                           "FROM admitidos a";

            try (PreparedStatement pst = con.prepareStatement(query);
                 ResultSet resul = pst.executeQuery()) {

                System.out.println("INFORMACIÓN DE ADMITIDOS--------------");

                // Obtén el descriptor del tipo estudiante
                StructDescriptor estudianteDescriptor = StructDescriptor.createDescriptor("ESTUDIANTE", con);

                while (resul.next()) {
                    // Obtén los valores de las columnas por nombre
                    Date dia = resul.getDate("dia");
                    STRUCT matriculadoStruct = (STRUCT) resul.getObject("matriculado");

                    // Convierte el STRUCT a un array de objetos
                    Object[] matriculadoAttributes = matriculadoStruct.getAttributes();

                    // Extrae los valores del array
                    String idEstudiante = (String) matriculadoAttributes[0];
                    STRUCT datosPersonalesStruct = (STRUCT) matriculadoAttributes[1];

                    // Obtén el descriptor del tipo persona
                    StructDescriptor personaDescriptor = StructDescriptor.createDescriptor("PERSONA", con);

                    // Convierte el STRUCT de datos_personales a un array de objetos
                    Object[] datosPersonalesAttributes = datosPersonalesStruct.getAttributes();

                    // Extrae los valores del array
                    String nombre = (String) datosPersonalesAttributes[0];
                    String telefono = (String) datosPersonalesAttributes[1];

                    // Imprime la información de admitidos
                    System.out.printf("\nDía: %s\nID Estudiante: %s\nNombre: %s\nTeléfono: %s\n",
                            dia, idEstudiante, nombre, telefono);
                }

                System.out.println("\n--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
