package genielogiciel.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Demande {

	public static Encoder<Demande> Encoder = Encoders.bean(Demande.class);
	
	private Boolean publie;
	private Integer heures;
	
	public Demande() {

	}
	
	public Boolean getPublie() {
		return publie;
	}

	public void setPublie(Boolean publie) {
		this.publie = publie;
	}

	public Integer getHeures() {
		return heures;
	}

	public void setHeures(Integer heures) {
		this.heures = heures;
	}

	public Demande(Boolean publie, Integer heures) {
		this.publie = publie;
		this.heures = heures;
	}

}
