package utn.estudiantes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import utn.estudiantes.modelo.Estudiantes2024;
import utn.estudiantes.servicio.EstudianteServicio;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class EstudiantesApplication implements CommandLineRunner {

	@Autowired
	private EstudianteServicio estudianteServicio;
	private static final Logger logger = LoggerFactory.getLogger(EstudiantesApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicación...");
		//Levantamos a la fabrica de Springboot
		SpringApplication.run(EstudiantesApplication.class, args);
		logger.info("Aplicación finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info(nl + "Ejecutandoo el método run de Springboot" + nl);
		var salir = false;
		var consola = new Scanner(System.in);
		while (!salir){
			mostrarMenu();
			salir = ejecutarOpciones(consola);
			logger.info(nl);
		}
	}

	private void mostrarMenu(){
		//logger.info(nl);
		logger.info("""
				******* Sistema de Estudiantes *******
				1. Listar Estudiantes
				2. Buscar Estudiante
				3. Agregar Estudiante
				4. Modificar Estudiante
				5. Eliminar Estudiante
				6. Salir
				Eliga una opción:
				""");
	}

	private boolean ejecutarOpciones(Scanner consola) {
		boolean salir = false;
		try {
			var opcion = Integer.parseInt(consola.nextLine());
			switch (opcion) {
				case 1 -> {
					logger.info(nl + "Listado de estudiantes" + nl);
					List<Estudiantes2024> estudiantes = estudianteServicio.listarEstudiantes();
					estudiantes.forEach(estudiante -> logger.info(estudiante.toString() + nl));
				}
				case 2 -> {
					logger.info("Digite el id del estudiante a buscar:");
					try {
						var idEstudiante = Integer.parseInt(consola.nextLine());
						Estudiantes2024 estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
						if (estudiante != null)
							logger.info("Estudiante encontrado: " + estudiante + nl);
						else
							logger.info("Estudiante NO encontrado" + nl);
					} catch (NumberFormatException e) {
						logger.error("Error: Debes ingresar un número válido para el ID del estudiante.");
					}
				}
				case 3 -> {
					logger.info("Agregar estudiante: " + nl);
					logger.info("Nombre: ");
					var nombre = consola.nextLine();
					logger.info("Apellido: ");
					var apellido = consola.nextLine();
					logger.info("Teléfono: ");
					var telefono = consola.nextLine();
					logger.info("Email: ");
					var email = consola.nextLine();

					// Crear el objeto estudiante sin el id
					var estudiante = new Estudiantes2024();
					estudiante.setNombre(nombre);
					estudiante.setApellido(apellido);
					estudiante.setTelefono(telefono);
					estudiante.setEmail(email);

					// Guardar el estudiante en la base de datos
					estudianteServicio.guardarEstudiante(estudiante);
					logger.info("Estudiante agregado: " + estudiante + nl);
				}
				case 4 -> {
					logger.info("Modificar Estudiante: " + nl);
					logger.info("Ingrese el id del estudiante: ");
					try {
						var idEstudiante = Integer.parseInt(consola.nextLine());
						Estudiantes2024 estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
						if (estudiante != null) {
							logger.info("Nombre: ");
							var nombre = consola.nextLine();
							logger.info("Apellido: ");
							var apellido = consola.nextLine();
							logger.info("Teléfono: ");
							var telefono = consola.nextLine();
							logger.info("Email: ");
							var email = consola.nextLine();
							estudiante.setNombre(nombre);
							estudiante.setApellido(apellido);
							estudiante.setTelefono(telefono);
							estudiante.setEmail(email);
							estudianteServicio.guardarEstudiante(estudiante);
							logger.info("Estudiante modificado: " + estudiante + nl);
						} else {
							logger.info("Estudiante no encontrado con el id: " + idEstudiante);
						}
					} catch (NumberFormatException e) {
						logger.error("Error: Debes ingresar un número válido para el ID del estudiante.");
					}
				}
				case 5 -> {
					logger.info("Eliminar estudiante: " + nl);
					logger.info("Digite el id del estudiante: ");
					try {
						var idEstudiante = Integer.parseInt(consola.nextLine());
						var estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
						if (estudiante != null) {
							estudianteServicio.eliminarEstudiante(estudiante);
							logger.info("Estudiante eliminado: " + estudiante + nl);
						} else {
							logger.info("Estudiante no encontrado con el id: " + idEstudiante);
						}
					} catch (NumberFormatException e) {
						logger.error("Error: Debes ingresar un número válido para el ID del estudiante.");
					}
				}
				case 6 -> {
					logger.info("Vuelva pronto" + nl + nl);
					salir = true;
				}
				default -> logger.error("Opción inválida. Debes ingresar un número entre 1 y 6.");
			}
		} catch (NumberFormatException e) {
			logger.error("Error: Debes ingresar un número válido para elegir una opción.");
		}
		return salir;
	}

}
