package colombia.cartagena.movilu_app_conductor.ListaRecorrido;

import java.util.ArrayList;

import colombia.cartagena.movilu_app_conductor.R;
import colombia.cartagena.movilu_app_conductor.DataBase.DbHelper;
import colombia.cartagena.movilu_app_conductor.Entidad.Estudiante;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ListaRecorridoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_recorrido);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		ListView lista = (ListView) findViewById(R.id.listViewListaRecorrido);
		ArrayList<ListRecorrido> arraytura = new ArrayList<ListRecorrido>();

		DbHelper db = new DbHelper(ListaRecorridoActivity.this);
		ArrayList<Estudiante> estudiantes = db.obtenerEstudiantes();
		int size = estudiantes.size();
		if(size>0){
			for (int i = 0; i < size ; i++) {
				Estudiante estudiante = estudiantes.get(i);
				if(!estudiante.getNombre().equals("")){
					String direccion = estudiante.getBarrio() + " " + estudiante.getDireccion();
					arraytura.add(new ListRecorrido(1, estudiante.getNombre().toUpperCase(),direccion,1));
				}
			}

		}
		// Introduzco los datos
//		ListRecorrido lr1 = new ListRecorrido(1, "Maria monica lujan correa","Blasdelezo calle 9a kr 11-55", 1);
//		ListRecorrido lr2 = new ListRecorrido(1, "Maria monica lujan correa",
//				"Blasdelezo calle 9a kr 11-55", 1);
//		ListRecorrido lr3 = new ListRecorrido(1, "Maria monica lujan correa",
//				"Blasdelezo calle 9a kr 11-55", 1);
//		ListRecorrido lr4 = new ListRecorrido(1, "Maria monica lujan correa",
//				"Blasdelezo calle 9a kr 11-55", 1);
//
//		arraytura.add(lr1);
//		arraytura.add(lr2);
//		arraytura.add(lr3);
//		arraytura.add(lr4);

		// Creo el adapter personalizado
		AdapterListRecorrido adapter = new AdapterListRecorrido(this, arraytura);

		// Lo aplico
		lista.setAdapter(adapter);


	}

	public void irAtras() {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_recorrido, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; goto parent activity.
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
