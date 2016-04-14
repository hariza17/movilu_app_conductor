package colombia.cartagena.movilu_app_conductor.Rutas;

import java.util.ArrayList;

import colombia.cartagena.movilu_app_conductor.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentRutaSalida extends Fragment {
	View vista = null;

	public static FragmentRutaSalida newInstance(Bundle arguments) {
		FragmentRutaSalida f = new FragmentRutaSalida();
		if (arguments != null) {
			f.setArguments(arguments);
		}

		// contexto = ctx;
		// idUsuario = f.getArguments().getString("_id");
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		vista = inflater.inflate(R.layout.fragment_ruta_salida, container,
				false);

		ListView lista = (ListView) vista.findViewById(R.id.listViewRutaSalida);
		ArrayList<ListaRuta> arraytura = new ArrayList<ListaRuta>();

		// Introduzco los datos
		ListaRuta l1 = new ListaRuta("11:00am", 1);
		ListaRuta l2 = new ListaRuta("1:00pm", 1);
		ListaRuta l3 = new ListaRuta("3:00pm", 1);
		ListaRuta l4 = new ListaRuta("5:00pm", 1);
		ListaRuta l5 = new ListaRuta("6:00pm", 1);
		ListaRuta l6 = new ListaRuta("7:00pm", 1);
		arraytura.add(l1);
		arraytura.add(l2);
		arraytura.add(l3);
		arraytura.add(l4);
		arraytura.add(l5);
		arraytura.add(l6);
		// Creo el adapter personalizado
		AdapterListRuta adapter = new AdapterListRuta(getActivity(), arraytura);

		// Lo aplico
		lista.setAdapter(adapter);
		return vista;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (vista != null) {
			// Orientacion();

		}
		// btnHentrada.setActivated(true);
		// Log.e("LOCO", "Activado: " + btnHentrada.isActivated());

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// Orientacion();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// Orientacion();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

}
