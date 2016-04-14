package colombia.cartagena.movilu_app_conductor.Recorrido;


import colombia.cartagena.movilu_app_conductor.R;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

public class ListPuntoR {
	private int id;

	private Drawable ic_puntoR;
	private String punto_referencia;

	public ListPuntoR(int id, String punto_referencia) {
		this.id = id;
		this.punto_referencia = punto_referencia;
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

	public void setIc_puntoR(Activity fb) {

		ic_puntoR = fb.getResources().getDrawable(R.drawable.ic_lis_prefer);


	}

	public String getPunto_referencia() {
		return punto_referencia;
	}

	public void setPunto_referencia(String punto_referencia) {
		this.punto_referencia = punto_referencia;
	}

}
