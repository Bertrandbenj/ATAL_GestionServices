package genielogiciel.model;

public class Voeu extends Demande{
	
	Integer priority;
	Enseignement e;

	public Voeu(Enseignement e, Integer priority) {
		this.e=e;
		this.priority=priority;
	}

	@Override
	Double getVolume() {
		return e.getVolume();
	}

}
