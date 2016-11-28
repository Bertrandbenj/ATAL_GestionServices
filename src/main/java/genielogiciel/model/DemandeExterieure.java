package genielogiciel.model;

public class DemandeExterieure extends Demande {
	String organisation;
	Double vol;
	
	public DemandeExterieure() {
		
	}

	@Override
	Double getVolume() {
		return vol;
	}

}
