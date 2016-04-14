package colombia.cartagena.movilu_app_conductor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MapActivity extends Activity implements LocationListener {

	private MapView MiMapView;
	private GoogleMap mMap;
	private Bundle mBundle;
	Marker melbourne;

	private String direccion;
	boolean bandera = false;

	View vista;
	private static Context context;
	protected LocationManager locationManager;
	private Location MyLocation;

	ArrayList<LatLng> markerPoints;

	private double myLat = 0;
	private double myLng = 0;

	Marker MyMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		markerPoints = new ArrayList<LatLng>();

		// getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// get user data from session

		// get name
		// String name = user.get(UserSessionManager.KEY_NAME);
		//
		// // get email
		// String email = user.get(UserSessionManager.KEY_EMAIL);

		// getActionBar().setTitle(email);

		try {
			MapsInitializer.initialize(getApplicationContext());

			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

			MyLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			setMyLat(MyLocation.getLatitude());
			setMyLng(MyLocation.getLongitude());

			Toast.makeText(getApplicationContext(),"Lat: " + getMyLat() + " , " + "Long: " + getMyLng(),Toast.LENGTH_LONG).show();

		} catch (final Exception e) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(),"JAJA: " + e.getMessage(), Toast.LENGTH_LONG)
							.show();
				}
			});

		}

		MiMapView = (MapView) findViewById(R.id.mapa);

		MiMapView.onCreate(savedInstanceState);

		setUpMapIfNeeded(MiMapView);

	}

	private void setUpMapIfNeeded(View inflatedView) {

		mMap = MiMapView.getMap();
		if (mMap != null) {
			OpcionesMap();
			Log.e("Tag", "Mapa2");
			mMap.setOnCameraChangeListener(new OnCameraChangeListener() {

				@Override
				public void onCameraChange(CameraPosition position) {
					// TODO Auto-generated method stub
					Toast.makeText(
							getApplicationContext(),
							"Tilt: " + position.tilt + ", Bearing: "
									+ position.bearing + ", Zoom: "
									+ position.zoom, Toast.LENGTH_SHORT).show();
					System.out.println("Tilt: " + position.tilt);
					System.out.println("Bearing: " + position.bearing);
					System.out.println("Zoom: " + position.zoom);

				}
			});
		}
	}

	private void OpcionesMap() {

		LatLng cartagena = new LatLng(MyLocation.getLatitude(),MyLocation.getLongitude());
		//LatLng cartagena = new LatLng(10.408516, -75.536188);

		float zoom = 20;
		float tilt = 67.5f;
		int bearing = (int) MyLocation.getBearing();
		//int bearing = 0;
		CameraPosition camp = new CameraPosition(cartagena, zoom, tilt, bearing);
		mMap.moveCamera(CameraUpdateFactory.newCameraPosition(camp));
		mMap.setMyLocationEnabled(true);

		LatLng origen = new LatLng(getMyLat(), getMyLng());

		// LatLng origen = new LatLng(10.41762803, -75.56258082);

		LatLng destino_ = new LatLng(10.39128917, -75.5510366);

		mMap.addMarker(new MarkerOptions().position(destino_));
		// Getting URL to the Google Directions API

		String url = getDirectionsUrl(origen, destino_);

		DownloadTask downloadTask = new DownloadTask();

		// Start downloading json data from Google Directions API

		downloadTask.execute(url);

		MyMarker = mMap.addMarker(new MarkerOptions()
				.position(origen)
				.title("My Location")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_marker)));

		// Closes the Polyline.

		// Get back the mutable Polyline

		// -------------------------------------------------------------------------------

		// mMap.setOnMapClickListener(new OnMapClickListener() {
		//
		// @Override
		// public void onMapClick(LatLng point) {
		//
		// // Already two locations
		// if (markerPoints.size() > 1) {
		// markerPoints.clear();
		// mMap.clear();
		// }
		//
		// // Adding new item to the ArrayList
		// markerPoints.add(point);
		//
		// // Creating MarkerOptions
		// MarkerOptions options = new MarkerOptions();
		//
		// // Setting the position of the marker
		// options.position(point);
		//
		// /**
		// * For the start location, the color of marker is GREEN and for
		// * the end location, the color of marker is RED.
		// */
		//
		// if (markerPoints.size() == 1) {
		// options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		// } else if (markerPoints.size() == 2) {
		// options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		// }
		//
		// // Add new marker to the Google Map Android API V2
		// mMap.addMarker(options);
		//
		// // Checks, whether start and end locations are captured
		// if (markerPoints.size() >= 2) {
		//
		// }
		//
		// }
		// });
		//
		// // melbourne = mMap.addMarker(new
		// MarkerOptions().position(locationManager.));

		// //
		// CameraUpdate ActualizarCamera = CameraUpdateFactory
		// .newCameraPosition(camPos);
		// mMap.animateCamera(ActualizarCamera);

		// MapView map = (MapView) vista.findViewById(R.id.mapa);

		// ImageView imgMarker=(ImageView)vista.findViewById(R.id.imageViewId);
		// imgMarker.set

	}

	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {

			// For storing data from web service
			String data = "";

			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);

		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {

			try {
				ArrayList<LatLng> points = null;
				PolylineOptions lineOptions = null;
				MarkerOptions markerOptions = new MarkerOptions();

				// Traversing through all the routes
				for (int i = 0; i < result.size(); i++) {
					points = new ArrayList<LatLng>();
					lineOptions = new PolylineOptions();

					// Fetching i-th route
					List<HashMap<String, String>> path = result.get(i);

					// Fetching all the points in i-th route
					for (int j = 0; j < path.size(); j++) {
						HashMap<String, String> point = path.get(j);

						double lat = Double.parseDouble(point.get("lat"));
						double lng = Double.parseDouble(point.get("lng"));
						LatLng position = new LatLng(lat, lng);

						points.add(position);
					}

					// Adding all the points in the route to LineOptions
					lineOptions.addAll(points);
					lineOptions.width(50);
					lineOptions.color(Color.rgb(0, 160, 198));

				}

				// Drawing polyline in the Google Map for the i-th route
				mMap.addPolyline(lineOptions);

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void CrearMarker(LatLng coordenadas, String titulo, String detalle,
			int Idimg, String texto) {

		// pintarNotificacion bitmap = new pintarNotificacion(getResources(),
		// Idimg,texto);
		LatLng center = mMap.getCameraPosition().target;

		melbourne = mMap.addMarker(new MarkerOptions().position(center));

	}

	@Override
	public void onResume() {
		super.onResume();
		if (MiMapView != null)
			MiMapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (MiMapView != null)
			MiMapView.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (MiMapView != null)
			MiMapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		// MiMapView.onLowMemory();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
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

	public double getMyLat() {
		return myLat;
	}

	public void setMyLat(double myLat) {
		this.myLat = myLat;
	}

	public double getMyLng() {
		return myLng;
	}

	public void setMyLng(double myLng) {
		this.myLng = myLng;
	}

	public void VerRuta(View v) {

		mMap.clear();
		LatLng origen = new LatLng(getMyLat(), getMyLng());

		// LatLng origen = new LatLng(10.41762803, -75.56258082);

		LatLng destino = new LatLng(10.39128917, -75.5510366);

		mMap.addMarker(new MarkerOptions().position(destino));
		// Getting URL to the Google Directions API

		String url = getDirectionsUrl(origen, destino);

		DownloadTask downloadTask = new DownloadTask();

		// Start downloading json data from Google Directions API

		downloadTask.execute(url);

	}

	@Override
	public void onLocationChanged(Location location) {
		// .addMarker(new MarkerOptions().position(new
		// LatLng(localizacion.getLatitude(), localizacion.getLongitude())));
		// Getting latitude of the current location
		double latitude = location.getLatitude();
		//

		// // Getting longitude of the current location
		double longitude = location.getLongitude();
		//
		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);
		float zoom = 20;
		float tilt = 67.5f;

		int bearing = (int) location.getBearing();
		MyMarker.setPosition(new LatLng(latitude, longitude));
		// Showing the current location in Google Map
		CameraPosition camp = new CameraPosition(latLng, zoom, tilt, bearing);
		mMap.moveCamera(CameraUpdateFactory.newCameraPosition(camp));
		//

		// Toast.makeText(getApplicationContext(), "Bearing: "+bearing,
		// Toast.LENGTH_SHORT).show();
		// Zoom in the Google Map
		// mMap.animateCamera(CameraUpdateFactory.zoomTo(22));

		// Setting latitude and longitude in the TextView tv_location

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int statu, Bundle arg2) {
		System.out.println("Status: " + statu);
	}

}
