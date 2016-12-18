package genielogiciel.model;

import java.util.List;
import java.util.stream.Collectors;

public class InterventionLocale extends Intervention {
	
	private List<Enseignement> enseignements ;

	public InterventionLocale() {
		super();
	}

	public InterventionLocale(List<Enseignement> enseignements) {
		super();
		this.enseignements = enseignements;
	}

	public List<Enseignement> getEnseignements() {
		return enseignements;
	}

	public void setEnseignements(List<Enseignement> enseignements) {
		this.enseignements = enseignements;
	}

	@Override
	public Double getVolume() {
		return enseignements.stream().collect(Collectors.summingDouble(e -> e.getVolume()));
	}

}
