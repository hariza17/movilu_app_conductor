package colombia.cartagena.movilu_app_conductor.Rutas;

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

public class AdapterListRuta extends BaseAdapter {

	private Activity activity;
	private ArrayList<ListaRuta> listaNoti;

	public AdapterListRuta(Activity activity, ArrayList<ListaRuta> lista) {
		this.activity = activity;
		this.listaNoti = lista;

	}

	@Override
	public int getCount() {

		return listaNoti.size();
	}

	@Override
	public Object getItem(int arg0) {

		return listaNoti.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return listaNoti.get(arg0).getId();
	}

	@Override
	public View getView(int posicion, View vista, ViewGroup vGrup) {
		View v = vista;

		if (vista == null) {
			LayoutInflater inf = (LayoutInflater) this.activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inf.inflate(R.layout.item_ruta_entrada, null);
		}

		ListaRuta noti = listaNoti.get(posicion);

		ImageView icon = (ImageView) v.findViewById(R.id.imageViewRuta);

		LinearLayout layout = (LinearLayout) v.findViewById(R.id.contTipoRuta);
		noti.setIc_ruta(this.activity, layout);
		icon.setImageDrawable(noti.getIc_ruta());

		TextView fecha = (TextView) v.findViewById(R.id.txtHoraRuta);
		fecha.setText(noti.getHora());

		// Retornamos la vista

		return v;

	}

}
