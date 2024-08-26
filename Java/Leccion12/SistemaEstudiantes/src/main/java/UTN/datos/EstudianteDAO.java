package UTN.datos;
import UTN.dominio.Estudiante;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.w3c.dom.ls.LSOutput;

import static UTN.conexion.Conexion.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    //metodo listar:
    public List<Estudiante> listarEstudiantes(){
        List<Estudiante> estudiantes = new ArrayList<>();
        //Creamos algunos objetos para comunicarnos a la bd:
        PreparedStatement ps; //preparar la sentencia sql para ejecutar a la bd
        ResultSet rs; //almacena resultado obtenido de la bd
        //creamos objeto conexion:
        Connection con = getConnection();
        String sql = "SELECT * FROM estudiantes2024 ORDER BY idestudiantes2024";
        try{
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                var estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("idestudiantes2024"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                //Lo agregamos a la lista:
                estudiantes.add(estudiante);
            }
        } catch (Exception e){
            System.out.println("Ocurrio un error al seleccionar datos: "+e.getMessage());
        }
        finally {
           try {
                con.close();
           } catch (Exception e){
               System.out.println("Ocurrio un error al cerrar la conexion: "+e.getMessage());
           }
        }//FIN FINALLY
        return estudiantes;
    }// FIN METODO 'LISTAR'

    //Metodo por id -> fin by id
    public boolean buscarEstudiantePorId(Estudiante estudiante){
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConnection();
        String sql = "SELECT * FROM estudiantes2024 WHERE idestudiantes2024=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            rs = ps.executeQuery();
            if(rs.next()){
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                return true;
            } //FIN IF
        } catch (Exception e){
            System.out.println("Ocurrio un error al buscar estudiante: "+e.getMessage());
        } //FIN CATCH
        finally {
            try{
                con.close();
            } catch (Exception e){
                System.out.println("Ocurrio un error al cerrar conexion: "+e.getMessage());
            } //FIN CATCH
        } //FIN FINALLY
        return false;
    }

    //Metodo agregar un nuevo estudiante
    public boolean agregarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConnection();
        String sql = "INSERT INTO estudiantes2024 (nombre, apellido, telefono, email) VALUES (?,?,?,?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.execute();
            return true;
        }catch(Exception e){
            System.out.println("Ocurrio un error al agregar estudiante: "+e.getMessage());
        }//Fin cath
        finally {
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerror la conexion: "+e.getMessage());
            }
        }
        return false;
    }//Fin metodo agregarEstudiante

    //Método eliminar estudiante
    public boolean eliminarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConnection();
        String sql = "DELETE FROM estudiantes2024 WHERE idestudiantes2024=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            ps.execute();
            return true;
        } catch(Exception e){
            System.out.println("Error al eliminar estudiante: " + e.getMessage());
        }
        finally {
            try{
                con.close();
            } catch (Exception e){
                System.out.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
        return false;
    }

    //Metodo para modificar estudiante
    public boolean modificarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConnection();
        String sql = "UPDATE estudiantes2024 SET nombre=?, apellido=?, telefono=?, email=? WHERE idestudiantes2024=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1,estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3,estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.setInt(5,estudiante.getIdEstudiante());
            ps.execute();
            return true;
        }catch(Exception e){
            System.out.println("Error al modificar el estudiante: "+e.getMessage());
        }//Fin catch
        finally {
            try{
                con.close();
            } catch (Exception e){
                System.out.println("Error al cerrar la conexion: "+e.getMessage());
            }//Fin catch
        }//Fin finally
        return false;
    }//Fin metodo modificarEstudiante

    public static void main(String[] args) {
        var estudianteDao = new EstudianteDAO();
        //Modificar estudiante
        //var estudianteModificado = new Estudiante("Juan Carlos","Pepes", "65437868754378", "noteentiendonadajuancarlo@gmail.com");
        //var modificado = estudianteDao.modificarEstudiante(estudianteModificado);
        //if(modificado)
        //    System.out.println("Estudiante modificado: "+estudianteModificado);
        //else
        //   System.out.println("No se modifico el estudiante: "+estudianteModificado);


        //Eliminar estudiante con id 3
        var estudianteEliminar = new Estudiante(3);
        var elminado = estudianteDao.eliminarEstudiante(estudianteEliminar);
        if(elminado)
            System.out.println("Estudiante eliminado = " + estudianteEliminar);
        else
            System.out.println("No se pudo eliminar estudiante: " + estudianteEliminar);

        //Listar los estudiantes: prueba
        System.out.println("Listado de estudiantes: ");
        List<Estudiante> estudiantes = estudianteDao.listarEstudiantes();
        estudiantes.forEach(System.out::println); //func lambda para imprimir



        //Agregar estudiante
        //var nuevoEstudiante = new Estudiante("Carlos","Lara","6545645","CarlosElpepe@gmail.com");
        //var agregado = estudianteDao.agregarEstudiante(nuevoEstudiante);
        //if(agregado)
        //   System.out.println("Estudiante agregado: "+nuevoEstudiante);
        //else
        //System.out.println("No se ha agregado estudiante: "+nuevoEstudiante);

        //Buscar por id
        //var estudiante1 = new Estudiante(1);
        //System.out.println("Estudiantes antes de la bisqueda: " + estudiante1);
        //var encontrado = estudianteDao.buscarEstudiantePorId(estudiante1);
        //if (encontrado)
         //   System.out.println("Estudiante encontrado: " + estudiante1);
        //else
        //   System.out.println("No se encontro el estudiante:  "+ estudiante1.getIdEstudiante());

    }
}

