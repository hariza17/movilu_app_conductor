package colombia.cartagena.movilu_app_conductor.Session;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import colombia.cartagena.movilu_app_conductor.MainActivity;
import colombia.cartagena.movilu_app_conductor.DataBase.DbHelper;

import colombia.cartagena.movilu_app_conductor.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpClient.StringCallback;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.JSONObjectBody;
import com.koushikdutta.async.http.callback.HttpConnectCallback;
import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.EventCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;


import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	String nombre, passw, correo, _id;
	EditText txtNroMovil, txtPassword;
	int log = 0;


	// User Session Manager Class
	UserSessionManager session;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// User Session Manager
		session = new UserSessionManager(getApplicationContext());

		// get Email, Password input text
		txtNroMovil = (EditText) findViewById(R.id.txtNroMovil);
		txtPassword = (EditText) findViewById(R.id.txtPassword);


//		if(getPreferences("loged").equals("Ok")){
//			Intent intent= new Intent(LoginActivity.this,MainActivity.class);
//			startActivity(intent);
//		}

		// User Login button
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		// Login button click event
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// Get username, password from EditText
				String nroMovil = txtNroMovil.getText().toString();
				String password = txtPassword.getText().toString();

				getVehiculo(nroMovil, password);

			}
		});
	}


	/************************************ Http Async ****************************************************/
	private void getVehiculo(final String nroMovil, final String password) {
		String url = getResources().getString(R.string.ServerUrl)+ "/api/get/vehiculo/" + nroMovil + "/" + password;

		AsyncHttpClient.getDefaultInstance().executeString(new AsyncHttpGet(url), new StringCallback() {

					@Override
					public void onCompleted(Exception arg0,AsyncHttpResponse arg1, String argument) {
						// TODO Auto-generated method stub

						try {

							JSONObject jo = new JSONObject(argument);

							if(jo.getString("value").equals("true")){
								addPreferences("_id", jo.getJSONObject("vehiculo").getString("_id"));
								addPreferences("placa", jo.getJSONObject("vehiculo").getString("placa"));
								addPreferences("nroMovil", jo.getJSONObject("vehiculo").getString("codigo"));
								addPreferences("loged", "Ok");

								System.out.println("id: " + getPreferences("_id"));
								System.out.println("placa: " + getPreferences("placa"));
								System.out.println("nroMovil: " + getPreferences("nroMovil"));
								System.out.println("loged: " + getPreferences("loged"));

								Intent intent= new Intent(LoginActivity.this,MainActivity.class);
								startActivity(intent);

							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}


	/************************************* Preferences *********************************************************/
	private void addPreferences(String key, String value) {
		SharedPreferences prefs = getSharedPreferences("MisPreferencias",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String getPreferences(String key) {
		String value = "";
		SharedPreferences prefs = getSharedPreferences("MisPreferencias",
				Context.MODE_PRIVATE);

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

}
