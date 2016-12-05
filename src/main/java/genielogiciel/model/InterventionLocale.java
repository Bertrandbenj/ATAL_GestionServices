package genielogiciel.model;

import java.util.List;
import java.util.stream.Collectors;

public class InterventionLocale extends Intervention {
	
	private List<Enseignement> enseignements ;

	@Override
	public Double getVolume() {
		return enseignements.stream().collect(Collectors.summingDouble(e -> e.getVolume()));
	}

}
