package genielogiciel.model;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Voeu extends Souhait{
	
	public static Encoder<Voeu> Encoder = Encoders.bean(Voeu.class);
	
	private Integer priority;
	private Enseignement enseignement;

	public Voeu(Enseignement e, Integer priority) {
		this.enseignement=e;
		this.setPriority(priority);
	}

	@Override
	Double getVolume() {
		return enseignement.getVolume();
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}
