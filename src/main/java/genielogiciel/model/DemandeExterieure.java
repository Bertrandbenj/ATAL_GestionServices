package genielogiciel.model;

public class DemandeExterieure extends Souhait {
	String organisation;
	Double vol;
	
	public DemandeExterieure() {
		
	}

	@Override
	Double getVolume() {
		return vol;
	}

}
