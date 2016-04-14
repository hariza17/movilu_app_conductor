package colombia.cartagena.movilu_app_conductor.Recorrido;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.JSONObjectBody;
import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.EventCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;

import colombia.cartagena.movilu_app_conductor.MapActivity;
import colombia.cartagena.movilu_app_conductor.DataBase.DbHelper;
import colombia.cartagena.movilu_app_conductor.Entidad.Estudiante;
import colombia.cartagena.movilu_app_conductor.ListaRecorrido.ListaRecorridoActivity;
import colombia.cartagena.movilu_app_conductor.Rutas.AdapterListRuta;
import colombia.cartagena.movilu_app_conductor.Rutas.ListaRuta;

import colombia.cartagena.movilu_app_conductor.R;

import android.media.RingtoneManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;

public class RecorridoActivity extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recorrido);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);


		ListView lista = (ListView) findViewById(R.id.listViewPuntoR);
		ArrayList<ListPuntoR> arraytura = new ArrayList<ListPuntoR>();

		// Introduzco los datos

		DbHelper db = new DbHelper(RecorridoActivity.this);
		ArrayList<Estudiante> estudiantes =  db.obtenerEstudiantes();
		int size = estudiantes.size();
		if(size==0){
			Estudiante estudiante = estudiantes.get(0);
			if(!estudiante.getRef1().equals("")){
				arraytura.add(new ListPuntoR(1,estudiante.getRef1().toLowerCase()));
			}

			if(!estudiante.getRef2().equals("")){
				arraytura.add(new ListPuntoR(1,estudiante.getRef2().toLowerCase()));
			}

			if(!estudiante.getRef3().equals("")){
				arraytura.add(new ListPuntoR(1,estudiante.getRef3().toLowerCase()));
			}

			if(!estudiante.getNombre().equals("")){
				TextView tNombre = (TextView)findViewById(R.id.txtNombre);
				tNombre.setText(estudiante.getNombre().toUpperCase());
			}

			if(!estudiante.getDireccion().equals("")){
				TextView tDireccion = (TextView)findViewById(R.id.txtDireccion);
				tDireccion.setText(estudiante.getDireccion().toUpperCase());
			}



		}

		// Creo el adapter personalizado
		AdapterListPuntoR adapter = new AdapterListPuntoR(this, arraytura);

		// Lo aplico
		lista.setAdapter(adapter);

		Button btVerLista = (Button) findViewById(R.id.btMostarLista);

		Button btVerMapa = (Button) findViewById(R.id.btMostarMapa);

		btVerLista.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mostrarLista();
			}
		});

		btVerMapa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mostrarMapa();
			}
		});

		Button btnNotificar = (Button) findViewById(R.id.Button02);
		btnNotificar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}




	public String getPreferences(String key) {
		String value = "";
		SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

		if (key.equals("_id")) {
			value = prefs.getString(key, "none");
		}

		if (key.equals("placa")) {
			value = prefs.getString(key, "none");
		}

		if (key.equals("nroMovil")) {
			value = prefs.getString(key, "none");
		}

		if (key.equals("loged")) {
			value = prefs.getString(key, "none");
		}

		return value;
	}

	public void mostrarLista() {
		Intent i = new Intent(this, ListaRecorridoActivity.class);
		startActivity(i);
	}

	public void mostrarMapa() {
		Intent i = new Intent(this, MapActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.recorrido, menu);

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
