package colombia.cartagena.movilu_app_conductor.Entidad;

import java.util.Arrays;

public class Estudiante {

	String id, apellidos, barrio, correo, zona, direccion, fecha, nombre,
			universidad, ubicacion, estado, ref1, ref2, ref3;

	public String getRef1() {
		return ref1;
	}

	public void setRef1(String ref1) {
		this.ref1 = ref1;
	}

	public String getRef2() {
		return ref2;
	}

	public void setRef2(String ref2) {
		this.ref2 = ref2;
	}

	public String getRef3() {
		return ref3;
	}

	public void setRef3(String ref3) {
		this.ref3 = ref3;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Estudiante() {

	}

	public Estudiante(String id, String apellidos, String barrio,
			String correo, String zona, String direccion, String fecha,
			String nombre, String universidad, String ubicacion, String estado,
			String ref1, String ref2, String ref3) {
		this.id = id;
		this.apellidos = apellidos;
		this.barrio = barrio;
		this.correo = correo;
		this.zona = zona;
		this.direccion = direccion;
		this.fecha = fecha;
		this.nombre = nombre;
		this.universidad = universidad;
		this.ubicacion = ubicacion;
		this.estado = estado;
		this.ref1 = ref1;
		this.ref2 = ref2;
		this.ref3 = ref3;
	}

	@Override
	public String toString() {
		return "Estudiante id=" + id + ", apellidos=" + apellidos + ", barrio="
				+ barrio + ", correo=" + correo + ", zona=" + zona
				+ ", direccion=" + direccion + ", fecha=" + fecha + ", nombre="
				+ nombre + ", universidad=" + universidad + ", ubicacion="
				+ ubicacion + ", estado=" + estado + ", referencias="
				+ "ref1: " + ref1 + "ref2: " + ref2 + "ref3: " + ref3 + "";
	}

}
