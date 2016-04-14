package colombia.cartagena.movilu_app_conductor.DataBase;

import java.util.ArrayList;
import java.util.List;

import colombia.cartagena.movilu_app_conductor.Entidad.Estudiante;
import colombia.cartagena.movilu_app_conductor.Entidad.Vehiculo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class DbHelper extends SQLiteOpenHelper {

	public DbHelper(Context context) {
		super(context, "dbConductor", null, 3);
		// TODO Auto-generated constructor stub
	}

	public void Abrir() {
		this.getWritableDatabase();
	}

	public void Cerrar() {
		this.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String Query = "CREATE TABLE vehiculo(" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "placa TEXT, nroMovil text, idV text); ";

		db.execSQL(Query);

		String Query2 = "CREATE TABLE estudiante(" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "id TEXT, apellidos text, barrio text,correo text, zona text, direccion text, fecha text,nombre text,universidad text,ref1 text, ref2 text, ref3 text,ubicacion text,estado text); ";

		db.execSQL(Query2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String Query = "DROP TABLE IF EXISTS vehiculo";
		db.execSQL(Query);

		String Query2 = "DROP TABLE IF EXISTS estudiante";
		db.execSQL(Query2);

		onCreate(db);
	}

	public boolean Insertar(String placa, String nroMovil, String Id) {
		boolean result = false;
		try {

			if (!placa.isEmpty() || !nroMovil.isEmpty()) {

				Abrir();
				ContentValues registro = new ContentValues();
				registro.put("placa", placa);
				registro.put("nroMovil", nroMovil);
				registro.put("idV", Id);
				this.getWritableDatabase().insert("vehiculo", null, registro);
				Cerrar();
				result = true;
			} else {
				return false;
			}

		} catch (Exception ex) {
			result = false;
		}

		return result;
	}

	public boolean InsertarEstudiante(Estudiante e) {
		boolean result = false;
		try {

				Abrir();
				ContentValues registro = new ContentValues();
				registro.put("id", e.getId());
				registro.put("apellidos", e.getApellidos());
				registro.put("barrio", e.getBarrio());
				registro.put("correo", e.getCorreo());
				registro.put("zona", e.getZona());
				registro.put("direccion", e.getDireccion());
				registro.put("fecha", e.getFecha());
				registro.put("nombre", e.getNombre());
				registro.put("universidad", e.getUniversidad());
				registro.put("ubicacion", e.getUbicacion());
				registro.put("estado", e.getEstado());
				registro.put("ref1", e.getRef1());
				registro.put("ref2", e.getRef2());
				registro.put("ref3", e.getRef3());
				this.getWritableDatabase().insert("estudiante", null, registro);
				Cerrar();

				result = true;

		} catch (Exception ex) {
			result = false;
		}

		return result;
	}

	public void borrar() {
		try {
			Abrir();
			SQLiteDatabase db = getWritableDatabase();
			db.delete("vehiculo", null, null);
			db.delete("estudiante", null, null);
			db.close();
			Cerrar();
		} catch (Exception ex) {
			//escribir en un log
		}

	}

	public void borrarPorId(int id) {
		try{


		Abrir();
		SQLiteDatabase db = getWritableDatabase();
		db.delete("vehiculo", "_id=" + id, null);
		db.delete("estudiante", "id=" + id, null);
		Cerrar();

		}catch(Exception ex){
			//escribir en un log
		}
	}

	public void modificar(Vehiculo vehiculo) {
		try {

			Abrir();
			SQLiteDatabase db = getWritableDatabase();
			ContentValues valores = new ContentValues();
			valores.put("idV", vehiculo.getId());
			valores.put("placa", vehiculo.getPlaca());
			valores.put("nroMovil", vehiculo.getNroMovil());
			db.update("vehiculo", valores, "idV=" + vehiculo.getId(),null);
			Cerrar();

		} catch (Exception ex) {
			//escribir en un log
		}

	}

	public ArrayList<Vehiculo> obtenerRegistros() {
		int colId, colPlaca, colNroMovil, colIdV;
		ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		try{

		Abrir();
		String columnas[] = { _ID, "placa", "nroMovil", "idV" };
		Cursor c = this.getReadableDatabase().query("vehiculo", columnas, null,null, null, null, null);

		colId = c.getColumnIndex(_ID);
		colPlaca = c.getColumnIndex("placa");
		colNroMovil = c.getColumnIndex("nroMovil");
		colIdV = c.getColumnIndex("idV");

		if (c.getCount() > 0) {
			c.moveToFirst();

			do {
				Vehiculo vehiculo = new Vehiculo();
				vehiculo.setId(c.getString(colIdV));
				vehiculo.setPlaca(c.getString(colPlaca));
				vehiculo.setNroMovil(c.getString(colNroMovil));

				vehiculos.add(vehiculo);
			} while (c.moveToNext());
		}

		Cerrar();

		}catch(Exception ex){
			//escribir en un log
		}

		return vehiculos;
	}


	public ArrayList<Estudiante> obtenerEstudiantes() {
		int colId, colIdE, colApellidos, colBarrio,colCorreo,colZona,colDireccion,colFecha,colNombre,colUniversidad,colUbicacion,colEstado, colRef1,colRef2,colRef3;
		ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
		try{

		Abrir();
		String columnas[] = { _ID, "id", "apellidos","barrio", "correo", "zona","direccion", "fecha", "nombre","universidad","ubicacion", "estado", "ref1", "ref2", "ref3" };
		Cursor c = this.getReadableDatabase().query("estudiante", columnas, null,null, null, null, null);

		colId = c.getColumnIndex(_ID);
		colIdE = c.getColumnIndex("id");
		colApellidos = c.getColumnIndex("apellidos");
		colBarrio = c.getColumnIndex("barrio");
		colCorreo = c.getColumnIndex("correo");
		colZona = c.getColumnIndex("zona");
		colDireccion = c.getColumnIndex("direccion");
		colFecha = c.getColumnIndex("fecha");
		colNombre = c.getColumnIndex("nombre");
		colUniversidad = c.getColumnIndex("universidad");
		colUbicacion = c.getColumnIndex("ubicacion");
		colEstado = c.getColumnIndex("estado");
		colRef1 = c.getColumnIndex("ref1");
		colRef2 = c.getColumnIndex("ref2");
		colRef3 = c.getColumnIndex("ref3");

		if (c.getCount() > 0) {
			c.moveToFirst();

			do {

				Estudiante estudiante = new Estudiante();
				estudiante.setId(c.getString(colIdE));
				estudiante.setApellidos(c.getString(colApellidos));
				estudiante.setBarrio(c.getString(colBarrio));
				estudiante.setCorreo(c.getString(colCorreo));
				estudiante.setZona(c.getString(colZona));
				estudiante.setDireccion(c.getString(colDireccion));
				estudiante.setFecha(c.getString(colFecha));
				estudiante.setNombre(c.getString(colNombre));
				estudiante.setUniversidad(c.getString(colUniversidad));
				estudiante.setUbicacion(c.getString(colUbicacion));
				estudiante.setEstado(c.getString(colEstado));
				estudiante.setRef1(c.getString(colRef1));
				estudiante.setRef2(c.getString(colRef2));
				estudiante.setRef3(c.getString(colRef3));


				estudiantes.add(estudiante);
			} while (c.moveToNext());
		}

		Cerrar();

		}catch(Exception ex){
			//escribir en un log
		}

		return estudiantes;
	}

}
