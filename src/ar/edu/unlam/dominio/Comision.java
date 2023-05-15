package ar.edu.unlam.dominio;

public class Comision {
	private static final int CANTIDAD_PERSONAS = 100;
	private static final Integer DNI_DOCENTE = 34000111;
	private static final String CONTRASENIA = "Pb12c2022";

	private int id;
	private String materia;
	private char turno; // M -> Mañana | N -> Noche
	private Persona[] personas;
	private Integer dni;
	private String contrasenia;

	/**
	 * El constructor debe generar las condiciones necesaias para garantizar el
	 * correcto funcionamiento de la app
	 */
	public Comision(int id, String Materia, Integer dni, String contrasenia) {
		this.id = id;
		this.materia = materia;
		this.dni = dni;
		this.contrasenia = contrasenia;
		this.personas = new Persona[CANTIDAD_PERSONAS];
	}

	public Comision() {
		this.dni = DNI_DOCENTE;
		this.contrasenia = CONTRASENIA;
		this.personas = new Persona[CANTIDAD_PERSONAS];
	}

	/**
	 * Agrega una persona a la comision.
	 * 
	 * @param persona Persona que se agregara
	 * @return true en caso de exito
	 * 
	 *         Debe proporcionar el codigo necesario para que funcione correctamente
	 */
	public boolean ingresarPersona(Persona persona) {
		boolean sePudoAgregar = false;
		for (int i = 0; i < personas.length; i++) {
			if (!sePudoAgregar && personas[i] == null) { // se puede usar Equas()
				personas[i] = persona;
				sePudoAgregar = true;
			}
		}
		return sePudoAgregar;

	}

	/**
	 * Verifica si la persona que llega como parametro, existe en la comision //
	 * TODO: validar que la persona no exista en la comision (por dni).
	 * 
	 * @param persona Persona que se validara
	 * @return true en caso de existir en la comision
	 * 
	 *         Debe proporcionar el codigo necesario para que funcione correctamente
	 * 
	 */
	private boolean existePersonaEnComision(Persona persona) {
		boolean yaExiste = false;
		for (int i = 0; i < personas.length; i++) {
			if (!yaExiste && personas[i] != null && personas[i].getDni().equals(persona.getDni())) {
				yaExiste = true;
			}
		}
		return yaExiste;
	}

	/**
	 * Devuelve una persona que debe ser encontrada por su DNI proporionado como
	 * parametro
	 * 
	 * @param dni DNI de la persona
	 * @return Persona Persona que aplica a la busqueda o null en caso de no
	 *         encontrarla.
	 * 
	 *         Debe proporcionar el codigo necesario para que funcione correctamente
	 * 
	 */
	public Persona buscarPorDni(Integer dni) {
		Persona persona = null;
		boolean personaEncontrado = false;
		for (int i = 0; i < personas.length; i++) {
			if (!personaEncontrado && personas[i] != null && personas[i].getDni().equals(dni)) {
				persona = personas[i];
				personaEncontrado = true;
			}
		}
		return persona;

	}

	/**
	 * Inicia la sesion de una persona en la comision
	 * 
	 * @param dni         DNI de la persona
	 * @param contrasenia Contrasenia de la persona
	 * @param esDocente   Define si la persona es el docente
	 * @return true en caso de exito
	 * 
	 *         Debe proporcionar el codigo necesario para que funcione correctamente
	 */
	public boolean iniciarSesion(Integer dni, String contrasenia, Boolean esDocente) {
		boolean pudoIniciarSesion = false;
		// si es docente lo traigo de la comision
		// sino es docente busco en las personas

		if (Comision.DNI_DOCENTE.equals(dni) && Comision.CONTRASENIA.equals(contrasenia)) {
			pudoIniciarSesion = true;
		} else {
			for (int i = 0; i < personas.length; i++) {
				if (!pudoIniciarSesion && personas[i] != null && personas[i].getDni().equals(dni)
						&& personas[i].getContrasenia().equals(contrasenia)) {
					pudoIniciarSesion = true;
				}
			}
		}
		return pudoIniciarSesion;
	}

	/**
	 * Actualiza el valor de la Nota para la persona que cumple con el DNI pasado
	 * como parametro
	 * 
	 * @param dni  DNI de la persona
	 * @param nota Nota que se actualizará
	 * 
	 *             Debe proporcionar el codigo necesario para que funcione
	 *             correctamente
	 */
	public boolean recuperarNota(Integer dni, Nota nota) {
		boolean seRecuperoNota = false;

		Persona persona = this.buscarPorDni(dni);
		persona.setParcial1(nota);
		seRecuperoNota = true;
//		if (persona == null) {
//			return false;
//		}
		return seRecuperoNota;
//		return persona.recuperarNota(nota);
	}

	/**
	 * Devuelve un array de personas que pertenecen a la carrera proporcionada como
	 * parametro y promocionaron la materia
	 * 
	 * @param carrera Carrera a la que pertenece la persona
	 * @return array de personas que pertenecen a la carrera
	 * 
	 *         Debe proporcionar el codigo necesario para que funcione correctamente
	 */
	public Persona[] obtenerPersonasDeLaCarreraQuePromocionaron(Carrera carrera) {
		// TODO: las personas devueltas deben estar ordenadas por DNI
		Persona personasDeLaCarreraQuePromocionaron[] = new Persona[personas.length];

		this.ordenarPersonasPorDni();
		int iteracionArray = 0;
		for (int i = 0; i < personas.length; i++) {
			if (personas[i] != null && personas[i].getCarrera().equals(carrera)
					&& personas[i].obtenerCondicionFinal().equals(CondicionFinal.PROMOCIONA)) {
				personasDeLaCarreraQuePromocionaron[iteracionArray++] = personas[i];
			}
		}
		return personasDeLaCarreraQuePromocionaron;
	}

	/**
	 * Devuelve el calculo del promedio de la nota 2 de las personas en la comision
	 * 
	 * @return Double Promedio
	 * 
	 *         Debe proporcionar el codigo necesario para que funcione correctamente
	 */
	public Double obtenerElPromedioDeNota2DeLasPersonasQueCursaron() {
		Double promedio = 0.0;
		int acumuladorNotas = 0;
		int contadorNotas = 0;
		for (int i = 0; i < personas.length; i++) {
			if (personas[i] != null) {
				acumuladorNotas += personas[i].getParcial2().getValor();
				contadorNotas++;
			}
		}
		promedio = (double) (acumuladorNotas / contadorNotas);
		return promedio;
	}

	/**
	 * Ordena las personas en la comision por DNI
	 * 
	 * Debe proporcionar el codigo necesario para que funcione correctamente
	 */
	private void ordenarPersonasPorDni() {
		Persona auxiliar = null;
		for (int j = 0; j < personas.length; j++) {
			for (int i = 0; i < personas.length - 1; i++) {
				if (personas[i] != null && personas[i + 1] != null && personas[i].getDni() > personas[i + 1].getDni()) {
					auxiliar = personas[i + 1];
					personas[i + 1] = personas[i];
					personas[i] = auxiliar;
				}
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public char getTurno() {
		return turno;
	}

	public void setTurno(char turno) {
		this.turno = turno;
	}

	public Persona[] getPersonas() {
		return personas;
	}

	public void setPersonas(Persona[] personas) {
		this.personas = personas;
	}

	public Integer getDni() {
		return dni;
	}

	public void setUsuario(Integer dni) {
		this.dni = dni;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

}
