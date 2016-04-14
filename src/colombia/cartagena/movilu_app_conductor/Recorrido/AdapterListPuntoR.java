package colombia.cartagena.movilu_app_conductor.Recorrido;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import colombia.cartagena.movilu_app_conductor.R;

public class AdapterListPuntoR extends BaseAdapter {

	private Activity activity;
	private ArrayList<ListPuntoR> listaPuntoR;

	public AdapterListPuntoR(Activity activity, ArrayList<ListPuntoR> lista) {
		this.activity = activity;
		this.listaPuntoR = lista;

	}

	@Override
	public int getCount() {

		return listaPuntoR.size();
	}

	@Override
	public Object getItem(int arg0) {

		return listaPuntoR.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return listaPuntoR.get(arg0).getId();
	}

	@Override
	public View getView(int posicion, View vista, ViewGroup vGrup) {
		View v = vista;

		if (vista == null) {
			LayoutInflater inf = (LayoutInflater) this.activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inf.inflate(R.layout.item_list_preferencia, null);
		}

		ListPuntoR noti = listaPuntoR.get(posicion);

		ImageView icon = (ImageView) v.findViewById(R.id.imageViewPuntoR);


		noti.setIc_puntoR(this.activity);
		icon.setImageDrawable(noti.getIc_puntoR());

		TextView fecha = (TextView) v.findViewById(R.id.txtPuntoR);
		fecha.setText(noti.getPunto_referencia());

		// Retornamos la vista
		return v;

	}

}
