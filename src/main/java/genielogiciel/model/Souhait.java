package genielogiciel.model;

import java.util.UUID;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public abstract class Souhait {

	public static Encoder<Souhait> Encoder = Encoders.bean(Souhait.class);
	
	private Boolean publie;
	private UUID uuid;
	
	public Souhait() {
		setUuid(UUID.randomUUID());
		setPublie(false);
	}
	
	public boolean equals(Object o){
		if(o instanceof Souhait)
			return ((Souhait)o).uuid.equals(uuid);
		return false;
	}
	
	abstract Double getVolume();


	public Souhait(Boolean publie) {
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
