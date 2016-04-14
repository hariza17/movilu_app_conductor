package colombia.cartagena.movilu_app_conductor.Rutas;


import colombia.cartagena.movilu_app_conductor.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

public class ListaRuta {
	private int id;
	private Drawable ic_ruta;
	private String hora;
	private int tipo;

	public ListaRuta(String hora, int tipo) {

		this.hora = hora;
		this.tipo = tipo;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}




	public Drawable getIc_ruta() {
		return ic_ruta;
	}

	public void setIc_ruta(Activity f,View v) {
		switch (tipo) {
		case 1:
			ic_ruta = f.getResources().getDrawable(R.drawable.ic_ruta);
			v.setBackgroundColor(Color.rgb(0, 160, 198));
			break;
		case 2:
			ic_ruta = f.getResources().getDrawable(R.drawable.ic_notify);
			v.setBackgroundColor(Color.rgb(223, 0, 36));
			break;

		case 3:
			ic_ruta = f.getResources().getDrawable(R.drawable.ic_notify);
			v.setBackgroundColor(Color.rgb(255, 210, 0));
			break;

		default:
			ic_ruta = f.getResources().getDrawable(R.drawable.ic_notify);
			v.setBackgroundColor(Color.rgb(0, 160, 198));
			break;
		}
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}
