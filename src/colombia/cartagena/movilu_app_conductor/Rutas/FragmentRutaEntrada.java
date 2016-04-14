package colombia.cartagena.movilu_app_conductor.Rutas;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import colombia.cartagena.movilu_app_conductor.MainActivity;
import colombia.cartagena.movilu_app_conductor.DataBase.DbHelper;
import colombia.cartagena.movilu_app_conductor.Entidad.Estudiante;
import colombia.cartagena.movilu_app_conductor.Recorrido.RecorridoActivity;
import colombia.cartagena.movilu_app_conductor.Session.LoginActivity;

import colombia.cartagena.movilu_app_conductor.R;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.AsyncHttpClient.StringCallback;
import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;

public class FragmentRutaEntrada extends Fragment {
	View vista = null;
	ListView lista = null;
	String hora = "";
	static Context contexto;
	static MainActivity mActivity;
	static ArrayList<JSONObject> Ids;
	
	public static FragmentRutaEntrada newInstance(Bundle arguments,MainActivity activity) {
		FragmentRutaEntrada f = new FragmentRutaEntrada();
		if (arguments != null) {
			f.setArguments(arguments);
		}

		mActivity = activity;
		contexto = activity.getApplicationContext();
		Ids = new ArrayList<JSONObject>();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		vista = inflater.inflate(R.layout.fragment_ruta_entrada, container,false);

		lista = (ListView) vista.findViewById(R.id.listViewRutaEntrada);
		ArrayList<ListaRuta> arraytura = new ArrayList<ListaRuta>();

		// Introduzco los datos
		ListaRuta l1 = new ListaRuta("7:00am", 1);
		ListaRuta l2 = new ListaRuta("9:00am", 1);
		ListaRuta l3 = new ListaRuta("11:00am", 1);
		ListaRuta l4 = new ListaRuta("1:00pm", 1);
		ListaRuta l5 = new ListaRuta("3:00pm", 1);

		arraytura.add(l1);
		arraytura.add(l2);
		arraytura.add(l3);
		arraytura.add(l4);
		arraytura.add(l5);

		// Creo el adapter personalizado
		AdapterListRuta adapter = new AdapterListRuta(getActivity(), arraytura);

		// Lo aplico
		lista.setAdapter(adapter);
		accionLista();

		return vista;
	}

	public void accionLista() {

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adpater, View arg1,
					int arg2, long arg3) {

				switch (arg2) {
				case 0:
					hora = "7:00_AM";
					break;
				case 1:
					hora = "9:00_AM";
					break;
				case 2:
					hora = "11:00_AM";
					break;
				case 3:
					hora = "1:00_PM";
					break;
				case 4:
					hora = "3:00_PM";
					break;
				}

				String idVehiculo = getPreferences("_id");
				getVehiculo(idVehiculo, hora);
				
				
			}
		});
	}

	/************************************ Http Async ****************************************************/
	private void getVehiculo(final String idVehiculo, final String hora) {
		String url = getResources().getString(R.string.ServerUrl)+ "/api/get/estudiantes/" + idVehiculo + "/" + hora + "/entrada";

		AsyncHttpClient.getDefaultInstance().executeString(
				new AsyncHttpGet(url), new StringCallback() {

					@Override
					public void onCompleted(Exception arg0,AsyncHttpResponse arg1, String argument) {
						// TODO Auto-generated method stub

						try {

							JSONObject jo = new JSONObject(argument);
							DbHelper db = new DbHelper(contexto);
							db.borrar();
							if (jo.getString("value").equals("true")) {
								Ids.clear();
								JSONArray json = jo.getJSONArray("estudiantes");
								for (int i = 0; i < json.length(); i++) {

									String id = json.getJSONObject(i).getString("_id");
									String apellidos = json.getJSONObject(i).getString("apellidos");
									String barrio = json.getJSONObject(i).getString("barrio");
									String correo = json.getJSONObject(i).getString("correo");
									String Idzona = json.getJSONObject(i).getString("zona");
									String direccion = json.getJSONObject(i).getString("direccion");
									String fecha = json.getJSONObject(i).getString("fecha_nacimiento");
									String nombre = json.getJSONObject(i).getString("nombre");
									String Iduniversidad = json.getJSONObject(i).getString("universidad");
									JSONArray ubicacion = json.getJSONObject(i).getJSONObject("ubicacion").getJSONArray("coordinates");
									String latlng = ubicacion.get(0) + ","+ ubicacion.get(1);
									String estado = json.getJSONObject(i).getString("estado");

									JSONArray array = json.getJSONObject(i).getJSONArray("puntos_de_referencia");
									
									for (int j = 0; j < array.length(); j++) {
										System.out.println("punto de referencia {"+ (j+1) +"} " + array.getString(j));
									}
									
									Estudiante estudiante = new Estudiante();
									
									estudiante.setId(id);
									estudiante.setApellidos(apellidos);
									estudiante.setBarrio(barrio);
									estudiante.setCorreo(correo);
									estudiante.setZona(Idzona);
									estudiante.setDireccion(direccion);
									estudiante.setFecha(fecha);
									estudiante.setNombre(nombre);
									estudiante.setUniversidad(Iduniversidad);
									estudiante.setUbicacion(latlng);
									estudiante.setEstado(estado);
									
									if(array.length()==0){
										estudiante.setRef1("");
										estudiante.setRef2("");
										estudiante.setRef3("");
									}
									
									if(array.length() == 1){
										estudiante.setRef1(array.getString(0).toString());
										estudiante.setRef2("");
										estudiante.setRef3("");
									}
									
									if (array.length() == 2) {
										estudiante.setRef1(array.getString(0).toString());
										estudiante.setRef2(array.getString(1).toString());
										estudiante.setRef3("");
									}
									
									if (array.length() == 3) {
										estudiante.setRef1(array.getString(0).toString());
										estudiante.setRef2(array.getString(1).toString());
										estudiante.setRef3(array.getString(2).toString());
									}
									
									
									Boolean result = db.InsertarEstudiante(estudiante);
									
									System.out.println(estudiante.toString());

								}
								
								 ArrayList<Estudiante> estudiantes = db.obtenerEstudiantes();
								 int size = estudiantes.size();
								if(size>0){
									
									for (int i = 0; i < estudiantes.size(); i++) {
										Estudiante e = estudiantes.get(i);
										try {
											JSONObject object = new JSONObject();
											object.put("_id", e.getId());
											
											Ids.add(object);
										
//											System.out.println("Id: " + e.getId());
//											System.out.println("Apellidos: " + e.getApellidos());
//											System.out.println("Barrio: " + e.getBarrio());
//											System.out.println("Correo: " + e.getCorreo());
//											System.out.println("IdZona: " + e.getZona());
//											System.out.println("Dirección: " + e.getDireccion());
//											System.out.println("Fecha: " + e.getFecha());
//											System.out.println("Nombre: " + e.getNombre());
//											System.out.println("IdUniversidad: " + e.getUniversidad());
//											System.out.println("Ubicación: " + e.getUbicacion());
//											System.out.println("Estado: " + e.getEstado());
//											System.out.println("Ref1: " + e.getRef1());
//											System.out.println("Ref2: " + e.getRef2());
//											System.out.println("Ref3: " + e.getRef3());
											
										} catch (JSONException ex) {
											// TODO Auto-generated catch block
											ex.printStackTrace();
										}
									}
								}
								
								String url = getResources().getString(R.string.ServerUrl);
								if(Ids.size()>0){
									emitSocketIO(url,Ids);
								}
								
								Intent intent = new Intent(contexto,RecorridoActivity.class);
								mActivity.startActivity(intent);

							} else {
								System.out.println("false");
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
	
	
	/**************************************** Socket.io async ********************************************/
	private void emitSocketIO(String url, final ArrayList<JSONObject> Ids) {
		
		SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), url,new ConnectCallback() {
					@Override
					public void onConnectCompleted(Exception ex,SocketIOClient client) {
						if (ex != null) {
							ex.printStackTrace();
							return;
						}

						String cadena_json = "";
						cadena_json += "[{ \"id_vehiculo\" :\""+getPreferences("_id")+"\",";
						cadena_json += "estudiantes : [";
						
						System.out.println("Tam: "+ Ids.size());
						
						for (int i = 0; i < Ids.size(); i++) {
							if (i == (Ids.size()-1)) {
								cadena_json += Ids.get(i);
															}else{
								cadena_json += Ids.get(i)+",";
							}
						
						}
						cadena_json += "]}]";
						
						try {
							JSONArray array = new JSONArray(cadena_json);
							System.out.println(cadena_json);
							client.emit("broadcast",array);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	public String getPreferences(String key) {
		String value = "";
		SharedPreferences prefs = mActivity.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

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
