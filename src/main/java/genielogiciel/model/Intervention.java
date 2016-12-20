package genielogiciel.model;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Intervention {
	
	private Integer annee;
	transient Enseignant ens;

	public Integer getAnnee() {
		return annee;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public static final Encoder<Intervention> Encoder = Encoders.bean(Intervention.class);
	
	public Double getVolume(){
		return 0.0;
	}

}
