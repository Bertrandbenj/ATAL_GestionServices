package genielogiciel.model;

import java.util.UUID;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Souhait {

	public static Encoder<Souhait> Encoder = Encoders.bean(Souhait.class);
	
	private Boolean publie;
	private String uuid;
	
	public Souhait() {
		setUuid(UUID.randomUUID().toString());
		setPublie(false);
	}
	
	public boolean equals(Object o){
		if(o==null)
			return false;
		
		if(o instanceof String)
			return ((String)o).equals(uuid);
		
		if(o instanceof Souhait)
			return ((Souhait)o).uuid.equals(uuid);
		return false;
	}
	
	public Double getVolume(){
		return 0.;
	}


	public Souhait(Boolean publie) {
		this.setPublie(publie);
	}

	public Boolean getPublie() {
		return publie;
	}

	public void setPublie(Boolean publie) {
		this.publie = publie;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String string) {
		this.uuid = string;
	}

}
