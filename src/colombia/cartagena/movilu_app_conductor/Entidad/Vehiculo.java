package colombia.cartagena.movilu_app_conductor.Entidad;

public class Vehiculo {

	String placa, nroMovil, Id;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getNroMovil() {
		return nroMovil;
	}

	public void setNroMovil(String nroMovil) {
		this.nroMovil = nroMovil;
	}

	public Vehiculo(){

	}

	public Vehiculo(String placa, String nroMovil,String Id) {
		this.placa = placa;
		this.nroMovil = nroMovil;
		this.Id= Id;
	}


}
