package genielogiciel.model;

public class DemandeExterieure extends Souhait {
	private String organisation;
	private Double volume;
	
	public DemandeExterieure() {
		super();
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double vol) {
		this.volume = vol;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

}
