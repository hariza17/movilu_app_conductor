package colombia.cartagena.movilu_app_conductor.ListaRecorrido;


import colombia.cartagena.movilu_app_conductor.R;
import android.app.Activity;
import android.graphics.drawable.Drawable;

public class ListRecorrido {
	private int id;

	private Drawable ic_puntoR;
	private String nombre;
	private String direccion;
	private int turno;

	public ListRecorrido(int id, String nombre, String direccion, int turno) {

		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.turno = turno;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Drawable getIc_puntoR() {
		return ic_puntoR;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public void setIc_Recorrido(Activity f) {

		ic_puntoR = f.getResources().getDrawable(R.drawable.avatar);

	}

}
