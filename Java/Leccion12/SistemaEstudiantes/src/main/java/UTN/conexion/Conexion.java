package UTN.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static Connection getConnection(){
        Connection conexion = null;
        //vcariables para conectarnos:
        var baseDeDatos="estudiantes2024";
        var url = "jdbc:mysql://localhost:3306/"+baseDeDatos;
        var usuario = "root";
        var password = "123456";

        //cargamos la clase del driver de msql en memoria:
        try {
            Class.forName("com.msql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);
        } catch(ClassNotFoundException | SQLException e){
            System.out.println("Ocurrio un error en la conexion: "+e.getMessage());
        } //FIN CATCH
        return conexion;
    } //FIN METODO Conenection
} //FIN CLASS Conexion
