package genielogiciel.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public abstract class Demande {

	public static Encoder<Demande> Encoder = Encoders.bean(Demande.class);
	
	private Boolean publie;
	
	
	
	public Demande() {

	}
	
	abstract Double getVolume();
	
	public Boolean getPublie() {
		return publie;
	}

	public void setPublie(Boolean publie) {
		this.publie = publie;
	}

	public Demande(Boolean publie) {
		this.publie = publie;
	}

}
