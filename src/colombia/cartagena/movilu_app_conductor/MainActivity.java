package colombia.cartagena.movilu_app_conductor;

import colombia.cartagena.movilu_app_conductor.Rutas.FragmentRutaEntrada;
import colombia.cartagena.movilu_app_conductor.Rutas.FragmentRutaSalida;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		FragmentManager fm = getFragmentManager();

		FragmentTransaction ftra = fm.beginTransaction();

		FragmentRutaEntrada newFragment = FragmentRutaEntrada.newInstance(new Bundle(),MainActivity.this);

		ftra.replace(R.id.contenedor, newFragment);
		ftra.commit();

		Button bten = (Button) findViewById(R.id.btRutaEn);
		bten.setActivated(true);

		return true;
	}

	public void CambiarRuta(View view) {

		Fragment newFragment = null;
		// Bundle bundle = getIntent().getExtras();

		Button bten = (Button) findViewById(R.id.btRutaEn);
		Button btsa = (Button) findViewById(R.id.btRutaSa);

		if (view == findViewById(R.id.btRutaEn)) {
			newFragment = FragmentRutaEntrada.newInstance(null,this);
			// Log.e("TAG", "ENtrada");
			bten.setActivated(true);
			btsa.setActivated(false);

		} else if (view == findViewById(R.id.btRutaSa)) {
			newFragment = FragmentRutaSalida.newInstance(null);
			// Log.e("TAG", "Salida");
			bten.setActivated(false);
			btsa.setActivated(true);

		}

		FragmentManager fm = getFragmentManager();

		FragmentTransaction ftra = fm.beginTransaction();

		ftra.replace(R.id.contenedor, newFragment);
		// ftra.addToBackStack(null);
		ftra.commit();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:

			// nkkToast.makeText(MainActivity.this,
			// "presiono el home",Toast.LENGTH_SHORT).show();
			finish();
			return true;

		case R.id.logout:
			// close session
			SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
			prefs.getString("loged", "No");
			finish();

			return true;

		}

		return super.onOptionsItemSelected(item);
	}

}
