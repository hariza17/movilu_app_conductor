package colombia.cartagena.movilu_app_conductor.ListaRecorrido;

import java.util.ArrayList;

import colombia.cartagena.movilu_app_conductor.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AdapterListRecorrido extends BaseAdapter {

	private Activity activity;
	private ArrayList<ListRecorrido> listaRecorrido;

	public AdapterListRecorrido(Activity activity, ArrayList<ListRecorrido> lista) {
		this.activity = activity;
		this.listaRecorrido = lista;

	}

	@Override
	public int getCount() {

		return listaRecorrido.size();
	}

	@Override
	public Object getItem(int arg0) {

		return listaRecorrido.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return listaRecorrido.get(arg0).getId();
	}

	@Override
	public View getView(int posicion, View vista, ViewGroup vGrup) {
		View v = vista;

		if (vista == null) {
			LayoutInflater inf = (LayoutInflater) this.activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inf.inflate(R.layout.item_lista_recorrido, null);
		}

		ListRecorrido lista_recorrido = listaRecorrido.get(posicion);

		ImageView avatar = (ImageView) v.findViewById(R.id.imageViewAvatar);


		lista_recorrido.setIc_Recorrido(this.activity);
		avatar.setImageDrawable(lista_recorrido.getIc_puntoR());

		TextView nombre = (TextView) v.findViewById(R.id.txtNombreListR);
		nombre.setText(lista_recorrido.getNombre());

		TextView direccion = (TextView) v.findViewById(R.id.txtDireccionListR);
		direccion.setText(lista_recorrido.getDireccion());

		TextView turno = (TextView) v.findViewById(R.id.txtNumeroEnListR);
		turno.setText(lista_recorrido.getTurno()+"");



		// Retornamos la vista
		return v;

	}

}
