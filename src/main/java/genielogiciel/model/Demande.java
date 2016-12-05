package genielogiciel.model;

import java.util.UUID;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public abstract class Demande {

	public static Encoder<Demande> Encoder = Encoders.bean(Demande.class);
	
	private Boolean publie;
	private UUID uuid;
	
	
	
	public Demande() {
		uuid = UUID.randomUUID();
	}
	
	public boolean equals(Object o){
		if(o instanceof Demande)
			return ((Demande)o).uuid.equals(uuid);
		return false;
	}
	
	abstract Double getVolume();


	public Demande(Boolean publie) {
		this.setPublie(publie);
	}

	public Boolean getPublie() {
		return publie;
	}

	public void setPublie(Boolean publie) {
		this.publie = publie;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

}
